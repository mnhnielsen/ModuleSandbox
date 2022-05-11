package org.example;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.example.data.GameWorld;
import org.example.helper.CamController;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapSpi;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IMapSpi.class)})
public class MapCreation implements IMapSpi
{


    private TiledMap map;


    private TiledMapTileLayer layer;


    private OrthogonalTiledMapRenderer renderer;
    //protected Map tiledMap = new Map(map, renderer);

    public MapCreation()
    {
        map = new TmxMapLoader().load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        renderer = new OrthogonalTiledMapRenderer(map);
        layer =  (TiledMapTileLayer) map.getLayers().get("colission");

    }

    public void initrenderer()
    {
        map = new TmxMapLoader().load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        renderer = new OrthogonalTiledMapRenderer(map);
        layer =  (TiledMapTileLayer) map.getLayers().get("colission");
    }

    public boolean isCellBlocked(float x, float y) {
        TiledMapTileLayer.Cell cell = null;
        boolean blocked = false;

        try {
            cell = layer.getCell((int) (x / layer.getTileWidth()), (int) (y / layer.getTileHeight()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (cell != null && cell.getTile() != null) {
            if (cell.getTile().getProperties().containsKey("blocked")) {
                blocked = true;
            }
        }
        return blocked;
    }



    @Override
    public void render()
    {
        renderer.setView(CamController.INSTANCE.getCam());
        renderer.render();
    }


    public TiledMap getMap()
    {
        return map;
    }

    public TiledMapTileLayer getLayer()
    {
        return layer;
    }

    public OrthogonalTiledMapRenderer getRenderer()
    {
        return renderer;
    }



}
