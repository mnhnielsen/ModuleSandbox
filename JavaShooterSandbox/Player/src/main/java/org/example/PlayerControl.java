package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.BodyHelper;
import org.example.helper.Const;
import org.example.helper.ContactType;
import org.example.helper.GameScreen;
import org.example.spi.IPlayerService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IPlayerService.class)
})
public class PlayerControl implements IPlayerService
{
    protected Body body;
    protected float x, y, speed, velY, velX;
    protected int width, height, score;
    protected Texture texture, textureOne;
    protected GameScreen gameScreen;

    float laserWidth, laserHeight;
    float laserMovementSpeed;
    float timeBetweenShots;
    float timeSinceLastShot = 0;

    @Override
    public void player(float x, float y, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.gameScreen = gameScreen;
        speed = 6;
        width = 32;
        height = 32;
        //textureOne = new Texture("/home/mathias/Documents/Projects/Semester4/javashooter/Project/Player/src/main/resources/org/example/player/Ship.png");
        texture = new Texture("/home/mathias/Desktop/ModuleSandbox/JavaShooterSandbox/Player/src/main/resources/org/example/color.png");
        body = BodyHelper.createBody(x, y, width, height, false, 10000, gameScreen.getWorld(), ContactType.PLAYER);
    }

    @Override
    public void update()
    {
        boolean isMoving = false;
        x = body.getPosition().x * Const.PPM - (width / 2);
        y = body.getPosition().y * Const.PPM - (height / 2);
        velY = 0;
        velX = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.W))
        {
            velY = 1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S))
        {
            velY = -1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D))
        {
            velX = 1;
            isMoving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A))
        {
            velX = -1;
            isMoving = true;

        }
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) && isMoving)
            speed = 10;
        else
            speed = 6;

        body.setLinearVelocity(velX * speed, velY * speed);
    }

    @Override
    public void render(SpriteBatch batch)
    {
        batch.draw(texture, x, y, width, height);
    }

    @Override
    public void score()
    {
        this.score++;
    }


}
