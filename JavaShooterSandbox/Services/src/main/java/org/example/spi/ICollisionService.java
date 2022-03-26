package org.example.spi;

import com.badlogic.gdx.physics.box2d.ContactListener;
import org.example.helper.GameContactListener;
import org.example.helper.GameScreen;

public interface ICollisionService
{
    GameContactListener contactListener(GameScreen gameScreen);
}
