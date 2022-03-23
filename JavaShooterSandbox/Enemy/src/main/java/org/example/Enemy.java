package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.BodyHelper;
import org.example.helper.Const;
import org.example.helper.ContactType;
import org.example.helper.GameScreen;
import org.example.spi.IEnemyService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEnemyService.class)
})
public class Enemy implements IEnemyService
{
    protected Body body;
    protected float x, y, speed, velY;
    protected int width, height, score;
    protected Texture texture, textureOne;
    protected GameScreen gameScreen;

    @Override
    public void enemy(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 6;
        width = 32;
        height = 32;
        texture = new Texture("/home/mathias/Desktop/ModuleSandbox/JavaShooterSandbox/Player/src/main/resources/org/example/color.png");
        body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.PLAYER);

    }

    @Override
    public void update()
    {
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);
        velY = 0;
        System.out.println("X " + gameScreen.getPlayerService().getX());
        System.out.println("Y " + gameScreen.getPlayerService().getY());

    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(texture, x, y, width, height);
    }

}
