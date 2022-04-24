package org.example.data;

import org.example.helper.AssetLoader;
import org.example.helper.LibWorld;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameWorld
{
    public static GameWorld INSTANCE;

    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private AssetLoader as = new AssetLoader();

    public GameWorld()
    {
        INSTANCE = this;
    }

    public String addEntity(Entity entity)
    {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID)
    {
        entityMap.remove(entityID);
    }

    public void removeEntity(Entity entity)
    {
        entityMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities()
    {
        return entityMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes)
    {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities())
        {
            for (Class<E> entityType : entityTypes)
            {
                if (entityType.equals(e.getClass()))
                {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID)
    {
        return entityMap.get(ID);
    }
}
