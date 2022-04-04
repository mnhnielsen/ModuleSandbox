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
    protected float x, y, speed, radians, fireDelay, fireRate = 1f, delay;
    protected int width, height;
    protected Sprite sprite;
    protected GameScreen gameScreen;
    private HealthPart lifePart = new HealthPart(100);
    private boolean canMove = true, isMoving;
    private String fileName;
    Vector2 dir = new Vector2();


    @Override
    public void player(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 6;
        width = 64;
        height = 64;

        fileName = "soldierIdle.png";

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
                    //updateTexture("soldierIdle.png");
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);


        dir.setZero();

        radians = 0;
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
            dir.x = 1;
            spawnBullet(1, 0, 60, 15, 30, 10, 5);

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

        if (isMoving && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            speed = 10;
        else
            speed = 6;

        dir.nor();

        float dirX = dir.x * speed;
        float dirY = dir.y * speed;

        body.setLinearVelocity(dirX, dirY);

    }

    private void spawnBullet(float directionX, float directionY, float spawnX, float spawnY, int speed, int width, int height)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            fireDelay -= Gdx.graphics.getDeltaTime() * fireRate;
            if (fireDelay <= 0)
            {
                EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(this.x + dir.x + spawnX, this.y + dir.y + spawnY, 0, 0, width, height, "yellow.png", gameScreen, gameScreen.getGameWorld());
                bullet.getBody().setLinearVelocity(directionX * speed, directionY * speed);
                gameScreen.getGameWorld().addBulletObject(bullet);
                fireDelay += 0.25;
                System.out.println("Shooting");
                gameScreen.getGameWorld().addBulletObject(bullet);

            }
        }
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x, y, width, height);
        for (EntityObject object : gameScreen.getGameWorld().getBulletEntities())
            batch.draw(object.getSprite(), object.getBody().getPosition().x * Const.PPM - (object.getWidth() / 2), object.getBody().getPosition().y * Const.PPM - (object.getHeight() / 2), object.getWidth(), object.getHeight());
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
