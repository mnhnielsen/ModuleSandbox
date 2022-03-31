package org.example;

import com.badlogic.gdx.math.Vector2;
import org.example.helper.*;
import org.example.spi.IBulletService;
import org.example.spi.ICollisionDetector;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = ICollisionDetector.class)})
public class CollisionDetector implements ICollisionDetector
{
    public void process(GameScreen gameScreen, GameWorld gameWorld)
    {
        for (EntityObject entityObject : gameWorld.getEntities())
        {
            System.out.println(entityObject.getID());

            for (EntityObject collisionDetection : gameWorld.getEntities())
            {


                //HealthPart entityLife = entityObject.getHealthPart();

                if (this.colliderCheck(entityObject, collisionDetection))
                {

                    //collisionDetection.getHealthPart().setHealth(0);

                    gameWorld.removeEntity(entityObject);
                    gameWorld.removeEntity(collisionDetection);
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

                     */
                }
            }
        }
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
