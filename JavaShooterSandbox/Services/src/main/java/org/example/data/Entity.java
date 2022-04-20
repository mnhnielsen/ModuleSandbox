package org.example.data;

import org.example.data.parts.EntityPart;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable
{
    private final UUID ID = UUID.randomUUID();
    private Map<Class, EntityPart> parts;

    public Entity()
    {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part)
    {
        parts.put(part.getClass(), part);
    }


    public <E extends EntityPart> E getPart(Class partClass)
    {
        return (E) parts.get(partClass);
    }

    public String getID()
    {
        return ID.toString();
    }

}
