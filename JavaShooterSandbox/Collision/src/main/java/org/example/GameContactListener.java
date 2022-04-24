package org.example;

import com.badlogic.gdx.physics.box2d.*;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.ContactType;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IContactListener.class)})

public class GameContactListener implements ContactListener, IContactListener
{
    //private final Lookup lookup = Lookup.getDefault();
    private GameWorld gameWorld = GameWorld.INSTANCE;

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
            System.out.println("Contact");
            for (Entity player : gameWorld.INSTANCE.getEntities(Player.class))
            {
                //Player take damage - this works here.
                //Need to configure Healthpart properly and reset the player - See dev branch for solution under PlayerController class in Player module.
                //Use Thread.Sleep(1250) for simulation dead effects
            }
        }
        if (a.getUserData() == ContactType.ENEMY && b.getUserData() == ContactType.BULLET)
        {
            //collisionDetector.process(gameScreen, gameScreen.getGameWorld());
        }
        if (a.getUserData() == ContactType.OBSTACLE && b.getUserData() == ContactType.BULLET)
        {
            //collisionDetector.deleteBullets(gameScreen, gameScreen.getGameWorld());
        }
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
