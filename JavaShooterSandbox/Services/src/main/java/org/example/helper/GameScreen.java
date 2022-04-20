package org.example.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import org.example.spi.*;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class GameScreen extends ScreenAdapter
{
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private World world;
    private final Lookup lookup = Lookup.getDefault();
    private final IPlayerService playerService = lookup.lookup(IPlayerService.class);
    private final IEnemyService enemyService = lookup.lookup(IEnemyService.class);

    //Testing updatecenter stuff for player module.
    private Lookup.Result<IEnemyService> result;
    private List<IEnemyService> gamePlugins = new CopyOnWriteArrayList<>();


    private final ICollisionDetector collisionDetector = Lookup.getDefault().lookup(ICollisionDetector.class);
    private Box2DDebugRenderer box2DDebugRenderer;
    private GameWorld gameWorld = new GameWorld();
    private Sprite sprite;
    private GameScreen gm = this;

    public GameWorld getGameWorld()
    {
        return gameWorld;
    }

    public GameScreen(OrthographicCamera cam)
    {
        result = lookup.lookupResult(IEnemyService.class);
        result.addLookupListener(lookupListener);
        result.allItems();
        this.cam = cam;
       // this.cam.position.set(new Vector3(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);
        this.box2DDebugRenderer = new Box2DDebugRenderer();

        File file = new File(this.getClass().getResource("bgimage.png").getPath());
        String path = file.getPath().substring(5);
        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));

        IContactListener collisionService = Lookup.getDefault().lookup(IContactListener.class);
        world.setContactListener(collisionService.contactListener(this));
        System.out.println("test");

        playerService.player(40, Boot.INSTANCE.getScreenHeight() / 2, this);

        enemyService.enemy(1, this, gameWorld);

    }

    public void update()
    {
        world.step(1 / 60f, 6, 2);
        //Delete bodies here
        if (collisionDetector.deleteObject()!= null)
            collisionDetector.deleteObject().removeBody();

        this.cam.position.set(playerService.getX(),playerService.getY(),0);

        cam.update();
        batch.setProjectionMatrix(cam.combined);

        playerService.update();

        for (IEnemyService eService : Lookup.getDefault().lookupAll(IEnemyService.class))
            eService.update();
        
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();

    }

    @Override
    public void render(float delta)
    {
        update();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        sprite.draw(batch);
        playerService.render(batch);
        enemyService.render(batch);
        //box2DDebugRenderer.render(world, cam.combined.scl(Const.PPM));
        batch.end();
    }


    public World getWorld()
    {
        return world;
    }

    public IPlayerService getPlayerService()
    {
        return playerService;
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IEnemyService> updated = result.allInstances();

            for (IEnemyService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.enemy(1, gm,gameWorld);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IEnemyService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    for(EntityObject enemy : gameWorld.getEntities())
                        gameWorld.removeEntity(enemy);

                    gamePlugins.remove(gs);
                }
            }
        }

    };

}
