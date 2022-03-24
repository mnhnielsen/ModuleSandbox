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
    private Sprite sprite;
    private Perimeter upperWall, lowerWall, leftWall, rightWall;

    @Override
    public void obstacle(GameScreen gameScreen)
    {

        upperWall = createPerimeter(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() - 32, Boot.INSTANCE.getScreenWidth(), 64, "color.png", gameScreen);
        lowerWall = createPerimeter(Boot.INSTANCE.getScreenWidth() / 2, 32, Boot.INSTANCE.getScreenWidth(), 64, "color.png", gameScreen);
        leftWall = createPerimeter(16, Boot.INSTANCE.getScreenHeight() / 2,32,Boot.INSTANCE.getScreenHeight(),"color.png",gameScreen);
        rightWall = createPerimeter(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight() / 2,32,Boot.INSTANCE.getScreenHeight(),"color.png",gameScreen);


    }

    private Perimeter createPerimeter(float spawnX, float spawnY, int width, int height, String textureName, GameScreen gameScreen)
    {
        File file = new File(this.getClass().getResource(textureName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));

        body = BodyHelper.createBody(spawnX, spawnY, width, height, true, 0, gameScreen.getWorld(), ContactType.OBSTACLE);

        Perimeter p = new Perimeter(spawnX, spawnY, width, height, textureName, gameScreen);
        System.out.println("Loaded");
        return p;

    }


    public void render(SpriteBatch batch)
    {
        //batch.draw(sprite, x - (width / 2), y - (height / 2), width, height);
    }
}
