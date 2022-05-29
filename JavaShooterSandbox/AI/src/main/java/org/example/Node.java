package org.example;

import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.LinkedList;

public class Node
{
    private Node parent;
    private TiledMapTileLayer.Cell cell;

    private boolean isBlocked;
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

    public Node(TiledMapTileLayer.Cell cell, int tileX, int tileY)
    {
        this.cell = cell;
        this.tileX = tileX;
        this.tileY = tileY;
    }

    public Node(int tileX, int tileY)
    {
        this.tileX = tileX;
        this.tileY = tileY;
    }


    public boolean isBlocked()
    {
        return isBlocked;
    }

    public void setBlocked(boolean blocked)
    {
        isBlocked = blocked;
    }

    public int getTileX()
    {
        return tileX;
    }

    public int getTileY()
    {
        return tileY;
    }


    public LinkedList<Node> previous()
    {
        LinkedList<Node> path = new LinkedList<>();
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
