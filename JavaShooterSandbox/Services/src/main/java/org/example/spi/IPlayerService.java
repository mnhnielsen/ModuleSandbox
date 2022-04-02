package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.GameScreen;

public interface IPlayerService
{
    void update();
    void player(float x, float y, GameScreen gameScreen);
    void render(SpriteBatch batch);
    float getX();
    float getY();
    void takeDamage(int damage);


}
