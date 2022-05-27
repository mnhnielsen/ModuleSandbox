package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.*;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Random;
import java.util.function.IntUnaryOperator;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerController implements IEntityProcessingService
{
    private boolean canMove = true, isMoving;
    private float x, y, fireDelay, fireRate = 1f;
    private Vector2 dir = new Vector2();
    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer walkableLayer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(2);


    public void updateTexture(String fname)
    {
        Gdx.app.postRunnable(new Runnable()
        {

            @Override
            public void run()
            {
                try
                {
                    File file = new File(this.getClass().getResource(fname).getPath());
                    String path = file.getPath().substring(5);

                    AssetLoader.INSTANCE.getAm().load(path, Texture.class);
                    AssetLoader.INSTANCE.getAm().finishLoading();

                    Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
                    for (Entity p : GameWorld.INSTANCE.getEntities(Player.class))
                        p.setSprite(sprite);
                } catch (NullPointerException e)
                {
                    System.out.println();
                }
            }
        });


    }

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        for (Entity p : world.getEntities(Player.class))
        {
            if (p.getHealthPart().dead())
            {
                canMove = false;
                try
                {
                    Thread.sleep(2500);
                    p.getHealthPart().setHealth(100);
                    p.getHealthPart().setDead(false);
                    p.getBody().setTransform(new Random().nextFloat() * Gdx.graphics.getWidth() / Const.PPM, new Random().nextFloat() * Gdx.graphics.getHeight() / Const.PPM, 0);

                    canMove = true;
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }

            x = p.getBody().getPosition().x * Const.PPM - (p.getWidth() / 2);
            y = p.getBody().getPosition().y * Const.PPM - (p.getHeight() / 2);
            dir.setZero();

            if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove && !collideTop(p))
            {
                updateTexture("soldierUp.png");
                dir.y = 1;
                spawnBullet(0, 1, 50, 70, 30, 5, 10);
                isMoving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove && !collideBottom(p))
            {
                updateTexture("soldierDown.png");
                dir.y = -1;
                spawnBullet(0, -1, 15, 0, 30, 5, 10);

                isMoving = true;

            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove && !collideRight(p))
            {
                updateTexture("soldierRight.png");
                dir.x = 1;
                spawnBullet(1, 0, 60, 15, 30, 10, 5);

                isMoving = true;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove && !collideLeft(p))
            {
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
                p.setSpeed(10);
            else
                p.setSpeed(6);

            dir.nor();

            float dirX = dir.x * p.getSpeed();
            float dirY = dir.y * p.getSpeed();

            p.getBody().setLinearVelocity(dirX, dirY);

            batch.draw(p.getSprite(), p.getBody().getPosition().x * Const.PPM - (p.getWidth() / 2), p.getBody().getPosition().y * Const.PPM - (p.getHeight() / 2), p.getWidth(), p.getHeight());

            for (Entity object : world.getEntities(Bullet.class))
                batch.draw(object.getSprite(), object.getBody().getPosition().x * Const.PPM - (object.getWidth() / 2), object.getBody().getPosition().y * Const.PPM - (object.getHeight() / 2), object.getWidth(), object.getHeight());


        }
    }


    private void spawnBullet(float directionX, float directionY, float spawnX, float spawnY, int speed, int width, int height)
    {
        try
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
                }
            }
        } catch (NullPointerException e)
        {
            System.out.println("Cannot fire");
        }
    }

    public boolean collideRight(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(position().x + player.getWidth(), position().y + step);
        return collides;
    }

    public boolean collideLeft(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(position().x, position().y + step);

        return collides;
    }

    public boolean collideTop(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(position().x + step, position().y + player.getHeight());

        return collides;
    }

    public boolean collideBottom(Entity player)
    {
        boolean collides = false;

        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(position().x + step, position().y);
        return collides;
    }


    @Override
    public Vector2 position()
    {
        return new Vector2(x, y);
    }


}
