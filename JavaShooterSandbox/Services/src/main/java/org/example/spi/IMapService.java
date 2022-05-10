package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.example.data.GameWorld;

public interface IMapService
{
    void mapBackground(GameWorld world, SpriteBatch batch);
}
