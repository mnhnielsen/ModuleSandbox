package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.helper.Enemy;
import org.example.helper.Player;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.Iterator;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class EnemyController implements IEntityProcessingService
{
    private final Lookup lookup = Lookup.getDefault();
    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer walkableLayer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(2);



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


                    float speedX = direction.x * enemy.getSpeed();
                    float speedY = direction.y * enemy.getSpeed();

/*                    if (collideTop(enemy)) {
                        float spdx = direction.x + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();
                        enemy.getBody().setLinearVelocity(spdx, speedY * -1);
                    } else if (collideBottom(enemy)) {
                        float spdx = direction.x + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();
                        enemy.getBody().setLinearVelocity(spdx, 0);
                    } else if (collideLeft(enemy)) {
                        float spdy = direction.y + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();

                        enemy.getBody().setLinearVelocity(0, spdy);
                    } else if (collideRight(enemy)) {
                        float spdy = direction.y + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();

                        enemy.getBody().setLinearVelocity(0, spdy);
                    } else*/
                    enemy.getBody().setLinearVelocity(speedX, speedY);
                    aStar(enemy);


                    //enemy.getBody().setTransform(enemy.getBody().getPosition(), direction.angleRad()); //This rotates the body but now the sprite. Uncomment debugrendere in Render() in Game.java to see
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

//    public void initPathfinding(Entity enemy)
//    {
//        currentCell = new GridCell((int) enemy.getX(), (int) enemy.getY());
//        targetCell = new GridCell((int) Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//        openList.add(currentCell);
//        if (currentCell == targetCell)
//        {
//            System.out.println("Target");
//            return;
//        }
//        int counter = 0;
//        while (!openList.isEmpty())
//        {
//            currentCell = openList.get(counter);
//            openList.remove(currentCell);
//            closedList.add(currentCell);
//            counter++;
//        }
//
//
//        for (GridCell cell : navLayer.getNeighbors(currentCell))
//        {
//            if (!cell.isWalkable() || closedList.contains(cell))
//            {
//                continue;
//            }
//
//            if (navLayer.getNeighbors(currentCell).isEmpty() || !closedList.contains(navLayer.getNeighbors(cell)))
//            {
//
//                currentCell.setF(cell.getF());
//                currentCell.setParent(cell.getParent());
//                if (!openList.contains(cell))
//                {
//                    openList.add(cell);
//
//                }
//            }
//
//
//        }
//
//    }

    public void aStar(Entity enemy)
    {
        int cellValueX = (int) (enemy.getX() / walkableLayer.getTileWidth());
        int cellValueY = (int) (enemy.getY() / walkableLayer.getTileHeight());
        int playerCellValueX = (int) (player.position().x / walkableLayer.getTileWidth());
        int playerCellValueY = (int) (player.position().y / walkableLayer.getTileHeight());


        TiledMapTileLayer.Cell enemyPosition = walkableLayer.getCell(cellValueX, cellValueY);
        Node currentNode = new Node(enemyPosition, cellValueX, cellValueY);
        TiledMapTileLayer.Cell playerPosition = walkableLayer.getCell(playerCellValueX, playerCellValueY);

        ArrayList<Node> fringe = new ArrayList<>();
        ArrayList<Node> rejectedCell = new ArrayList<>();


        Node targetNode = new Node(playerPosition, playerCellValueX, playerCellValueY);
        fringe.add(currentNode);
        while (!fringe.isEmpty())
        {
            currentNode = lowestNode(fringe, targetNode);
            fringe.remove(currentNode);
            rejectedCell.add(currentNode);
            if (currentNode.getCell() == targetNode.getCell())
            {
                ArrayList<Node> path = currentNode.getPath();
                if (path.size() > 1)
                {
                    //System.out.println("Enemy goooo");
                } else
                {
                    //System.out.println("Enemy on my tile");
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
                if (walkableLayer.getCell(n[0], n[1]) != null && !walkableLayer.getCell(n[0], n[1]).getTile().getProperties().containsKey("blocked") && !rejectedCell.contains(walkableLayer.getCell(n[0], n[1])))
                {
                    Node node = new Node(walkableLayer.getCell(n[0], n[1]), n[0], n[1]);
                    node.setParent(currentNode);
                    fringe.add(node);
                } else
                    System.out.println("Shit is blocked yo");
            }
        }


    }

    public double heuristics(Node node, Node targetNode)
    {
        float x1 = node.getTileX();
        float y1 = node.getTileY();
        float x2 = targetNode.getTileX();
        float y2 = targetNode.getTileY();

        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public double valuation(Node node, Node targetNode)
    {
        return node.getPath().size() + heuristics(node, targetNode);
    }

    public Node lowestNode(ArrayList<Node> fringe, Node targetNode)
    {
        Node lowNode = fringe.get(0);

        double evalValue = valuation(lowNode, targetNode);
        for (Node node : fringe)
        {
            double valuation = valuation(node, targetNode);
            if (valuation < evalValue)
            {
                evalValue = valuation;
                lowNode = node;
            }
        }
        return lowNode;
    }

    private ArrayList getAllBlockedCells()
    {
        ArrayList<String> cellArrayList = new ArrayList<>();
        for (Iterator<String> it = walkableLayer.getProperties().getKeys(); it.hasNext(); )
        {
            System.out.println("teststetset");
            String s = it.next();
            System.out.println(s);

        }
        return cellArrayList;
    }

    private boolean getTileValue(Entity entity)
    {
        int cellValueX = (int) (entity.getX() / walkableLayer.getTileWidth());
        int cellValueY = (int) (entity.getY() / walkableLayer.getTileHeight());
        TiledMapTileLayer.Cell cell = walkableLayer.getCell(cellValueX, cellValueY);

        boolean blocked = false;
        for (Iterator<Object> it = cell.getTile().getProperties().getValues(); it.hasNext(); )
        {
            Tile tile = new Tile(cellValueX, cellValueY, cell, it.next().toString());


            try
            {
                int[][] neighbours = {{tile.getTileX() - 1, tile.getTileY() + 1}, {tile.getTileX(), tile.getTileY() + 1},
                        {tile.getTileX() + 1, tile.getTileY() + 1}, {tile.getTileX() - 1, tile.getTileY()},
                        {tile.getTileX() + 1, tile.getTileY()}, {tile.getTileX() - 1, tile.getTileY() - 1},
                        {tile.getTileX(), tile.getTileY() - 1}, {tile.getTileX() + 1, tile.getTileY() - 1}};


                for (int[] n : neighbours)
                {

                    if (walkableLayer.getCell(n[0], n[1]).getTile().getProperties().containsKey("blocked"))
                    {
                        blocked = true;
                    }
                }
            } catch (NullPointerException e)
            {
                System.out.println("Too close to edge");
            }
        }
        return blocked;
    }
}
