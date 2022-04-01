package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.*;
import org.example.spi.IBulletService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

@ServiceProviders(value = {@ServiceProvider(service = IBulletService.class)})
public class BulletController implements IBulletService
{
    private GameScreen gameScreen;


    @Override
    public EntityObject createBullet(float x, float y, float speedX, float speedY, int width, int height, String textureName, GameScreen gameScreen, GameWorld gameWorld)
    {
        int minusAccuracy = 2, maxAccuracy = 5;
        int accuracy = new Random().nextInt(maxAccuracy - minusAccuracy) + minusAccuracy;
        this.gameScreen = gameScreen;
        File file = new File(this.getClass().getResource(textureName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.BULLET);
        body.setLinearVelocity(speedX,speedY);
        EntityObject entityObject = new EntityObject(x, y, speedX, speedY, width, height, sprite, gameScreen, body);
        return entityObject;
    }
}
