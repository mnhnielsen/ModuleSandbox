package org.example;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IBulletService;
import org.example.spi.ICollisionDetector;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Iterator;

@ServiceProviders(value = {@ServiceProvider(service = ICollisionDetector.class)})
public class CollisionDetector implements ICollisionDetector
{
    EntityObject entity = null;

    public void process(GameScreen gameScreen, GameWorld gameWorld)
    {
        for (EntityObject entityObject : gameWorld.getEntities())
        {
            System.out.println(entityObject.getID());

            for (EntityObject collisionDetection : gameWorld.getBulletEntities())
            {


                if (this.colliderCheck(entityObject, collisionDetection))
                {
                    HealthPart entityLife = entityObject.getHealthPart();
                    gameWorld.addObjectForDeletion(collisionDetection);

                    if (entityLife != null)
                    {
                        entityLife.setDead(true);
                        if (entityLife.dead())
                        {
                            entity = entityObject;
                            gameWorld.addObjectForDeletion(entity);
                        }
                    }

                    /*if (entityLife.getHealth() > 0)
                    {
                        entityLife.setHealth(entityLife.getHealth() - 1);
                        if (entityLife.getHealth() <= 0)
                        {
                            entityLife.setDead(true);
                            gameWorld.removeEntity(collisionDetection);
                            gameWorld.removeEntity(entityObject);

                            }
                        }
                    }

                     */
                }
            }
        }
    }

    public void deleteBullets(GameScreen gameScreen, GameWorld gameWorld)
    {
        for (EntityObject entityObject : gameWorld.getWallEntities())
        {
            for (EntityObject collisionDetection : gameWorld.getBulletEntities())
            {
                if (bulletColliderCheck(entityObject, collisionDetection))
                {
                    entity = collisionDetection;
                    gameWorld.addObjectForDeletion(entity);
                }
            }
        }
    }

    @Override
    public EntityObject deleteObject()
    {
        return entity;
    }

    private Boolean bulletColliderCheck(EntityObject e1, EntityObject e2)
    {
        float dst = e1.getBody().getPosition().dst(e2.getBody().getPosition());
        System.out.println(dst);
        return dst >= 0 && dst <= 20;
    }

    private Boolean colliderCheck(EntityObject e1, EntityObject e2)
    {
        float ex1 = e1.getBody().getPosition().x * Const.PPM - (e1.getWidth() / 2);
        float ey1 = e1.getBody().getPosition().y * Const.PPM - (e1.getHeight() / 2);

        float ex2 = e2.getBody().getPosition().x * Const.PPM - (e2.getWidth() / 2);
        float ey2 = e2.getBody().getPosition().y * Const.PPM - (e2.getHeight() / 2);

        float dst = Vector2.dst(ex1, ey1, ex2, ey2);
        return dst >= 1 && dst <= 23;
    }
}
