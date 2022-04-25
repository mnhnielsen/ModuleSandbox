package org.example;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.spi.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class ObstacleController implements IEntityProcessingService
{
    protected ObstacleCreator obstacle = new ObstacleCreator();

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        for (Entity obstacle : world.getEntities(Obstacle.class))
        {
            obstacle.setX(obstacle.getBody().getPosition().x * Const.PPM - (obstacle.getWidth() / 2));
            obstacle.setY(obstacle.getBody().getPosition().y * Const.PPM - (obstacle.getHeight() / 2));
        }
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
