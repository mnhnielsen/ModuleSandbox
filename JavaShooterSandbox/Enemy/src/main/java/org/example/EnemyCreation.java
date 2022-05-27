package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.GameWorld;
import org.example.data.parts.HealthPart;
import org.example.helper.*;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@ServiceProviders(value = {@ServiceProvider(service = IGamePluginService.class)})

public class EnemyCreation implements IGamePluginService
{
    protected ArrayList<Enemy> enemies = new ArrayList<>();

    @Override
    public void spawnEnemies(int amountOfEnemies, int speed, String filename)
    {
        for (int i = 0; i < amountOfEnemies; i++)
        {

            Enemy e = createEnemy(filename,speed);
            enemies.add(e);
        }
        System.out.println(enemies.size());

    }


    protected Enemy createEnemy(String filename, int maxSpeed)
    {
        float x = new Random().nextFloat() * Gdx.graphics.getWidth();
        float y = new Random().nextFloat() * Gdx.graphics.getWidth();

        int speed = new Random().nextInt(maxSpeed - 2) + 2;
        int width = 32;
        int height = 32;

        File file = new File(this.getClass().getResource(filename).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, false, 10000, LibWorld.INSTANCE.getWorld(), ContactType.ENEMY);

        Enemy p = new Enemy(x, y, speed, width, height, body, sprite, new HealthPart(100));
        return p;
    }


    @Override
    public void start(GameWorld world)
    {
        for (Enemy enemy : enemies)
            world.addEntity(enemy);
    }

    @Override
    public void stop(GameWorld world)
    {
        for (Enemy enemy : enemies)
            world.removeEntity(enemy);
    }

}
