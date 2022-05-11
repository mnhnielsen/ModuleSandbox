package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;
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

    TiledMapTileLayer layer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get("colission");
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
        x = player.getBody().getPosition().x * Const.PPM - (player.getWidth() / 2);
        y = player.getBody().getPosition().y * Const.PPM - (player.getHeight() / 2);
        dir.setZero();


        mapCreation.isCellBlocked(x, y);



        if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove)
        {
            radians = 90;
            updateTexture("soldierUp.png");
            dir.y = 1;
            spawnBullet(0, 1, 50, 70, 30, 5, 10);
            isMoving = true;



        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove)
        {
            radians = 270;
            updateTexture("soldierDown.png");
            dir.y = -1;
            spawnBullet(0, -1, 15, 0, 30, 5, 10);

            isMoving = true;


        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove)
        {
            radians = 0;
            updateTexture("soldierRight.png");
            spawnBullet(1, 0, 60, 15, 30, 10, 5);
            dir.x = 1;
            isMoving = true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove)
        {
            radians = 180;
            updateTexture("soldierLeft.png");
            dir.x = -1;
            spawnBullet(-1, 0, -10, 50, 30, 10, 5);
            isMoving = true;



        }

        collisionDetect();

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

    private void collisionDetect() {
        if (dir.x < 0) {

            //top left
            collisionX = layer.getCell((int) (x / tileWidth),
                    (int) (y + player.getHeight() / tileHeight)).getTile().getProperties().containsKey("blocked");
            //middle left
            if(!collisionX)
            collisionX = layer.getCell((int) (x / tileWidth),
                    (int) ((y + player.getHeight() / 2) / tileHeight)).getTile().getProperties().containsKey("blocked");
            //bottom left
            if(!collisionX)
            collisionX = layer.getCell((int) (x / tileWidth),
                    (int) (y / tileHeight)).getTile().getProperties().containsKey("blocked");

        } else if( dir.x> 0) {

        }
        if(dir.y < 0) {

        }else if (dir.y > 0) {

        }
    }

    @Override
    public Vector2 position()
    {
        return new Vector2(x, y);
    }
}
