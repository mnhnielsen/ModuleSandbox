package org.example.helper;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LibWorld
{
    public static LibWorld INSTANCE;
    private World world;

    public LibWorld()
    {
        world = new World(new Vector2(0, 0), false);
        INSTANCE = this;
    }

    public World getWorld()
    {
        return world;
    }
}
