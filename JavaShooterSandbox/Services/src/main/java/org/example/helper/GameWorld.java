package org.example.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameWorld
{
    private final Map<String, EntityObject> entityMap = new ConcurrentHashMap<>();
    private final Map<String, EntityObject> deleteObjectMap = new ConcurrentHashMap<>();


    public String addEntity(EntityObject entity)
    {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }
    public String addObjectForDeletion(EntityObject entity)
    {
        deleteObjectMap.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(String entityID)
    {
        entityMap.remove(entityID);
    }

    public void removeEntity(EntityObject entity)
    {
        entityMap.remove(entity.getID());
    }

    public Collection<EntityObject> getEntities()
    {
        return entityMap.values();
    }
    public Collection<EntityObject> getEntitiesForDeletion()
    {
        return deleteObjectMap.values();
    }

    public <E extends EntityObject> List<EntityObject> getEntities(Class<E>... entityTypes)
    {
        List<EntityObject> r = new ArrayList<>();
        for (EntityObject e : getEntities())
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

    public EntityObject getEntity(String ID)
    {
        return entityMap.get(ID);
    }
}
