package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.ContactListener;
import org.example.helper.GameScreen;

public interface IObstacleService
{
    void obstacle(float y, GameScreen gameScreen);
    void render(SpriteBatch batch);

}
