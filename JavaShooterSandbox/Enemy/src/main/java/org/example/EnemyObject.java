package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.EntityObject;
import org.example.helper.GameScreen;
import org.example.helper.HealthPart;

public class EnemyObject extends EntityObject
{
    public EnemyObject(float x, float y, int speed, int width, int height, Body body, Sprite sprite, GameScreen gameScreen, HealthPart healthPart)
    {
        super(x,y,speed,width,height,body,sprite,gameScreen,healthPart);
    }
}
