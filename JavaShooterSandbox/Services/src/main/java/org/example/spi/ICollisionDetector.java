package org.example.spi;

import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

public interface ICollisionDetector
{
    void process(GameScreen gameScreen,GameWorld gameWorld);
}
