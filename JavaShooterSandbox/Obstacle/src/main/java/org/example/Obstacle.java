package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;

public class Obstacle extends Entity
{
    public Obstacle(float x, float y, int width, int height, Body body, Sprite sprite)
    {
        super(x, y, width, height, body, sprite);
    }
}
