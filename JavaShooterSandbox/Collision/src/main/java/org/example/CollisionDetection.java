package org.example;

import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.data.parts.HealthPart;
import org.example.helper.Const;
import org.example.spi.ICollisionDetection;

public class CollisionDetection implements ICollisionDetection
{

    Entity entity = null;
    @Override
    public void process(GameWorld gameWorld)
    {
//        for (Entity entityObject : gameWorld.getEntities())
//        {
//            for (Entity collisionDetection : gameWorld.getBulletEntities())
//            {
//                if (this.colliderCheck(entityObject, collisionDetection))
//                {
//                    HealthPart entityLife = entityObject.getHealthPart();
//                    gameWorld.addObjectForDeletion(collisionDetection);
//
//                    if (entityLife != null)
//                    {
//                        entityLife.setDead(true);
//                        if (entityLife.isDead())
//                        {
//                            entity = entityObject;
//                            gameWorld.addObjectForDeletion(entity);
//                        }
//                    }
//                }
//            }
//        }
    }


    @Override
    public Entity deleteObject()
    {
        return entity;
    }

    private Boolean colliderCheck(Entity e1, Entity e2)
    {
        float ex1 = e1.getBody().getPosition().x * Const.PPM - (e1.getWidth() / 2);
        float ey1 = e1.getBody().getPosition().y * Const.PPM - (e1.getHeight() / 2);

        float ex2 = e2.getBody().getPosition().x * Const.PPM - (e2.getWidth() / 2);
        float ey2 = e2.getBody().getPosition().y * Const.PPM - (e2.getHeight() / 2);

        float dst = Vector2.dst(ex1, ey1, ex2, ey2);
        return dst >= 1 && dst <= 23;
    }
}
