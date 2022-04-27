package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.example.data.GameWorld;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IGamePluginService.class)})

public class MapCreation extends Game implements IGamePluginService{

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;



    @Override
    public void create() {

        TmxMapLoader loader = new TmxMapLoader();
        map =  loader.load("resources/org.example/map.tmx");

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    @Override
    public void start(GameWorld world) {

    }

    @Override
    public void stop(GameWorld world) {

    }

}
