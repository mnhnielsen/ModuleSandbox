package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.Enemy;
import org.example.spi.IMapService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IMapService.class)})

public class MapHandler implements IMapService
{
    private EnemyCreation enemyCreation = new EnemyCreation();
    private boolean map1 = true;

    private Sprite updateMapTexture(String fileName)
    {
        File file = new File(this.getClass().getResource(fileName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();
        return new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
    }

    @Override
    public void mapBackground(GameWorld world, SpriteBatch batch)
    {
       // System.out.println(world.getEntities(Enemy.class).size());
        if (world.getEntities(Enemy.class).size() == 0 && map1)
        {
            //updateMapTexture("map2.png").draw(batch);
            spawnEnemies(1);
            map1 = false;
        }
    }

    @Override
    public void render()
    {

    }

    @Override
    public void initialRender()
    {

    }

    @Override
    public TiledMap getMap()
    {
        return null;
    }

    private void spawnEnemies(int numberOfEnemies)
    {
        enemyCreation.spawnEnemies(numberOfEnemies);
        enemyCreation.start(GameWorld.INSTANCE);
        //System.out.println("Spawned " + numberOfEnemies + " enemies...");
    }

}
