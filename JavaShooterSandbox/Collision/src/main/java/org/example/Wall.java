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
import java.util.ArrayList;

@ServiceProviders(value = {@ServiceProvider(service = IObstacleService.class)
})
public class Wall implements IObstacleService
{

    protected GameScreen gameScreen;
    private EntityObject upperWall, lowerWall, leftWall, rightWall;
    private ArrayList<EntityObject> walls = new ArrayList<>();


    @Override
    public void obstacle(GameScreen gameScreen)
    {
        //Game border Walls
        upperWall = createPerimeter(Boot.INSTANCE.getScreenWidth() / 2, Boot.INSTANCE.getScreenHeight() - 32, Boot.INSTANCE.getScreenWidth(), 64, "color.png", gameScreen);
        lowerWall = createPerimeter(Boot.INSTANCE.getScreenWidth() / 2, 32, Boot.INSTANCE.getScreenWidth(), 64, "color.png", gameScreen);
        leftWall = createPerimeter(16, Boot.INSTANCE.getScreenHeight() / 2, 32, Boot.INSTANCE.getScreenHeight(), "color.png", gameScreen);
        rightWall = createPerimeter(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight() / 2, 32, Boot.INSTANCE.getScreenHeight(), "color.png", gameScreen);
        walls.add(upperWall);
        walls.add(lowerWall);
        walls.add(leftWall);
        walls.add(rightWall);
    }


    private EntityObject createPerimeter(float spawnX, float spawnY, int width, int height, String textureName, GameScreen gameScreen)
    {
        this.gameScreen = gameScreen;
        File file = new File(this.getClass().getResource(textureName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(spawnX, spawnY, width, height, true, 0, gameScreen.getWorld(), ContactType.OBSTACLE);
        return new EntityObject(spawnX, spawnY, width, height, body, textureName, sprite, gameScreen);
    }


    public void render(SpriteBatch batch)
    {
        for (EntityObject perimeter : walls)
            batch.draw(perimeter.getSprite(), perimeter.getX() - (perimeter.getWidth() / 2), perimeter.getY() - (perimeter.getHeight() / 2), perimeter.getWidth(), perimeter.getHeight());
    }
}
