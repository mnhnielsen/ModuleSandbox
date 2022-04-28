package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;

public class Bullet extends Entity
{
    public Bullet(float x, float y, float speedX, float speedY, int width, int height, Sprite sprite, Body body)
    {
        super(x, y, speedX, speedY, width, height, sprite, body);
    }
}
