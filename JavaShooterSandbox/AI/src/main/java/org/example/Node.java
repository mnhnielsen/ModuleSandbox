package org.example;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;

public class Node
{
    private Node parent;
    private TiledMapTileLayer.Cell cell;

    private float evalValue;
    private int cost;
    private int tileX;
    private int tileY;

    public Node getParent()
    {
        return parent;
    }

    public void setParent(Node parent)
    {
        this.parent = parent;
    }

    public TiledMapTileLayer.Cell getCell()
    {
        return cell;
    }

    public Node(TiledMapTileLayer.Cell cell, int tileX, int tileY, int cost)
    {
        this.cell = cell;
        this.tileX = tileX;
        this.tileY = tileY;
        this.cost = cost;
    }

    public Node(int tileX, int tileY)
    {
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public float getEvalValue()
    {
        return evalValue;
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public int getCost()
    {
        return cost;
    }

    public void setCost(int cost)
    {
        this.cost = cost;
    }

    public ArrayList<Node> getPath()
    {
        ArrayList<Node> path = new ArrayList<Node>();
        Node currentNode = this;
        path.add(currentNode);
        while (currentNode.getParent() != null)
        {
            currentNode = currentNode.getParent();
            path.add(0, parent);
        }
        return path;
    }

}
