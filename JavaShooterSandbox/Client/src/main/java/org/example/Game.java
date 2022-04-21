package org.example;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.helper.LibWorld;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener
{
    private static OrthographicCamera cam;
    private final Lookup lookup = Lookup.getDefault();
    private GameWorld gameWorld = new GameWorld();
    private LibWorld world;
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();

    private Lookup.Result<IGamePluginService> result;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;


    @Override
    public void create()
    {

        debugRenderer = new Box2DDebugRenderer();
        world = new LibWorld();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances())
        {
            plugin.start(gameWorld);
            gamePlugins.add(plugin);
        }
    }

    @Override
    public void resize(int i, int i1)
    {

    }

    private void camUpdate()
    {
        world.getWorld().step(1 / 60f, 6, 2);
        //this.cam.position.set(lookup.lookup(IEntityProcessingService.class).position().x, lookup.lookup(IEntityProcessingService.class).position().y,0);
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }

    private void update()
    {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
        {
            entityProcessorService.update(gameWorld, batch);
        }
    }

    @Override
    public void render()
    {

        camUpdate();
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        update();
        //debugRenderer.render(world.getWorld(),cam.combined.scl(Const.PPM));
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
