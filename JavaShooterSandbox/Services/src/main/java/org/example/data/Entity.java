package org.example.data;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.parts.EntityPart;
import org.example.data.parts.HealthPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable
{
    private final UUID ID = UUID.randomUUID();
    private float x;
    private float y;
    private float speed;
    private float speedY;
    private float speedX;
    private float velY;
    private float velX;
    private int width, height;
    private String filename;
    private Sprite sprite;
    private Body body;
    private HealthPart healthPart;
    private Map<Class, EntityPart> parts;

    //Enemy
    public Entity(float x, float y, float speed, int width, int height, Body body, Sprite sprite, HealthPart healthPart)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
        this.body = body;
        this.sprite = sprite;
        this.healthPart = healthPart;
        parts = new ConcurrentHashMap<>();
    }
    public Entity()
    {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part)
    {
        parts.put(part.getClass(), part);
    }


    public <E extends EntityPart> E getPart(Class partClass)
    {
        return (E) parts.get(partClass);
    }

    public String getID()
    {
        return ID.toString();
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


    public Body getBody()
    {
        return body;
    }

    public void setBody(Body body)
    {
        this.body = body;
    }

    public HealthPart getHealthPart()
    {
        return healthPart;
    }

    public void removeBody()
    {


    }

    public void setHealthPart(HealthPart healthPart)
    {
        this.healthPart = healthPart;
    }

    public float getSpeedY()
    {
        return speedY;
    }

    public float getSpeedX()
    {
        return speedX;
    }
}
