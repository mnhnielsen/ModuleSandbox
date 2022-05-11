package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class EnemyController implements IEntityProcessingService
{
    private final Lookup lookup = Lookup.getDefault();


    private IEntityProcessingService player = lookup.lookup(IEntityProcessingService.class);

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        for (Entity enemy : world.getEntities(Enemy.class))
        {
            System.out.println(world.getEntities(Enemy.class).size());
            if (enemy.getHealthPart().getHealth() > 0)
            {
                enemy.setX(enemy.getBody().getPosition().x * Const.PPM - (enemy.getWidth() / 2));
                enemy.setY(enemy.getBody().getPosition().y * Const.PPM - (enemy.getHeight() / 2));
                enemy.setVelX(0);
                enemy.setVelY(0);

                //Enemy movement with vector
                Vector2 zombiePos = new Vector2(enemy.getX(), enemy.getY());
                Vector2 playerPos = new Vector2(player.position().x, player.position().y);
                Vector2 direction = new Vector2();

                //Difference in position to create vector with direction
                direction.x = playerPos.x - zombiePos.x;
                direction.y = playerPos.y - zombiePos.y;

                //Normalize vector so the length is always 1, and therefore "speed" decides how fast enemy goes
                direction.nor();

                float speedX = direction.x * enemy.getSpeed();
                float speedY = direction.y * enemy.getSpeed();

                enemy.getBody().setLinearVelocity(speedX, speedY);
                enemy.getBody().setTransform(enemy.getBody().getPosition(), direction.angleRad()); //This rotates the body but now the sprite. Uncomment debugrendere in Render() in Game.java to see
                batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
            }
        }
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
