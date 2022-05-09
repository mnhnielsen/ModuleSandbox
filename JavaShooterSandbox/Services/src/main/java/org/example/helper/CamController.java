package org.example.helper;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class CamController
{
    public static CamController INSTANCE;
    private OrthographicCamera cam;

    public CamController()
    {
        INSTANCE = this;
        cam = new OrthographicCamera();
    }

    public OrthographicCamera getCam()
    {
        return cam;
    }
}
