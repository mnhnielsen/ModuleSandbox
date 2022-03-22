package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IObstacleService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IObstacleService.class)
})
public class Wall implements IObstacleService
{

    private Body body;
    private float x, y;
    private int width, height;
    private Sprite sprite;

    @Override
    public void obstacle(float y, GameScreen gameScreen)
    {
        x = Boot.INSTANCE.getScreenWidth() / 2;
        this.y = y;
        width = Boot.INSTANCE.getScreenWidth();
        height = 64;

        File file = new File(this.getClass().getResource("color.png").getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        body = BodyHelper.createBody(x, y, width, height, true, 0, gameScreen.getWorld(), ContactType.OBSTACLE);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(sprite, x - (width / 2), y - (height / 2), width, height);
    }
}
