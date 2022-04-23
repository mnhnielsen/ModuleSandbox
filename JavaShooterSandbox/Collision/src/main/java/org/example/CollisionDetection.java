package org.example;

import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.data.parts.HealthPart;
import org.example.helper.Const;
import org.example.spi.ICollisionDetection;
import org.example.spi.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = ICollisionDetection.class)})

public class CollisionDetection implements ICollisionDetection
{

    Entity entity = null;

    @Override
    public void process(GameWorld gameWorld)
    {
        for (Entity entityObject : gameWorld.getEntities())
        {
            for (Entity collisionDetection : gameWorld.getEntities())
            {
                if (this.colliderCheck(entityObject, collisionDetection))
                {
                    System.out.println("hit");
                }
            }
        }
    }


    @Override
    public Entity deleteObject()
    {
        return entity;
    }

    private Boolean colliderCheck(Entity e1, Entity e2)
    {
        float dst = e1.getBody().getPosition().dst(e2.getBody().getPosition());
        return dst >= 1 && dst <= 20;
    }
}
