package org.example.spi;

import org.example.data.Entity;




public interface IPathFindingService
{
    void aStar(Entity enemy, IEntityProcessingService player);
}
