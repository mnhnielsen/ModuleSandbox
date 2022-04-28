package org.example.data.parts;

import org.example.data.Entity;

public class TimerPart implements EntityPart
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

    @Override
    public void process(Entity entity)
    {
        if (expiration > 0)
        {
            //reduceExpiration(gameData.getDelta());
        }

        if (expiration <= 0)
        {
            HealthPart lifePart = entity.getPart(HealthPart.class);
            lifePart.setHealth(0);

        }
    }

}
