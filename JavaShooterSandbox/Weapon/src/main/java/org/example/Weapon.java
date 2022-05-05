package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;
import org.example.data.parts.HealthPart;

public class Weapon extends Entity
{
    public Weapon(float x, float y, float speed, int width, int height, Body body, Sprite sprite, HealthPart healthPart){
        super(x, y, speed, width, height, body, sprite, healthPart);
    }
}
