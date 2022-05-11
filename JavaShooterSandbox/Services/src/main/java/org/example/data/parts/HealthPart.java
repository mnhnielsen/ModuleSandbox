package org.example.data.parts;

import org.example.data.Entity;

public class HealthPart
{
    private int health;
    private boolean isDead;
    private boolean isHit = false, tempDead;


    public HealthPart(int health)
    {
        this.health = health;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setDead(boolean dead)
    {
        isDead = dead;
    }

    public boolean isHit()
    {
        return isHit;
    }

    public void takeDamage(int damage)
    {
        isHit = true;
        health -= damage;
        if (health <= 0)
            isDead = true;

    }

    public boolean dead()
    {
        return isDead;
    }
}
