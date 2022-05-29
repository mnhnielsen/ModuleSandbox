package org.example;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IPathFindingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.PriorityQueue;

@ServiceProviders(value = {@ServiceProvider(service = IPathFindingService.class)})
public class AStar implements IPathFindingService
{
    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer walkableLayer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(3);


    //Heuristics function. Estimated costs from node to the goal node.
    public double h(Node node, Node targetNode)
    {
        double x1 = node.getTileX();
        double y1 = node.getTileY();
        double x2 = targetNode.getTileX();
        double y2 = targetNode.getTileY();

        //Euclidean distance from current current node. Can run into scale problems
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public double f(Node node, Node targetNode)
    {
        return node.previous().size() + h(node, targetNode);
    }

    public Node lowestNodeInFringe(LinkedList<Node> fringe, Node targetNode)
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

    public void aStar(Entity enemy, IEntityProcessingService player)
    {
        int cellValueX = (int) (enemy.getX() / walkableLayer.getTileWidth());
        int cellValueY = (int) (enemy.getY() / walkableLayer.getTileHeight());
        int playerCellValueX = (int) (player.position().x / walkableLayer.getTileWidth());
        int playerCellValueY = (int) (player.position().y / walkableLayer.getTileHeight());


        TiledMapTileLayer.Cell enemyPosition = walkableLayer.getCell(cellValueX, cellValueY);
        Node currentNode = new Node(enemyPosition, cellValueX, cellValueY);
        TiledMapTileLayer.Cell playerPosition = walkableLayer.getCell(playerCellValueX, playerCellValueY);


        LinkedList<Node> openSet = new LinkedList<>();
        LinkedList<Node> closedSet = new LinkedList<>();



        Node targetNode = new Node(playerPosition, playerCellValueX, playerCellValueY);
        openSet.add(currentNode);
        while (!openSet.isEmpty())
        {
            currentNode = lowestNodeInFringe(openSet, targetNode);
            openSet.remove(currentNode);
            closedSet.add(currentNode);
            if (currentNode.getCell() == targetNode.getCell())
            {
                LinkedList<Node> path = currentNode.previous();
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
}
