package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Enemy;
import org.example.helper.Player;
import org.example.spi.IEntityProcessingService;
import org.xguzm.pathfinding.finders.AStarFinder;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;

import java.util.ArrayList;
import java.util.Iterator;

public class Pathfinding{

    TiledMap map;

    Enemy enemy;

    Player player;

    NavTmxMapLoader navTmxMapLoader = new NavTmxMapLoader();

    NavigationTiledMapLayer navLayer;


    GridCell currentCell, targetCell;


    public Pathfinding (){

        map = navTmxMapLoader.load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        navLayer = (NavigationTiledMapLayer) map.getLayers().get("NavLayer");
        navTmxMapLoader.navigationProperty = "blocked";



    }

    public void initPathfinding(){
        currentCell = new GridCell((int) enemy.getX(), (int) enemy.getY());
        targetCell = new GridCell((int) player.getX(), (int) player.getY());
        ArrayList<GridCell> openList = new ArrayList<>();
        ArrayList<GridCell> closedList = new ArrayList<>();
        openList.add(currentCell);

        for(GridCell cell : openList) {
            currentCell = cell;
            openList.remove(currentCell);
            closedList.add(currentCell);
             }


        if(currentCell.equals(targetCell)) {
                return;
            }

        for (GridCell cell : navLayer.getNeighbors(currentCell)){
            if ( !cell.isWalkable() || closedList.contains(cell)){
               continue;
            }

            if(openList.size()) {
                //todo if pathlist is shorter than openlist and cell is not in openlist
                currentCell.setF(cell.getF());
                currentCell.setParent(cell.getParent());
                if (!openList.contains(cell)){
                    openList.add(cell);
                }
        }



    }

    public void getPathDistance(){

    }

}

