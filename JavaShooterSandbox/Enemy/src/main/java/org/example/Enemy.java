package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IEnemyService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IEnemyService.class)
})
public class Enemy implements IEnemyService
{
    protected Body body;
    protected float x, y, speed, velY, velX, dirY, dirX;
    protected double hyp;
    protected int width, height;
    protected Sprite sprite;
    protected GameScreen gameScreen;
    protected double attackRange = 32.48322;

    @Override
    public void enemy(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 3;
        width = 32;
        height = 32;

        File file = new File(this.getClass().getResource("red.png").getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.PLAYER);

    }

    @Override
    public void update()
    {
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);
        velY = 0;
        velX = 0;

        //Difference in coordinates
        dirX = gameScreen.getPlayerService().getX() - x;
        dirY = gameScreen.getPlayerService().getY() - y;

        double distToPlayer = Vector2.dst(x, y, gameScreen.getPlayerService().getX(), gameScreen.getPlayerService().getY());


        //If statements to decide what direction zombie goes, based on dirX and dirY
        if (dirX < 0)
        {
            velX = -1;
        }
        if (dirX > 0)
        {
            velX = 1;
        }
        if (dirY > 0)
        {
            velY = 1;
        }
        if (dirY < 0)
        {
            velY = -1;
        }
        if (distToPlayer <= attackRange)
        {
            velX = 0;
            velY = 0;
        }

        body.setLinearVelocity(velX * speed, velY * speed);

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x, y, width, height);
    }

}
