package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.EntityObject;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

public class BulletObject extends EntityObject
{
    public BulletObject(float x, float y, float speed, int width, int height, Sprite sprite, GameScreen gameScreen, Body body)
    {
        super(x, y, speed, width, height, sprite, gameScreen, body);
    }
}
