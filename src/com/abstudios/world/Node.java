package com.abstudios.world;

import java.util.Vector;

public class Node {

    public Vector2i tile;
    public Node parent;
    public double fCost, gCost, hCost;

    public Node(Vector2i tile, Node parent, double gcost, double hcost){

        this.tile = tile;
        this.parent = parent;
        this.gCost = gcost;
        this.hCost = hcost;
        this.fCost = gcost + hcost;
    }
}
