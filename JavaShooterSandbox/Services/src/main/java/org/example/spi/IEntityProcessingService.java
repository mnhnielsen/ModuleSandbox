package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;

public interface IEntityProcessingService
{
    void update(GameWorld world, SpriteBatch batch);
    Vector2 position();

}
