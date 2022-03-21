package org.example;

import com.badlogic.gdx.physics.box2d.*;
import org.example.helper.ContactType;
import org.example.helper.GameScreen;
import org.example.spi.ICollisionService;
import org.example.spi.IPlayerService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = ICollisionService.class)
})
public class GameContactListener implements ContactListener, ICollisionService
{
    private GameScreen gameScreen;


    @Override
    public void contactListener(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    @Override
    public void beginContact(Contact contact)
    {
        Fixture a = contact.getFixtureA();
        Fixture b = contact.getFixtureB();

        if (a == null || b == null) return;
        if (a.getUserData() == null || b.getUserData() == null) return;

        if (a.getUserData() == ContactType.ENEMY || b.getUserData() == ContactType.ENEMY){
            //Enemy - Player
            if (a.getUserData() == ContactType.PLAYER || b.getUserData() == ContactType.PLAYER){
                //Player damage -1
            }
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

}
