package org.example.helper;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class BulletEntity
{
    private float x,y,speed;
    private int width, height;
    private String filename;
    private Sprite sprite;
    private GameScreen gameScreen;
    private Body body;

    public BulletEntity(float x, float y, float speed, int width, int height, Sprite sprite, GameScreen gameScreen, Body body)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        this.gameScreen = gameScreen;
        this.body = body;
    }

    public float getX()
    {
        return x;
    }

    public void setX(float x)
    {
        this.x = x;
    }

    public float getY()
    {
        return y;
    }

    public void setY(float y)
    {
        this.y = y;
    }

    public float getSpeed()
    {
        return speed;
    }

    public void setSpeed(float speed)
    {
        this.speed = speed;
    }

    public int getWidth()
    {
        return width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }

    public int getHeight()
    {
        return height;
    }

    public void setHeight(int height)
    {
        this.height = height;
    }

    public String getFilename()
    {
        return filename;
    }

    public void setFilename(String filename)
    {
        this.filename = filename;
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public GameScreen getGameScreen()
    {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
    }

    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }
}
