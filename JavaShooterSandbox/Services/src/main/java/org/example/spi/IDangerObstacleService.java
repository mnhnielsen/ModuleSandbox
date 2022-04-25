package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

public interface IDangerObstacleService
{
    void obstacle(int obstacleCount, GameScreen gameScreen, GameWorld gameWorld);

    void render(SpriteBatch batch);
}
