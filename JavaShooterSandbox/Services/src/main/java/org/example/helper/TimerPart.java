package org.example.helper;

import com.badlogic.gdx.Gdx;

public class TimerPart
{
    private float expiration;

    public TimerPart(float expiration)
    {
        this.expiration = expiration;
    }

    public float getExpiration()
    {
        return expiration;
    }

    public void setExpiration(float expiration)
    {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta)
    {
        this.expiration -= delta;
    }

    public void process(EntityObject entityObject){
        if (expiration > 0)
            reduceExpiration(Gdx.graphics.getDeltaTime());
        if (expiration <= 0)
            entityObject.getHealthPart().setHealth(0);
    }
}
