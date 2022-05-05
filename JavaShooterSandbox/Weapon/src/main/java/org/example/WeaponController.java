package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.AssetLoader;
import org.example.helper.Const;
import org.example.helper.LibWorld;
import org.example.spi.IBulletService;
import org.example.spi.IEntityProcessingService;
import org.example.spi.IGamePluginService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class WeaponController implements IEntityProcessingService
{
    @Override
    public void update(GameWorld world, SpriteBatch batch)
    {
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
        System.out.println("Weapon is shooting");
    }
    }

    @Override
    public Vector2 position()
    {
        return null;
    }
}
