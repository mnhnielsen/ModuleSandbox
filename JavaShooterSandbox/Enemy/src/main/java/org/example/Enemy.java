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

        //Enemy movement with vector
        Vector2 zombiePos = new Vector2(x, y);
        Vector2 playerPos = new Vector2(gameScreen.getPlayerService().getX(), gameScreen.getPlayerService().getY());
        Vector2 direction = new Vector2();

        //Difference in position to create vector with direction
        direction.x = playerPos.x - zombiePos.x;
        direction.y = playerPos.y - zombiePos.y;

        //Normalize vector so the length is always 1, and therefore "speed" decides how fast enemy goes
        direction.nor();


        float speedX = direction.x * speed;
        float speedY = direction.y * speed;

        body.setLinearVelocity(speedX, speedY);

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x, y, width, height);
    }

}
