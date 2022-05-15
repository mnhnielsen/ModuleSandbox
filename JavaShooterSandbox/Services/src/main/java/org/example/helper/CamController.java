package org.example.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;

public class CamController
{
    public static CamController INSTANCE;
    private OrthographicCamera cam = new OrthographicCamera();

    public CamController()
    {
        INSTANCE = this;
    }

    public OrthographicCamera getCam()
    {
        return cam;
    }
}
