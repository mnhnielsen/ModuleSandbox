package org.example;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.loaders.resolvers.ExternalFileHandleResolver;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.ImageResolver;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.LibWorld;
import org.example.spi.ICollisionDetection;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapSpi;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import sun.awt.X11.XErrorHandler;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener {

    private final Lookup lookup = Lookup.getDefault();
    private final GameWorld gameWorld = new GameWorld();
    private final IContactListener contactListener = lookup.lookup(IContactListener.class);
    private LibWorld world;
    private final List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();

    private final IMapSpi mapSpi = lookup.lookup(IMapSpi.class);

    private Lookup.Result<IGamePluginService> result;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;

    TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    float unitScale = 1 / 32f;



    @Override
    public void create() {


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        debugRenderer = new Box2DDebugRenderer();
        world = new LibWorld();
        mapSpi.create().setToOrtho(false, w, h);
        batch = new SpriteBatch();

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        LibWorld.INSTANCE.getWorld().setContactListener(contactListener.contactListener());


        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameWorld);
            gamePlugins.add(plugin);
        }

    }

    @Override
    public void resize(int width, int height)
    {
        mapSpi.create().viewportWidth = width;
        mapSpi.create().viewportHeight = height;
        mapSpi.create().update();
    }


    private void camUpdate()
    {

        world.getWorld().step(1 / 60f, 6, 2);
        mapSpi.create().position.set(lookup.lookup(IEntityProcessingService.class).position().x, lookup.lookup(IEntityProcessingService.class).position().y,0);
        mapSpi.create().update();
        batch.setProjectionMatrix(mapSpi.create().combined);

    }

    private void update()
    {

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
        {
            entityProcessorService.update(gameWorld, batch);
        }
        for (ICollisionDetection postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameWorld);
        }
    }

    @Override
    public void render()
    {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mapSpi.render();
        camUpdate();
        batch.begin();
        update();
        batch.end();

        }


    @Override
    public void pause()
    {

    }

    @Override
    public void resume()
    {

    }

    @Override
    public void dispose()
    {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices()
    {
        return lookup.lookupAll(IEntityProcessingService.class);
    }
    private Collection<? extends ICollisionDetection> getPostEntityProcessingServices() {
        return lookup.lookupAll(ICollisionDetection.class);
    }

    private final LookupListener lookupListener = new LookupListener()
    {
        @Override
        public void resultChanged(LookupEvent le)
        {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated)
            {
                // Newly installed modules
                if (!gamePlugins.contains(us))
                {
                    us.start(gameWorld);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins)
            {
                if (!updated.contains(gs))
                {
                    gs.stop(gameWorld);
                    gamePlugins.remove(gs);
                }
            }
        }

    };
}
