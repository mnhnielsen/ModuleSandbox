package org.example;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.GameScreen;
import org.example.helper.HealthPart;

public class EnemyObject
{
    private float x, y, speed, velY, velX;
    private int width, height;
    private String filename;
    private Sprite sprite;
    private GameScreen gameScreen;
    private Body body;
    private HealthPart healthPart;

    public EnemyObject(float x, float y, float speed, int width, int height, Body body, Sprite sprite, GameScreen gameScreen, HealthPart healthPart)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.body = body;
        this.gameScreen = gameScreen;
        this.sprite = sprite;
        this.healthPart = healthPart;
    }

    public HealthPart getHealthPart()
    {
        return healthPart;
    }

    public void setHealthPart(HealthPart healthPart)
    {
        this.healthPart = healthPart;
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

    public float getVelY()
    {
        return velY;
    }

    public void setVelY(float velY)
    {
        this.velY = velY;
    }

    public float getVelX()
    {
        return velX;
    }

    public void setVelX(float velX)
    {
        this.velX = velX;
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
