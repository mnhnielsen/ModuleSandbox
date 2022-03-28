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

import java.awt.font.FontRenderContext;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@ServiceProviders(value = {@ServiceProvider(service = IEnemyService.class)
})
public class Enemy implements IEnemyService
{
    protected GameScreen gameScreen;
    protected double attackRange = 32.48322;
    public int numberOfEnemies = 5;
    ArrayList<EnemyObject> enemies = new ArrayList<>();

    @Override
    public void enemy(GameScreen gameScreen)
    {
        for (int i = 0; i < numberOfEnemies; i++)
        {
            EnemyObject e1 = createEnemy("red.png", gameScreen);
            enemies.add(e1);
        }


    }

    private EnemyObject createEnemy(String textureName, GameScreen gameScreen)
    {
        float x = new Random().nextFloat() * Gdx.graphics.getWidth();
        float y = new Random().nextFloat() * Gdx.graphics.getHeight();

        this.gameScreen = gameScreen;
        float speed = 3;
        int width = 32;
        int height = 32;

        File file = new File(this.getClass().getResource(textureName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.ENEMY);

        return new EnemyObject(x, y, speed, width, height, body, sprite, gameScreen);
    }

    @Override
    public void update()
    {
        for (EnemyObject enemy : enemies)
        {
            enemy.setX(enemy.getBody().getPosition().x * Const.PPM - (enemy.getWidth() / 2));
            enemy.setY(enemy.getBody().getPosition().y * Const.PPM - (enemy.getHeight() / 2));
            enemy.setVelX(0);
            enemy.setVelY(0);
            //Enemy movement with vector
            Vector2 zombiePos = new Vector2(enemy.getX(), enemy.getY());
            Vector2 playerPos = new Vector2(gameScreen.getPlayerService().getX(), gameScreen.getPlayerService().getY());
            Vector2 direction = new Vector2();

            //Difference in position to create vector with direction
            direction.x = playerPos.x - zombiePos.x;
            direction.y = playerPos.y - zombiePos.y;

            //Normalize vector so the length is always 1, and therefore "speed" decides how fast enemy goes
            direction.nor();


            float speedX = direction.x * enemy.getSpeed();
            float speedY = direction.y * enemy.getSpeed();

            enemy.getBody().setLinearVelocity(speedX, speedY);
        }

    }

    @Override
    public void render(SpriteBatch batch)
    {
        for (EnemyObject enemy : enemies)
        {
            batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
        }
    }

}
