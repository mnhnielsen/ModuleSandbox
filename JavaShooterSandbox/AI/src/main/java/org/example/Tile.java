package org.example;


import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

public class Tile
{
    private int tileX;
    private int tileY;
    private String tileCellType;
    private TiledMapTileLayer.Cell cell;

    public Tile(int tileX, int tileY, TiledMapTileLayer.Cell cell, String tileCellType){
        this.tileX = tileX;
        this.tileY = tileY;
        this.cell = cell;
        this.tileCellType = tileCellType;
    }
    public Tile(int tileX, int tileY, String tileCellValue){
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileCellType = tileCellValue;
    }
    public Tile(int tileX, int tileY, TiledMapTileLayer.Cell cell){
        this.tileX = tileX;
        this.tileY = tileY;
        this.cell = cell;
    }

    public int getTileX()
    {
        return tileX;
    }

    public void setTileX(int tileX)
    {
        this.tileX = tileX;
    }

    public int getTileY()
    {
        return tileY;
    }

    public void setTileY(int tileY)
    {
        this.tileY = tileY;
    }

    public String getTileCellType()
    {
        return tileCellType;
    }

    public void setTileCellType(String tileCellType)
    {
        this.tileCellType = tileCellType;
    }

    public TiledMapTileLayer.Cell getCell()
    {
        return cell;
    }

    public void setCell(TiledMapTileLayer.Cell cell)
    {
        this.cell = cell;
    }
}
