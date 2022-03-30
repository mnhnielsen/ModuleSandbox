package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.BulletEntity;
import org.example.helper.GameScreen;

public interface IBulletService
{
    BulletEntity createBullet(float x, float y,float speed, int width, int height, String textureName, GameScreen gameScreen);
}
