package org.example.spi;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public interface IMapSpi {

    void render();

    OrthographicCamera create();

    //void initialCam();
}
