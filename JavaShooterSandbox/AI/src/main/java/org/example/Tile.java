package org.example;


public class Tile
{
    private int tileX;
    private int tileY;
    private String tileCellType;

    public Tile(int tileX, int tileY, String tileCellValue){
        this.tileX = tileX;
        this.tileY = tileY;
        this.tileCellType = tileCellValue;
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
}
