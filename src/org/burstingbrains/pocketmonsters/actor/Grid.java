package org.burstingbrains.pocketmonsters.actor;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.GameLogic;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;
	BBSHandler handler;

	private MapTile[][] grid;
	private MapTile activeMapTile;
	private MapTile selectorMapTile;
	
	private Monster activeMonster;
	private Monster[][] monsters;
	
	private SharedMonsterMenu menu;

	public Grid(final Universe universe) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager(), DrawType.STATIC);
		this.universe = universe;
		
		// Set the overall scene to be transparent
		this.setColor(0, 0, 0, 0);
		
		universe.attachChild(this);
		universe.registerTouchArea(this);
		
		initializeGrid();
		initializeMonsters(universe);
		
		menu = new SharedMonsterMenu(universe, 4);
		menu.setPosition(CAMERA_WIDTH/2, 0);
	}

	private void initializeGrid() {
		grid = new MapTile[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		for(int column = 0; column < GRID_WIDTH_IN_METERS; column++){
			for(int row = 0; row < GRID_HEIGHT_IN_METERS; row++){
				MapTile tile = new MapTile(column * PIXELS_PER_METER, row * PIXELS_PER_METER, PIXELS_PER_METER,
						PIXELS_PER_METER, getVertexBufferObjectManager());
				tile.setColor(
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256),
						0.3f);

				attachChild(tile);

				grid[column][row] = tile;
			}
		}
		
		activeMapTile = grid[0][0];
		
		selectorMapTile = new MapTile(0, 0, PIXELS_PER_METER, PIXELS_PER_METER, getVertexBufferObject());
		selectorMapTile.setColor(Color.RED);
		attachChild(selectorMapTile);		
	}

	private void initializeMonsters(Universe universe) {
		monsters = new Monster[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		
		Monster monster1 = new Monster(universe);
		monster1.setGridPos(4, 4);
		monsters[4][4] = monster1;
		
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		int posXInMeters = (int) (pTouchAreaLocalX / PIXELS_PER_METER);
		int posYInMeters = (int) (pTouchAreaLocalY / PIXELS_PER_METER);
		if (posXInMeters < GRID_WIDTH_IN_METERS &&
				posYInMeters < GRID_HEIGHT_IN_METERS) {

			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				activeMapTile = grid[posXInMeters][posYInMeters];
				selectorMapTile.setPosition(activeMapTile);
				break;
			case TouchEvent.ACTION_UP:
				int activeTilePosX = activeMapTile.getGridX();
				int activeTilePosY = activeMapTile.getGridY();
				activeMonster = monsters[activeTilePosX][activeTilePosY];
				if(activeMonster != null){
					menu.setVisible(true);
				}
				else{
					menu.setVisible(false);
				}
				
				break;
			}
		}
		return true;
	}

	public MapTile getMapTileAt(int x, int y) {
		return grid[x][y];
	}
}
