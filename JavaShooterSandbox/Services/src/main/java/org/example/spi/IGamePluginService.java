package org.example.spi;

import org.example.data.GameWorld;

public interface IGamePluginService
{
    void start(GameWorld world);

    void stop(GameWorld world);
}
