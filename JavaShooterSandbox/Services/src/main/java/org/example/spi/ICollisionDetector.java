package org.example.spi;

import org.example.helper.EntityObject;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

import javax.swing.text.html.parser.Entity;

public interface ICollisionDetector
{
    void process(GameScreen gameScreen,GameWorld gameWorld);
    void deleteBullets(GameScreen gameScreen,GameWorld gameWorld);
    EntityObject deleteObject();
}
