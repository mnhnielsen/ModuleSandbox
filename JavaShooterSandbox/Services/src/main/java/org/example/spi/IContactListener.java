package org.example.spi;

import org.example.helper.GameContactListener;
import org.example.helper.GameScreen;

public interface IContactListener
{
    GameContactListener contactListener(GameScreen gameScreen);
}
