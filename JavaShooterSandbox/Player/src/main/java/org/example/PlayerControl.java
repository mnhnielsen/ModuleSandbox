package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IBulletService;
import org.example.spi.IPlayerService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import javax.swing.text.html.parser.Entity;
import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IPlayerService.class)
})
public class PlayerControl implements IPlayerService
{
    protected Body body;
    protected float x, y, speed, velY, velX, radians = 3.1415f / 2, rotationSpeed = 5f, fireRate = 1f;
    protected int width, height;
    protected Sprite sprite;
    protected GameScreen gameScreen;
    private HealthPart lifePart = new HealthPart(100);
    private boolean canMove = true, isMoving;
    private String fileName;
    private boolean canShoot = true;
    private float fireDelay, experation = 5f;


    @Override
    public void player(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 6;
        width = 64;
        height = 64;

        fileName = "man.png";

        File file = new File(this.getClass().getResource(fileName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.PLAYER);
    }



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

                sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
            }
        });

    }


    @Override
    public void update()
    {
        updateTexture("soldierIdle.png");

        if (lifePart.isHit() && lifePart.getHealth() <= 50)
            //updateTexture("injured.png");
        if (lifePart.dead())
        {
            canMove = false;
            try
            {
                Thread.sleep(2500);
                lifePart.setHealth(100);
                lifePart.setDead(false);
                body.setTransform(Boot.INSTANCE.getScreenWidth() / 2 / Const.PPM, Boot.INSTANCE.getScreenHeight() / 2 / Const.PPM, 0);
                canMove = true;
                updateTexture("soldierIdle.png");
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);

        velY = 0;
        velX = 0;



        double direction = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove)
        {
            updateTexture("soldierUp.png");
            velY = 1;
            isMoving = true;
            direction = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove)
        {
            updateTexture("soldierDown.png");
            velY = -1;
            isMoving = true;
            direction = 3;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove)
        {
            updateTexture("soldierRight.png");
            velX = 1;
            isMoving = true;
            direction = 2;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove)
        {
            updateTexture("soldierLeft.png");
            velX = -1;
            isMoving = true;
            direction = 4;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D))
        {
            direction = 1.5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && Gdx.input.isKeyPressed(Input.Keys.S))
        {
            direction = 2.5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A))
        {
            direction = 3.5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && Gdx.input.isKeyPressed(Input.Keys.W))
        {
            direction = 4.5;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 1)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 15, y + 60, 0, 50, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 1.5)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 60, y + 60, 25, 25, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 2)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 60, y + 15, 50, 0, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 2.5)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 60, y - 15, 25, -25, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 3)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 15, y - 28, 0, -50, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 3.5)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x - 28, y - 15, -25, -25, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 4)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x - 28, y + 15, -50, 0, 10, 5, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot && direction == 4.5)
        {
            EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x - 28, y + 60, -25, 25, 10, 4, "red.png", gameScreen, gameScreen.getGameWorld());
            gameScreen.getGameWorld().addBulletObject(bullet);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && isMoving && canMove)
            speed = 10;
        else
            speed = 6;


        body.setLinearVelocity(velX * speed, velY * speed);

    }


    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x, y, width, height);

        /*for (EntityObject entityObject : gameScreen.getGameWorld().getBulletEntities())
            batch.draw(entityObject.getSprite(), entityObject.getX(), entityObject.getY(), entityObject.getWidth(), entityObject.getHeight());

         */

    }



    @Override
    public float getX()
    {
        return x;
    }

    @Override
    public float getY()
    {
        return y;
    }

    public void takeDamage(int damage)
    {
        lifePart.takeDamage(damage);
    }

}
