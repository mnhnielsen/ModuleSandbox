package org.example;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;

public class AstarCreation {

    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer layer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(1);
    private NavTmxMapLoader nav = new NavTmxMapLoader();


    public void AstarFinder(){

    }

}
