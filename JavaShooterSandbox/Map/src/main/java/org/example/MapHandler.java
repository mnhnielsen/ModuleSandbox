package org.example;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.GameWorld;
import org.example.spi.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})

public class MapHandler implements IEntityProcessingService
{

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        if (world.getEntities(Enemy.class).size() == 0)
            System.out.println("All enemies destroyed. Change map");
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
