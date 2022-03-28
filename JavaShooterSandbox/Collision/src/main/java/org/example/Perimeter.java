package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.GameScreen;

public class Perimeter
{

    private float x, y;
    private int width, height;
    private String filename;
    private Sprite sprite;
    private GameScreen gameScreen;
    private Body body;

    public Perimeter(float x, float y, int width, int height, Body body, String filename, Sprite sprite, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.body = body;
        this.filename = filename;
        this.gameScreen = gameScreen;
        this.sprite = sprite;
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    public Sprite getSprite()
    {
        return sprite;
    }
}
