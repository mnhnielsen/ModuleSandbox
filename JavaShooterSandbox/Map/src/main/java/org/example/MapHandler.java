package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.example.spi.IMapService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@ServiceProviders(value = {@ServiceProvider(service = IMapService.class)})

public class MapHandler implements IMapService
{
    private Lookup.Result<IGamePluginService> result;
    private final List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();


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
        if (world.getEntities(Enemy.class).size() == 0)
        {
            updateMapTexture("map2.png").draw(batch);

        }
    }

    @Override
    public void spawnEnemies(int amount)
    {
        
    }


}
