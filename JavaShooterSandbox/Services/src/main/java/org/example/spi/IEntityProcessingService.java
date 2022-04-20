package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.data.GameWorld;

public interface IEntityProcessingService
{
    void update(GameWorld world, SpriteBatch batch);

}
