package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.example.data.GameWorld;

public interface IMapService
{
    void mapBackground(GameWorld world, SpriteBatch batch);
    void render();
    void initialRender();
    TiledMap getMap();
}
