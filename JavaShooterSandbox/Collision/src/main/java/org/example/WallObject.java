package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.EntityObject;
import org.example.helper.GameScreen;

public class WallObject extends EntityObject
{
    public WallObject(float x, float y, int width, int height, Body body, String filename, Sprite sprite, GameScreen gameScreen){
        super(x, y, width, height, body, filename, sprite, gameScreen);
    }
}
