package org.example.spi;

import org.example.helper.EntityObject;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

public interface IBulletService
{
    EntityObject createBullet(float x, float y, float speedX, float speedY, int width, int height, String textureName, GameScreen gameScreen, GameWorld gameWorld);
}
