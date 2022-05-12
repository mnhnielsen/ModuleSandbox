package org.example;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.Const;
import org.example.helper.LibWorld;
import org.example.helper.Player;
import org.example.spi.ICollisionDetection;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapService;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Game implements ApplicationListener
{
    private static OrthographicCamera cam;
    private final Lookup lookup = Lookup.getDefault();
    private final GameWorld gameWorld = new GameWorld();
    private final IContactListener contactListener = lookup.lookup(IContactListener.class);
    private LibWorld world;
    private final List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private Box2DDebugRenderer debugRenderer;
    private SpriteBatch batch;
    private Sprite sprite;


    @Override
    public void create()
    {

        File file = new File(this.getClass().getResource("map1.png").getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();
        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));

        debugRenderer = new Box2DDebugRenderer();
        world = new LibWorld();
        cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        LibWorld.INSTANCE.getWorld().setContactListener(contactListener.contactListener());

        for (IGamePluginService plugin : result.allInstances())
        {
            plugin.spawnEnemies(1);
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
        if (contactListener.delteObject() != null)
            contactListener.delteObject().removeBody();

        try
        {
            cam.position.set(gameWorld.getEntities(Player.class).get(0).getBody().getPosition().x * Const.PPM - (gameWorld.getEntities(Player.class).get(0).getWidth() / 2), gameWorld.getEntities(Player.class).get(0).getBody().getPosition().y * Const.PPM - (gameWorld.getEntities(Player.class).get(0).getHeight() / 2), 0);
            //lookup.lookup(IEntityProcessingService.class).position().x, lookup.lookup(IEntityProcessingService.class).position().y
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println();
        }
        cam.update();
        batch.setProjectionMatrix(cam.combined);
    }

    private void update()
    {
        System.out.println(gameWorld.getEntities(Player.class).size());
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices())
            entityProcessorService.update(gameWorld, batch);
        for (ICollisionDetection postEntityProcessorService : getPostEntityProcessingServices())
            postEntityProcessorService.process(gameWorld);


    }

    private void mapService()
    {
        for (IMapService mapService : getMapService())
            mapService.mapBackground(gameWorld, batch);
    }

    @Override
    public void render()
    {


        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        mapService();
        sprite.draw(batch);
        camUpdate();
        update();
        //debugRenderer.render(world.getWorld(),cam.combined.scl(32));
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

    private Collection<? extends IMapService> getMapService()
    {
        return lookup.lookupAll(IMapService.class);
    }

    private Collection<? extends ICollisionDetection> getPostEntityProcessingServices()
    {
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
