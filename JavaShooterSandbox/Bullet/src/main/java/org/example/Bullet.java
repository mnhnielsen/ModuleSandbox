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
    float xPosition, yPosition;
    float width, height, speed;
    TextureRegion textureRegion;

    public Bullet(float xPosition, float yPosition, float width, float height, float speed, TextureRegion textureRegion)
    {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.textureRegion = textureRegion;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(textureRegion, xPosition - width / 2, yPosition, width, height);
    }
}
