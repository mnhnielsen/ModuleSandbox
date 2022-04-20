package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.Const;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerController implements IEntityProcessingService
{
    private static PlayerCreation player = new PlayerCreation();
    private boolean canMove = true, isMoving;
    private float x, y, radians, fireDelay, fireRate = 1f, delay;
    private Vector2 dir = new Vector2();

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
                player.getPlayer().setSprite(sprite);
            }
        });

    }

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        updateTexture("soldierIdle.png");
        if (player.getPlayer().getHealthPart().isHit() && player.getPlayer().getHealthPart().getLife() <= 50)
            //updateTexture("injured.png");
            if (player.getPlayer().getHealthPart().isDead())
            {
                canMove = false;
                try
                {
                    Thread.sleep(2500);
                    player.getPlayer().getHealthPart().setLife(100);
                    player.getPlayer().getHealthPart().setDead(false);
                    player.getPlayer().getBody().setTransform(Gdx.graphics.getWidth() / 2 / Const.PPM, Gdx.graphics.getHeight() / 2 / Const.PPM, 0);
                    canMove = true;
                    //updateTexture("soldierIdle.png");
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        x = player.getPlayer().getBody().getPosition().x * Const.PPM - (player.getPlayer().getWidth() / 2);
        y = player.getPlayer().getBody().getPosition().y * Const.PPM - (player.getPlayer().getHeight() / 2);
        dir.setZero();
        radians = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W) && canMove)
        {
            radians = 90;
            updateTexture("soldierUp.png");
            dir.y = 1;
            //spawnBullet(0, 1, 50, 70, 30, 5, 10);
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && canMove)
        {
            radians = 270;
            updateTexture("soldierDown.png");
            dir.y = -1;
            //spawnBullet(0, -1, 15, 0, 30, 5, 10);

            isMoving = true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.D) && canMove)
        {
            radians = 0;
            updateTexture("soldierRight.png");
            dir.x = 1;
            //spawnBullet(1, 0, 60, 15, 30, 10, 5);

            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A) && canMove)
        {
            radians = 180;
            updateTexture("soldierLeft.png");
            dir.x = -1;
            //spawnBullet(-1, 0, -10, 50, 30, 10, 5);
            isMoving = true;
        }

        if (isMoving && Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
            player.getPlayer().setSpeed(10);
        else
            player.getPlayer().setSpeed(6);

        dir.nor();

        float dirX = dir.x * player.getPlayer().getSpeed();
        float dirY = dir.y * player.getPlayer().getSpeed();

        player.getPlayer().getBody().setLinearVelocity(dirX, dirY);

        batch.draw(player.getPlayer().getSprite(), x, y, player.getPlayer().getWidth(), player.getPlayer().getHeight());

    }
}
