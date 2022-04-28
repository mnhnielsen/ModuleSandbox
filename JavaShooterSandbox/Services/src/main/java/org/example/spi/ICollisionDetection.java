package org.example.spi;

import org.example.data.Entity;
import org.example.data.GameWorld;

public interface ICollisionDetection
{
    void process(GameWorld gameWorld);
    //void deleteBullets(GameWorld gameWorld);
    Entity deleteObject();
}
