package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.GameScreen;

public interface IEnemyService
{
    void update();
    void render(SpriteBatch batch);
    void enemy(GameScreen gameScreen);
}
