package org.example;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.helper.GameScreen;
import org.example.spi.IBulletSPI;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IBulletSPI.class)
})
public class IBullet implements IBulletSPI
{
    float xPosition, yPosition;
    float width, height, speed;
    TextureRegion textureRegion;
    protected GameScreen gameScreen;

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
