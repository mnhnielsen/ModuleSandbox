package org.example;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import org.example.spi.IBulletService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IBulletService.class)})
public class Bullet implements IBulletService
{
    float width, height, speed;
    Rectangle boundingBox;
    TextureRegion textureRegion;

    public Bullet(float x, float y, float width, float height, float speed, TextureRegion textureRegion)
    {
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.boundingBox = new Rectangle(x - width / 2, y, width, height);
        this.textureRegion = textureRegion;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(textureRegion, boundingBox.x, boundingBox.y, boundingBox.width, boundingBox.height);
    }
}
