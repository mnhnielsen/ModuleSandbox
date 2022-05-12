package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.*;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IBulletService.class)})
public class BulletController implements IBulletService
{
    @Override
    public Entity createBullet(float x, float y, float speedX, float speedY, int width, int height, String textureName, GameWorld gameWorld)
    {
        File file = new File(this.getClass().getResource(textureName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();

        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, false, 10000, LibWorld.INSTANCE.getWorld(), ContactType.BULLET);
        Entity bullet = new Bullet(x, y, speedX, speedY, width, height, sprite, body);
        return bullet;
    }
}
