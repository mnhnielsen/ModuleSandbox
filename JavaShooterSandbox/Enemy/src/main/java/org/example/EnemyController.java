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
                        aStar(enemy);


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

    public double h(Node node, Node targetNode)
    {
        double x1 = node.getTileX();
        double y1 = node.getTileY();
        double x2 = targetNode.getTileX();
        double y2 = targetNode.getTileY();

        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public double f(Node node, Node targetNode)
    {
        return node.previous().size() + h(node, targetNode);
    }

    public void aStar(Entity enemy)
    {
        int cellValueX = (int) (enemy.getX() / walkableLayer.getTileWidth());
        int cellValueY = (int) (enemy.getY() / walkableLayer.getTileHeight());
        int playerCellValueX = (int) (player.position().x / walkableLayer.getTileWidth());
        int playerCellValueY = (int) (player.position().y / walkableLayer.getTileHeight());


        TiledMapTileLayer.Cell enemyPosition = walkableLayer.getCell(cellValueX, cellValueY);
        Node currentNode = new Node(enemyPosition, cellValueX, cellValueY);
        TiledMapTileLayer.Cell playerPosition = walkableLayer.getCell(playerCellValueX, playerCellValueY);

        ArrayList<Node> openSet = new ArrayList<>();
        ArrayList<Node> closedSet = new ArrayList<>();


        Node targetNode = new Node(playerPosition, playerCellValueX, playerCellValueY);
        openSet.add(currentNode);
        while (!openSet.isEmpty())
        {
            currentNode = lowestNodeInFringe(openSet, targetNode);
            openSet.remove(currentNode);
            closedSet.add(currentNode);
            if (currentNode.getCell() == targetNode.getCell())
            {
                ArrayList<Node> path = currentNode.previous();
                System.out.println(path.size());
                if (path.size() > 1)
                {
                    float x = targetNode.getTileX() - path.get(0).getTileX();
                    float y = targetNode.getTileY() - path.get(0).getTileY();
                    Vector2 dir = new Vector2(x, y);
                    dir.nor();
                    float speedX = dir.x * enemy.getSpeed();
                    float speedY = dir.y * enemy.getSpeed();
                    enemy.getBody().setLinearVelocity(speedX, speedY);
                }
                return;
            }
            Tile tile = new Tile(currentNode.getTileX(), currentNode.getTileY(), currentNode.getCell());


            int[][] neighbours = {{tile.getTileX() - 1, tile.getTileY() + 1}, {tile.getTileX(), tile.getTileY() + 1},
                    {tile.getTileX() + 1, tile.getTileY() + 1}, {tile.getTileX() - 1, tile.getTileY()},
                    {tile.getTileX() + 1, tile.getTileY()}, {tile.getTileX() - 1, tile.getTileY() - 1},
                    {tile.getTileX(), tile.getTileY() - 1}, {tile.getTileX() + 1, tile.getTileY() - 1}};

            for (int[] n : neighbours)
            {
                Node neighbour = new Node(walkableLayer.getCell(n[0], n[1]), n[0], n[1]);
                //neighbour.setBlocked(neighbour.getCell().getTile().getProperties().containsKey("blocked"));


                if (!openSet.contains(neighbour) && !closedSet.contains(neighbour))
                {
                    Node node = new Node(neighbour.getCell(), neighbour.getTileX(), neighbour.getTileY());
                    node.setParent(currentNode);
                    openSet.add(node);
                } else
                {
                    return;
                }

            }
        }
    }


    public Node lowestNodeInFringe(ArrayList<Node> fringe, Node targetNode)
    {
        Node n = fringe.get(0);

        double fScore = f(n, targetNode);
        for (Node node : fringe)
        {
            double tempF = f(node, targetNode);
            if (tempF < fScore)
            {
                fScore = tempF;
                n = node;
            }
        }
        return n;
    }
}
