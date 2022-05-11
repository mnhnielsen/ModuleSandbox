package org.example;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.data.parts.HealthPart;
import org.example.helper.Const;
import org.example.helper.ContactType;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.Objects;

@ServiceProviders(value = {@ServiceProvider(service = IContactListener.class)})

public class GameContactListener implements ContactListener, IContactListener
{
    //private final Lookup lookup = Lookup.getDefault();
    private GameWorld gameWorld = GameWorld.INSTANCE;
    private Entity enemyHit = null;


    //private IEntityProcessingService iEntityProcessingService = lookup.lookup(IEntityProcessingService.class);
    @Override
    public void beginContact(Contact contact)
    {
        Fixture b = contact.getFixtureB();
        Fixture a = contact.getFixtureA();

        if (a == null || b == null) return;
        if (a.getUserData() == null || b.getUserData() == null) return;

        if (a.getUserData() == ContactType.ENEMY && b.getUserData() == ContactType.PLAYER)
        {
            for (Entity player : gameWorld.getEntities(Player.class))
            {
                player.getHealthPart().takeDamage(100);
            }
        }
        if (a.getUserData() == ContactType.ENEMY && b.getUserData() == ContactType.BULLET)
        {
            for (Entity enemy : gameWorld.getEntities(Enemy.class))
            {
                for (Entity bullet : gameWorld.getEntities(Bullet.class))
                {
                    if (colliderCheck(enemy, bullet))
                    {
                        System.out.println("Hit: " + enemy.getID());
                        enemyHit = enemy;
                        gameWorld.addObjectForDeletion(enemyHit);
                        gameWorld.addObjectForDeletion(bullet);
                    }
                }
            }
        }
        if (a.getUserData() == ContactType.OBSTACLE && b.getUserData() == ContactType.BULLET)
        {
            Entity player = gameWorld.getEntities(Player.class).get(0);
            player.getHealthPart().takeDamage(100);
        }
    }

    private Boolean colliderCheck(Entity e1, Entity e2)
    {
        float ex1 = e1.getBody().getPosition().x * Const.PPM - (e1.getWidth() / 2);
        float ey1 = e1.getBody().getPosition().y * Const.PPM - (e1.getHeight() / 2);

        float ex2 = e2.getBody().getPosition().x * Const.PPM - (e2.getWidth() / 2);
        float ey2 = e2.getBody().getPosition().y * Const.PPM - (e2.getHeight() / 2);

        float dst = Vector2.dst(ex1, ey1, ex2, ey2);
        return dst >= 1 && dst <= 33;
    }

    @Override
    public Entity delteObject()
    {
        return enemyHit;
    }

    @Override
    public void endContact(Contact contact)
    {

    }

    @Override
    public void preSolve(Contact contact, Manifold manifold)
    {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse contactImpulse)
    {

    }

    @Override
    public GameContactListener contactListener()
    {
        return this;
    }
}
