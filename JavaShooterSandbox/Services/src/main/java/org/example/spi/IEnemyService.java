package org.example.spi;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;


public interface IEnemyService
{
    void update();
    void enemy(int numberOfEnemies, GameScreen gameScreen, GameWorld gameWorld);
    void render(SpriteBatch bach);
}
