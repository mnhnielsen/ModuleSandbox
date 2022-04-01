package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IBulletService;
import org.example.spi.IPlayerService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IPlayerService.class)
})
public class PlayerControl implements IPlayerService
{
    protected Body body;
    protected float x, y, speed, velY, velX;
    protected int width, height;
    protected Sprite sprite;
    protected GameScreen gameScreen;
    private HealthPart lifePart = new HealthPart(100);
    private boolean canMove = true, isMoving;
    private String fileName;
    private boolean canShoot = true;
    private float fireDelay;


    @Override
    public void player(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 6;
        width = 32;
        height = 32;

        fileName = "color.png";

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
        if (lifePart.isHit() && lifePart.getHealth() <= 50)
            updateTexture("injured.png");
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
                updateTexture("color.png");
            } catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);
        velY = 0;
        velX = 0;


        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canShoot)
        {
            fireDelay -= Gdx.graphics.getDeltaTime();
            if (fireDelay<=0)
            {
                EntityObject bullet = Lookup.getDefault().lookup(IBulletService.class).createBullet(x + 60, y + 15, 50, 20, 10, "red.png", gameScreen, gameScreen.getGameWorld());
                gameScreen.getGameWorld().addEntity(bullet);
                fireDelay += 0.25;
            }

        }

        if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove)
        {
            velY = 1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove)
        {
            velY = -1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove)
        {
            velX = 1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove)
        {
            velX = -1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && isMoving && canMove)
            speed = 10;
        else
            speed = 6;

        body.setLinearVelocity(velX * speed, velY * speed);
    }


    @Override
    public void render(SpriteBatch batch)
    {

        batch.draw(sprite, x, y, width, height);
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
