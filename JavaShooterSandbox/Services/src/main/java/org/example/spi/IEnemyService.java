package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.GameScreen;
import org.example.helper.GameWorld;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public interface IEnemyService
{
    void update();
    void enemy(int numberOfEnemies, GameScreen gameScreen, GameWorld gameWorld);
}
