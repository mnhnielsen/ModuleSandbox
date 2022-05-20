package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.CamController;
import org.example.spi.IMapService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.ArrayList;

@ServiceProviders(value = {@ServiceProvider(service = IMapService.class)})
public class MapCreation implements IMapService
{


    private TiledMap map;

    private TiledMapTileLayer layer;


    private OrthogonalTiledMapRenderer renderer;
    //protected Map tiledMap = new Map(map, renderer);

    public MapCreation()
    {
        map = new TmxMapLoader().load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        renderer = new OrthogonalTiledMapRenderer(map);
        layer = (TiledMapTileLayer) map.getLayers().get(2);

    }

    @Override
    public void initialRender()
    {
        map = new TmxMapLoader().load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        renderer = new OrthogonalTiledMapRenderer(map);
        layer = (TiledMapTileLayer) map.getLayers().get(2);
    }

    public boolean isCellBlocked(float x, float y)
    {
        TiledMapTileLayer.Cell cell = null;
        boolean blocked = false;
        try
        {
            cell = layer.getCell((int) (x / layer.getTileWidth()), (int) (y / layer.getTileHeight()));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        if (cell != null && cell.getTile() != null)
        {
            if (cell.getTile().getProperties().containsKey("blocked"))
            {
                //System.out.println("blocked");
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

    @Override
    public void mapBackground(GameWorld world, SpriteBatch batch)
    {

    }

}
