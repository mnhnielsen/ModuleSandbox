package org.example;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;

public class Map extends Entity {

    TiledMap tiledMap;

    String id;

    Body body;
    OrthogonalTiledMapRenderer renderer;
    public Map (TiledMap tiledMap, OrthogonalTiledMapRenderer renderer){
        this.tiledMap = tiledMap;
        this.renderer = renderer;
        this.body = body;

    }


    public String getId() {
        return id;
    }


}
