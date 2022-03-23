package org.example.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.World;
import org.example.spi.ICollisionService;
import org.example.spi.IEnemyService;
import org.example.spi.IObstacleService;
import org.example.spi.IPlayerService;
import org.openide.util.Lookup;



public class GameScreen extends ScreenAdapter
{
    private OrthographicCamera cam;
    private SpriteBatch batch;
    private World world;
    private final Lookup lookup = Lookup.getDefault();
    IPlayerService playerService = lookup.lookup(IPlayerService.class);
    IEnemyService enemyService = lookup.lookup(IEnemyService.class);
    ICollisionService collisionService = lookup.lookup(ICollisionService.class);
    IObstacleService obstacleService = lookup.lookup(IObstacleService.class);


    public GameScreen(OrthographicCamera cam)
    {
        this.cam = cam;
        this.cam.position.set(new Vector3(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() / 2, 0));
        this.batch = new SpriteBatch();
        this.world = new World(new Vector2(0, 0), false);

        playerService.player(16, Boot.INSTANCE.getScreenHeight() / 2, this);
        enemyService.enemy(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight() / 2, this);
        obstacleService.obstacle(32,this);

    }


    public void update()
    {
        world.step(1 / 60f, 6, 2);
        cam.update();
        batch.setProjectionMatrix(cam.combined);

        playerService.update();
        enemyService.update();

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

        playerService.render(batch);
        enemyService.render(batch);
        obstacleService.render(batch);

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
}
