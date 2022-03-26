package org.example.helper;

public class HealthPart
{
    private int health;
    private boolean isDead;


    public HealthPart(int health)
    {
        this.health = health;
    }


    public void setHealth(int health)
    {
        this.health = health;
    }

    public void setDead(boolean dead)
    {
        isDead = dead;
    }


    public void takeDamage(int damage)
    {
        health -= damage;
        System.out.println(health);
        if (health <= 0)
            isDead = true;
    }

    public boolean dead()
    {
        return isDead;
    }
}
