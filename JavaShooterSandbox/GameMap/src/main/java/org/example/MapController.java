package org.example;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.spi.IEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class MapController implements IEntityProcessingService {

    float x, y;

    @Override
    public void update(GameWorld world, SpriteBatch batch) {

        for (Entity map : world.getEntities(Map.class)) {

            map.setX(map.getBody().getPosition().x  * Const.PPM - (map.getWidth() /2));
            map.setY(map.getBody().getPosition().y * Const.PPM - map.getHeight() /2);
            batch.draw(map.getSprite(), map.getX(), map.getY(), map.getWidth(), map.getHeight());
        }


    }

    @Override
    public Vector2 position() {
        return new Vector2(x,y);
    }
}
