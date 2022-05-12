package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.Const;
import org.example.helper.LibWorld;
import org.example.spi.IBulletService;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapSpi;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerController implements IEntityProcessingService
{
    private Entity player = PlayerCreation.INSTANCE.getPlayer();
    private boolean canMove = true, isMoving;
    private float x, y, radians, fireDelay, fireRate = 1f, oldX = x, oldY = y;
    private Vector2 dir = new Vector2();


    private final Lookup lookup = Lookup.getDefault();
    private final IMapSpi mapSpi = lookup.lookup(IMapSpi.class);

    private boolean collisionX = false, collisionY = false;

    MapCreation mapCreation = new MapCreation();

    TiledMapTileLayer layer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(1);
    float tileWidth = mapCreation.getLayer().getTileWidth(), tileHeight = mapCreation.getLayer().getTileHeight();


    public void updateTexture(String fname)
    {

        Gdx.app.postRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                File file = new File(this.getClass().getResource(fname).getPath());
                String path = file.getPath().substring(5);

                AssetLoader.INSTANCE.getAm().load(path, Texture.class);
                AssetLoader.INSTANCE.getAm().finishLoading();

                Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
                player.setSprite(sprite);
            }
        });

    }

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {

        //updateTexture("soldierIdle.png");
        if (player.getHealthPart().isHit() && player.getHealthPart().getLife() <= 50)
            //updateTexture("injured.png");
            if (player.getHealthPart().isDead())
            {
                canMove = false;
                try
                {
                    Thread.sleep(2500);
                    player.getHealthPart().setLife(100);
                    player.getHealthPart().setDead(false);
                    player.getBody().setTransform(Gdx.graphics.getWidth() / 2 / Const.PPM, Gdx.graphics.getHeight() / 2 / Const.PPM, 0);
                    canMove = true;
                    //updateTexture("soldierIdle.png");
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        setX(player.getBody().getPosition().x * Const.PPM - (player.getWidth() / 2));
        setY(player.getBody().getPosition().y * Const.PPM - (player.getHeight() / 2));
        dir.setZero();



        if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove)
        {

            if  (collisionY = collideTop()) {
                setY(oldY);
                canMove = false;
            }

            radians = 90;
            updateTexture("soldierUp.png");
            dir.y = 1;
            spawnBullet(0, 1, 50, 70, 30, 5, 10);
            isMoving = true;




        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove)
        {

            if  (collisionY = collideBottom()) {
                setY(oldY);
                canMove = false;
            }

            radians = 270;
            updateTexture("soldierDown.png");
            dir.y = -1;
            spawnBullet(0, -1, 15, 0, 30, 5, 10);
            isMoving = true;



        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove)
        {
            if(collisionX = collideRight()) {
                setX(oldX);
                canMove = false;
            }

            radians = 0;
            updateTexture("soldierRight.png");
            spawnBullet(1, 0, 60, 15, 30, 10, 5);
            dir.x = 1;
            isMoving = true;


        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove)
        {

            if(collisionX = collideLeft()) {
                setX(oldX);
                canMove = false;
            }

            radians = 180;
            updateTexture("soldierLeft.png");
            dir.x = -1;
            spawnBullet(-1, 0, -10, 50, 30, 10, 5);
            isMoving = true;



        }



        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D))
            updateTexture("soldier135.png");
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
            updateTexture("soldier45.png");
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A))
            updateTexture("soldier45Left.png");
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
            updateTexture("soldier135Left.png");


        if (isMoving && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            player.setSpeed(10);
        else
            player.setSpeed(6);

        dir.nor();

        float dirX = dir.x * player.getSpeed();
        float dirY = dir.y * player.getSpeed();

        player.getBody().setLinearVelocity(dirX, dirY);

        batch.draw(player.getSprite(), x, y, player.getWidth(), player.getHeight());

        for (Entity object : world.getEntities(Bullet.class))
            batch.draw(object.getSprite(), object.getBody().getPosition().x * Const.PPM - (object.getWidth() / 2), object.getBody().getPosition().y * Const.PPM - (object.getHeight() / 2), object.getWidth(), object.getHeight());


    }


    private void spawnBullet(float directionX, float directionY, float spawnX, float spawnY, int speed, int width, int height)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            fireDelay -= Gdx.graphics.getDeltaTime() * fireRate;
            if (fireDelay <= 0)
            {
                Entity bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(this.x + dir.x + spawnX, this.y + dir.y + spawnY, 0, 0, width, height, "yellow.png", GameWorld.INSTANCE);
                bullet.getBody().setLinearVelocity(directionX * speed, directionY * speed);
                GameWorld.INSTANCE.addEntity(bullet);
                fireDelay += 0.25;
                System.out.println("Shooting");
            }
        }
    }

    public boolean collideRight() {
        boolean collides = false;
        for(float step =0; step < player.getHeight(); step += layer.getTileHeight() /2){
            if(collides = mapCreation.isCellBlocked(getX() + player.getWidth(), getY() + step)){
                break;
            }
        }
        return collides;
    }

    public boolean collideLeft() {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += layer.getTileHeight() /2)
            if(collides = mapCreation.isCellBlocked(getX(), getY() + step)) {
                break;
            }
        return collides;
    }

    public boolean collideTop(){
        boolean collides = false;
        for(float step = 0; step < player.getWidth(); step += layer.getTileWidth() /2){
            if(collides = mapCreation.isCellBlocked(getX() + step, getY() + player.getHeight())){
                    break;
                }
        }
        return collides;
    }

    public boolean collideBottom() {
        boolean collides = false;

        for (float step = 0; step < player.getWidth(); step += layer.getTileWidth() /2 ) {
            if (collides = mapCreation.isCellBlocked(getX() + step, getY())) {
                break;
            }

        }
        return collides;
    }


    @Override
    public Vector2 position()
    {
        return new Vector2(x, y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
