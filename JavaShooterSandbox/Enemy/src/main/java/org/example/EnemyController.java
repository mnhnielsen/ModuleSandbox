package org.example;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import org.example.data.Entity;
import org.example.data.GameWorld;
import org.example.helper.Const;
import org.example.helper.Enemy;
import org.example.helper.Player;
import org.example.spi.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import org.xguzm.pathfinding.gdxbridge.NavTmxMapLoader;
import org.xguzm.pathfinding.gdxbridge.NavigationTiledMapLayer;
import org.xguzm.pathfinding.grid.GridCell;

import java.util.ArrayList;
import java.util.Iterator;

@ServiceProviders(value = {@ServiceProvider(service = IEntityProcessingService.class)})
public class EnemyController implements IEntityProcessingService {
    private final Lookup lookup = Lookup.getDefault();
    private MapCreation mapCreation = new MapCreation();
    private TiledMapTileLayer layer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(1);
    private TiledMapTileLayer walkableLayer = (TiledMapTileLayer) mapCreation.getMap().getLayers().get(0);
    private int offSet = 85;

    private GridCell currentCell, targetCell;


    TiledMap map;
    NavigationTiledMapLayer navLayer;


    private IEntityProcessingService player = lookup.lookup(IEntityProcessingService.class);

    @Override
    public void update(GameWorld world, SpriteBatch batch) {
        try {
            for (Entity enemy : world.getEntities(Enemy.class)) {
                if (enemy.getHealthPart().getHealth() > 0) {
                    enemy.setX(enemy.getBody().getPosition().x * Const.PPM - (enemy.getWidth() / 2));
                    enemy.setY(enemy.getBody().getPosition().y * Const.PPM - (enemy.getHeight() / 2));
                    enemy.setVelX(0);
                    enemy.setVelY(0);


                    //Enemy movement with vector
                    Vector2 zombiePos = new Vector2(enemy.getX(), enemy.getY());
                    Vector2 playerPos = new Vector2(player.position().x, player.position().y);
                    Vector2 direction = new Vector2();

                    //Difference in position to create vector with direction
                    direction.x = playerPos.x - zombiePos.x;
                    direction.y = playerPos.y - zombiePos.y;


                    //Normalize vector so the length is always 1, and therefore "speed" decides how fast enemy goes
                    direction.nor();


                    float speedX = direction.x * enemy.getSpeed();
                    float speedY = direction.y * enemy.getSpeed();

/*                    if (collideTop(enemy)) {
                        float spdx = direction.x + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();
                        enemy.getBody().setLinearVelocity(spdx, speedY * -1);
                    } else if (collideBottom(enemy)) {
                        float spdx = direction.x + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();
                        enemy.getBody().setLinearVelocity(spdx, 0);
                    } else if (collideLeft(enemy)) {
                        float spdy = direction.y + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();

                        enemy.getBody().setLinearVelocity(0, spdy);
                    } else if (collideRight(enemy)) {
                        float spdy = direction.y + offSet * enemy.getSpeed() * Gdx.graphics.getDeltaTime();

                        enemy.getBody().setLinearVelocity(0, spdy);
                    } else*/
                        enemy.getBody().setLinearVelocity(speedX, speedY);



                    //enemy.getBody().setTransform(enemy.getBody().getPosition(), direction.angleRad()); //This rotates the body but now the sprite. Uncomment debugrendere in Render() in Game.java to see
                    batch.draw(enemy.getSprite(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                    initPathfinding(enemy);
                }
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean getTileValue(Entity entity) {
        int cellValueX = (int) (entity.getX() / walkableLayer.getTileWidth());
        int cellValueY = (int) (entity.getY() / walkableLayer.getTileHeight());
        TiledMapTileLayer.Cell cell = walkableLayer.getCell(cellValueX, cellValueY);

        boolean blocked = false;
        for (Iterator<Object> it = cell.getTile().getProperties().getValues(); it.hasNext(); ) {
            Tile tile = new Tile(cellValueX, cellValueY, cell, it.next().toString());


            try {
                int[][] neighbours = {{tile.getTileX() - 1, tile.getTileY() + 1}, {tile.getTileX(), tile.getTileY() + 1},
                        {tile.getTileX() + 1, tile.getTileY() + 1}, {tile.getTileX() - 1, tile.getTileY()},
                        {tile.getTileX() + 1, tile.getTileY()}, {tile.getTileX() - 1, tile.getTileY() - 1},
                        {tile.getTileX(), tile.getTileY() - 1}, {tile.getTileX() + 1, tile.getTileY() - 1}};


                for (int[] n : neighbours) {

                    if (walkableLayer.getCell(n[0], n[1]).getTile().getProperties().containsKey("blocked")) {
                        blocked = true;
                    }
                }
            } catch (NullPointerException e) {
                System.out.println("Too close to edge");
            }
        }
        return blocked;
    }


    public boolean collideRight(Entity player) {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + player.getWidth(), player.getY() + step);
        return collides;
    }

    public boolean collideLeft(Entity player) {
        boolean collides = false;
        for (float step = 0; step < player.getHeight(); step += walkableLayer.getTileHeight() / 2)
            collides = mapCreation.isCellBlocked(player.getX(), player.getY() + step);

        return collides;
    }

    public boolean collideTop(Entity player) {
        boolean collides = false;
        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + step, player.getY() + player.getHeight());

        return collides;
    }

    public boolean collideBottom(Entity player) {
        boolean collides = false;

        for (float step = 0; step < player.getWidth(); step += walkableLayer.getTileWidth() / 2)
            collides = mapCreation.isCellBlocked(player.getX() + step, player.getY());
        return collides;
    }

    @Override
    public Vector2 position() {
        return null;
    }

    public void initPathfinding(Entity enemy) {

        NavTmxMapLoader navTmxMapLoader = new NavTmxMapLoader();
        navLayer = (NavigationTiledMapLayer) map.getLayers().get("NavLayer");
        map = navTmxMapLoader.load(Gdx.files.internal("map.tmx").file().getAbsolutePath());
        currentCell = new GridCell((int) enemy.getBody().getPosition().x, (int) enemy.getBody().getPosition().y);
        targetCell = new GridCell((int) player.position().x, (int) player.position().y);
        ArrayList<GridCell> openList = new ArrayList<>();
        ArrayList<GridCell> closedList = new ArrayList<>();
        openList.add(currentCell);
        System.out.println(currentCell);

        for (GridCell cell : openList) {
            currentCell = cell;
            openList.remove(currentCell);
            closedList.add(currentCell);
        }


        if (currentCell.equals(targetCell)) {
            return;
        }

        for (GridCell cell : navLayer.getNeighbors(currentCell)) {
            if (!cell.isWalkable() || closedList.contains(cell)) {
                continue;
            }

            if (navLayer.getNeighbors(currentCell).isEmpty() || !closedList.contains(navLayer.getNeighbors(cell))) {

                currentCell.setF(cell.getF());
                currentCell.setParent(cell.getParent());
                if (!openList.contains(cell)) {
                    openList.add(cell);

                }
            }


        }

    }
}
