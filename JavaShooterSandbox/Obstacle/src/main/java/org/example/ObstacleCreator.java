package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.BodyHelper;
import org.example.helper.ContactType;
import org.example.helper.LibWorld;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@ServiceProviders(value = {@ServiceProvider(service = IGamePluginService.class)})
public class ObstacleCreator implements IGamePluginService
{
    protected ArrayList<Obstacle> obstacles = new ArrayList<>();
    private int obstacleAmount = 2;


    private void spawnObstacles()
    {
        for (int i = 0; i < obstacleAmount; i++)
        {
            Obstacle obstacle = createObstacle();
            obstacles.add(obstacle);
        }
    }

    protected Obstacle createObstacle()
    {
        float x = new Random().nextFloat() * Gdx.graphics.getWidth();
        float y = new Random().nextFloat() * Gdx.graphics.getHeight();

        int width = (int) (Math.random() * (230 - 110)) + 110;
        int height = (int) (Math.random() * (50 - 30)) + 30;

        File file = new File(this.getClass().getResource("red.png").getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, true, 10000, LibWorld.INSTANCE.getWorld(), ContactType.OBSTACLE);

        Obstacle obstacle = new Obstacle(x, y, width, height, body, sprite);
        return obstacle;
    }

    @Override
    public void start(GameWorld world)
    {
        spawnObstacles();
        for (Obstacle obstacle : obstacles)
        {
            world.addEntity(obstacle);
        }
    }

    @Override
    public void stop(GameWorld world)
    {
        for (Obstacle obstacle : obstacles)
        {
            world.removeEntity(obstacle);
        }
    }
}
