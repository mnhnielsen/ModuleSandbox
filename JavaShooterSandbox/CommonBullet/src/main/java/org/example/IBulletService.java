package org.example;

import org.example.data.Entity;
import org.example.data.GameWorld;

public interface IBulletService
{
    Entity createBullet(float x, float y, float speedX, float speedY, int width, int height, String textureName, GameWorld gameWorld);
}
