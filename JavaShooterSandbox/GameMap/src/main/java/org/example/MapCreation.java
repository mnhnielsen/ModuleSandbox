package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.example.data.GameWorld;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapSpi;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IMapSpi.class)})


public class MapCreation implements IGamePluginService, IMapSpi {

    public MapCreation() {

    }

    private TiledMap map;


    TiledMapTileLayer layer;

    protected Map tiledMap = createMap();


    OrthogonalTiledMapRenderer renderer;

    static OrthographicCamera cam = new OrthographicCamera();



    public OrthographicCamera create() {
      return cam;
    }
    /*@Override
    public void initialCam(){
        cam = new OrthographicCamera();
    }*/

    public Map createMap() {


        map = new TmxMapLoader().load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        renderer = new OrthogonalTiledMapRenderer(map);
        cam = new OrthographicCamera();

        return new Map(map, renderer);
    }

    @Override
    public void start(GameWorld world) {
        world.addEntity(tiledMap);

    }

    @Override
    public void stop(GameWorld world) {
        world.removeEntity(tiledMap);
    }

    @Override
    public void render() {
        renderer.setView(cam);
        renderer.render();
    }


    public TiledMap getMap () {
        return map;
    }

    public TiledMapTileLayer getLayer() {
        return layer;
    }

    public OrthogonalTiledMapRenderer getRenderer() {
        return renderer;
    }

    public TiledMap getTiledMap() {
        return map;
    }
}
