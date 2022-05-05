package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.GameWorld;
import org.example.spi.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;
import java.util.Random;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class WeaponController implements IEntityProcessingService
{
    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            System.out.println("Weapon is shooting");
        }
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
