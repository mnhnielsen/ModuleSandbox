package org.example;

import com.badlogic.gdx.physics.box2d.Body;
import org.example.helper.GameScreen;

public class Perimeter
{

    private float x, y;
    private int width, height;
    private String filename;
    private GameScreen gameScreen;

    public Perimeter(float x, float y, int width, int height, String filename, GameScreen gameScreen)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.filename = filename;
        this.gameScreen = gameScreen;
    }
}
