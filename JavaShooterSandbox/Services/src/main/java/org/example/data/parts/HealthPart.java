package org.example.data.parts;

import org.example.data.Entity;

public class HealthPart implements EntityPart
{
    public HealthPart(int life)
    {
        this.life = life;
    }

    private boolean dead = false;
    private int life;
    private boolean isHit = false;


    public int getLife()
    {
        return life;
    }

    public void setLife(int life)
    {
        this.life = life;
    }

    public boolean isHit()
    {
        return isHit;
    }

    public void setIsHit(boolean isHit)
    {
        this.isHit = isHit;
    }

    public boolean isDead()
    {
        return dead;
    }

    @Override
    public void process(Entity entity)
    {
        if (isHit)
        {
            life = -1;
            isHit = false;
        }
        if (life <= 0)
        {
            dead = true;
        }
    }
}
