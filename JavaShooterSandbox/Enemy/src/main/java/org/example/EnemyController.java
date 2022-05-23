package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.helper.Enemy;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IPathFindingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.ArrayList;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class EnemyController implements IEntityProcessingService
{
    private final Lookup lookup = Lookup.getDefault();
    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer walkableLayer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(3);


    private int offSet = 85;


    private IEntityProcessingService player = lookup.lookup(IEntityProcessingService.class);
    private IPathFindingService astar = lookup.lookup(IPathFindingService.class);

    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {

        try
        {
            for (Entity enemy : world.getEntities(Enemy.class))
            {
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


                    if (collideTop(enemy))
                    {
                        float spdx = 0;
                        if (direction.x <= 0)
                            spdx = direction.x + offSet * enemy.getSpeed() * -1;
                        else
                            spdx = direction.x + offSet * enemy.getSpeed();
                        enemy.getBody().setLinearVelocity(spdx * Gdx.graphics.getDeltaTime(), 0);

                    } else if (collideBottom(enemy))
                    {
                        float spdx = 0;
                        if (direction.x <= 0)
                            spdx = direction.x + offSet * enemy.getSpeed() * -1;
                        else
                            spdx = direction.x + offSet * enemy.getSpeed();
                        enemy.getBody().setLinearVelocity(spdx * Gdx.graphics.getDeltaTime(), 0);

                    } else if (collideLeft(enemy))
                    {
                        float spdy = 0;
                        if (direction.y <= 0)
                            spdy = direction.y + offSet * enemy.getSpeed() * -1;
                        else
                            spdy = direction.y + offSet * enemy.getSpeed();
                        enemy.getBody().setLinearVelocity(0, spdy * Gdx.graphics.getDeltaTime());

                    } else if (collideRight(enemy))
                    {
                        float spdy = 0;
                        if (direction.y <= 0)
                            spdy = direction.y + offSet * enemy.getSpeed() * -1;
                        else
                            spdy = direction.y + offSet * enemy.getSpeed();
                        enemy.getBody().setLinearVelocity(0, spdy * Gdx.graphics.getDeltaTime());

                    } else
                        astar.aStar(enemy, player);


                    batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                }
            }
        } catch (NullPointerException e)
        {
            e.printStackTrace();
        }
    }


    public boolean collideRight(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + player.getWidth(), player.getY() + step);
        return collides;
    }

    public boolean collideLeft(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(player.getX(), player.getY() + step);

        return collides;
    }

    public boolean collideTop(Entity player)
    {
        boolean collides = false;
        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + step, player.getY() + player.getHeight());

        return collides;
    }

    public boolean collideBottom(Entity player)
    {
        boolean collides = false;

        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + step, player.getY());
        return collides;
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
