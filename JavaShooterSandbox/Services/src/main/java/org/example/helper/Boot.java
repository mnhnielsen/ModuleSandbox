package org.example.helper;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game
{

    public static Boot INSTANCE;
    private int screenWidth;


    private int screenHeight;
    private OrthographicCamera cam;

    public Boot()
    {
        INSTANCE = this;
    }

    @Override
    public void create()
    {
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        cam = new OrthographicCamera();
        cam.setToOrtho(false, screenWidth, screenHeight);
        setScreen(new GameScreen(cam));
    }

    public int getScreenWidth()
    {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth)
    {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight()
    {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight)
    {
        this.screenHeight = screenHeight;
    }
}
