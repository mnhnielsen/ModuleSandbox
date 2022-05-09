package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.data.parts.HealthPart;
import org.example.helper.*;
import org.example.spi.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

import java.io.File;

@ServiceProviders(value = {@ServiceProvider(service = IGamePluginService.class)})
public class PlayerCreation implements IGamePluginService
{

    public static PlayerCreation INSTANCE;
    private Player player = createPlayer();


    public PlayerCreation()
    {
        INSTANCE = this;
    }

    protected Player createPlayer()
    {
        System.out.println("Created");
        int x = 40;
        int y = Gdx.graphics.getHeight() / 2;
        int speed = 6;
        int width = 64;
        int height = 64;
        String fileName = "soldierIdle.png";

        File file = new File(this.getClass().getResource(fileName).getPath());
        String path = file.getPath().substring(5);

        AssetLoader.INSTANCE.getAm().load(path, Texture.class);
        AssetLoader.INSTANCE.getAm().finishLoading();
        Sprite sprite = new Sprite(AssetLoader.INSTANCE.getAm().get(path, Texture.class));
        Body body = BodyHelper.createBody(x, y, width, height, false, 10000, LibWorld.INSTANCE.getWorld(), ContactType.PLAYER);
        Player p = new Player(x, y, speed, width, height, body, sprite, new HealthPart(1));
        return p;
    }

    @Override
    public void start(GameWorld world)
    {
        world.addEntity(player);
    }

    @Override
    public void stop(GameWorld world)
    {
        world.removeEntity(player);
    }

    public Entity getPlayer()
    {
        return player;
    }
}
