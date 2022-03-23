package org.example.spi;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.example.helper.GameScreen;

public interface BulletSPI
{
    void bullet(float xCentre, float yBottom, float width, float height, float movementSpeed, TextureRegion textureRegion, GameScreen gameScreen);

    void update();

    void render(SpriteBatch batch);
}
