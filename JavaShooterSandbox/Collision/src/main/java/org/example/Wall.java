package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.BodyHelper;
import org.example.helper.Boot;
import org.example.helper.ContactType;
import org.example.helper.GameScreen;
import org.example.spi.IObstacleService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IObstacleService.class)
})
public class Wall implements IObstacleService
{

    private Body body;
    private float x, y;
    private int width, height;
    private Texture texture;

    @Override
    public void obstacle(float y, GameScreen gameScreen)
    {
        x = Boot.INSTANCE.getScreenWidth() / 2;
        this.y = y;
        width = Boot.INSTANCE.getScreenWidth();
        height = 64;

        texture = new Texture("/home/mathias/Desktop/Pong/Client/src/main/resources/org/pong/white.png");
        body = BodyHelper.createBody(x, y, width, height, true, 0, gameScreen.getWorld(), ContactType.OBSTACLE);
    }

    public void render(SpriteBatch batch)
    {
        batch.draw(texture, x - (width / 2), y - (height / 2), width, height);
    }
}
