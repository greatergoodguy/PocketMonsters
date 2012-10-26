package org.burstingbrains.pocketmonsters.menu;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.actor.IMonster;
import org.burstingbrains.pocketmonsters.actor.MapTile;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.menu.SharedMonsterMenu.SharedMonsterMenuHandler;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class EnemySelectorGrid extends Rectangle implements GameConstants{

	private SharedMonsterMenuHandler handler;
	
	private MapTile[][] grid;
	
	public EnemySelectorGrid(Universe universe, SharedMonsterMenuHandler handler){
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager());

		this.handler = handler;
		
		grid = new MapTile[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		for(int column = 0; column < GRID_WIDTH_IN_METERS; column++){
			for(int row = 0; row < GRID_HEIGHT_IN_METERS; row++){
				MapTile tile = new MapTile(column * PIXELS_PER_METER, row * PIXELS_PER_METER, PIXELS_PER_METER,
						PIXELS_PER_METER, universe.getVertexBufferObjectManager());
				tile.setColor(Color.RED);
				tile.setVisible(false);

				attachChild(tile);

				grid[column][row] = tile;
			}
		}
		
		this.setColor(0, 0, 0, 0);
		universe.attachChild(this);
	}

	/*
	 * Need to optimize
	 */
	public void activateTiles(IMonster monster) {
		final int posX = monster.getGridPosX();
		final int posY = monster.getGridPosY();
		
		int minX = Math.max(0, posX - 1);
		int maxX = Math.min(GRID_WIDTH_IN_METERS - 1, posX + 1);
		int minY = Math.max(0, posY - 1);
		int maxY = Math.min(GRID_HEIGHT_IN_METERS - 1, posY + 1);
		
		for(int i=minX; i<=maxX; ++i){
			for(int j=minY; j<=maxY; ++j){
				if(handler.isTileOccupied(i, j)){
					grid[i][j].setVisible(true);
				}
			}
		}
	}

	/*
	 * Need to optimize
	 */
	public void deactivateTiles() {
		for(int i=0; i<grid.length; ++i){
			for(int j=0; j<grid[0].length; ++j){
				grid[i][j].setVisible(false);
			}
		}
	}

	public boolean isSelected(int coordX, int coordY) {
		return grid[coordX][coordY].isVisible();
	}
}
