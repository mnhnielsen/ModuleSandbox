package org.example;

import java.util.ArrayList;

public class Node
{
    private Node parent;
    private float value;

    public Node getNode()
    {
        return parent;
    }

    public void setNode(Node parent)
    {
        this.parent = parent;
    }

    public float getValue()
    {
        return value;
    }

    public void setValue(float value)
    {
        this.value = value;
    }

    public ArrayList<Node> getPathNodes()
    {
        ArrayList<Node> nodePath = new ArrayList<Node>();
        Node thisNode = this;
        nodePath.add(thisNode);
        while (thisNode.getNode() != null)
        {
            thisNode = thisNode.getNode();
            nodePath.add(0, parent);
        }
        return nodePath;
    }
}
