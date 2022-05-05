package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;

public class Weapon extends Entity
{
    public Weapon(float x, float y, int width, int height, Sprite sprite, Body body)
    {
        super(x,y,width,height,sprite,body);

    }
}
