package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class PlayerController implements IEntityProcessingService
{
    private PlayerCreation player;

    public void updateTexture(String fname)
    {

        Gdx.app.postRunnable(new Runnable()
        {
            @Override
            public void run()
            {
                File file = new File(this.getClass().getResource(fname).getPath());
                String path = file.getPath().substring(5);

                AssetLoader.INSTANCE.getAm().load(path, Texture.class);
                AssetLoader.INSTANCE.getAm().finishLoading();

                Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
                player.getPlayer().setSprite(sprite);
            }
        });

    }

    @Override
    public void update(GameWorld world)
    {

    }
}
