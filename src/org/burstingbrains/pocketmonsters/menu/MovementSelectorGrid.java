package org.burstingbrains.pocketmonsters.menu;

import org.andengine.entity.primitive.Rectangle;
import org.burstingbrains.pocketmonsters.actor.IMonster;
import org.burstingbrains.pocketmonsters.actor.MapTile;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.pocketmonsters.util.GridUtil;

import android.util.Log;

public class MovementSelectorGrid extends Rectangle implements GameConstants{

	private MapTile[][] grid;
	
	public MovementSelectorGrid(Universe universe){
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager());

		this.setColor(0, 0, 0, 0);
		
		grid = new MapTile[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		for(int column = 0; column < GRID_WIDTH_IN_METERS; column++){
			for(int row = 0; row < GRID_HEIGHT_IN_METERS; row++){
				MapTile tile = new MapTile(column * PIXELS_PER_METER, row * PIXELS_PER_METER, PIXELS_PER_METER,
						PIXELS_PER_METER, universe.getVertexBufferObjectManager());
				tile.setColor(0, 255, 255);
				tile.setVisible(false);

				attachChild(tile);

				grid[column][row] = tile;
			}
		}
		
		universe.attachChild(this);
	}

	/*
	 * Need to optimize
	 */
	public void activateTiles(IMonster monster) {
		final int movementPoints = monster.getMovementPoints();
		final int monsterGridPosX = monster.getGridPosX();
		final int monsterGridPosY = monster.getGridPosY();
		
		int minX = Math.max(0, monsterGridPosX - movementPoints);
		int maxX = Math.min(GRID_WIDTH_IN_METERS - 1, monsterGridPosX + movementPoints);
		int minY = Math.max(0, monsterGridPosY - movementPoints);
		int maxY = Math.min(GRID_HEIGHT_IN_METERS - 1, monsterGridPosY + movementPoints);
		
		for(int i=minX; i<=maxX; ++i){
			for(int j=minY; j<=maxY; ++j){
				
				int distance = GridUtil.getDistance(i, j, monsterGridPosX, monsterGridPosY);
				
				if(distance <= movementPoints){
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
