package org.example.helper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;
import org.example.data.parts.HealthPart;

public class Player extends Entity
{
    public Player(float x, float y, float speed, int width, int height, Body body, Sprite sprite, HealthPart healthPart)
    {
        super(x, y, speed, width, height, body, sprite, healthPart);
    }
}
