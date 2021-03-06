package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.GameScreen;

public interface IPlayerService
{
    void update();
    void render(SpriteBatch batch);
    void player(float x, float y, GameScreen gameScreen);
    float getX();
    float getY();


}
