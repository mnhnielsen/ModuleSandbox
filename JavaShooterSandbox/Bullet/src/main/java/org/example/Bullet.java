package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.AssetLoader;
import org.example.helper.BodyHelper;
import org.example.helper.ContactType;
import org.example.helper.GameScreen;
import org.example.spi.IBulletService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IBulletService.class)
})
public class Bullet implements IBulletService
{
    float xPosition, yPosition;
    float width, height, speed;
    TextureRegion textureRegion;
    protected GameScreen gameScreen;
    protected Sprite sprite;
    protected Body body;

    @Override
    public void bullet(float xCentre, float yBottom, float width, float height, float movementSpeed, TextureRegion textureRegion, GameScreen gameScreen)
    {
        this.xPosition = xCentre - width / 2;
        this.yPosition = yBottom;
        this.width = width;
        this.height = height;
        this.speed = movementSpeed;
        this.textureRegion = textureRegion;
        this.gameScreen = gameScreen;

        File file = new File(this.getClass().getResource("bullet.png").getPath());
        String path = file.getPath().substring(5);
        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();
        sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        body = BodyHelper.createBody(xPosition, yPosition, width, height, false, 10000, gameScreen.getWorld(), ContactType.BULLET);

    }

    @Override
    public void update()
    {
        // Not sure if needed
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(textureRegion, xPosition, yPosition, width, height);
    }
}
