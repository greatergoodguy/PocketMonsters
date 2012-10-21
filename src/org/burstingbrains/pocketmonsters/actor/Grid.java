package org.burstingbrains.pocketmonsters.actor;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.menu.SharedMonsterMenu;
import org.burstingbrains.pocketmonsters.monsters.BadlyDrawnMonster;
import org.burstingbrains.pocketmonsters.monsters.OrangeMon;
import org.burstingbrains.pocketmonsters.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.pocketmonsters.world.MovementSelectorGrid;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;
	BBSHandler handler;

	private MapTile[][] grid;
	private MapTile activeMapTile;
	private MapTile selectorMapTile;
	
	private MovementSelectorGrid movementSelectorGrid;
	
	private IMonster activeMonster;
	private IMonster[][] monsters;
	
	private SharedMonsterMenu sharedMonsterMenu;

	public Grid(final Universe universe) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager());
		this.universe = universe;
		
		// Set the overall scene to be transparent
		this.setColor(0, 0, 0, 0);
		
		universe.attachChild(this);
		universe.registerTouchArea(this);
		
		initializeGrid();
		movementSelectorGrid = new MovementSelectorGrid(universe);
		initializeMonsters(universe);
		
		sharedMonsterMenu = new SharedMonsterMenu(universe, new WorldHandler());
		sharedMonsterMenu.deactivate();
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
		
		Monster monster1 = new BadlyDrawnMonster(universe);
		monster1.setGridPos(1, 1);
		monsters[1][1] = monster1;
		
		Monster monster2 = new OrangeMon(universe);
		monster2.setGridPos(3, 4);
		monsters[3][4] = monster2;
		
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
					sharedMonsterMenu.activate();
					sharedMonsterMenu.setActiveMonster(activeMonster);
				}
				else{
					sharedMonsterMenu.deactivate();
				}
				
				break;
			}
		}
		return true;
	}

	public MapTile getMapTileAt(int x, int y) {
		return grid[x][y];
	}
	
	public class WorldHandler{
		public void activateMovementSelectorGridTiles(IMonster monster){
			movementSelectorGrid.activateTiles(monster);
		}
		
		public void deactivateMovementSelectorGridTiles(){
			movementSelectorGrid.deactivateTiles();
		}
	}
}
