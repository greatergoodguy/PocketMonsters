package org.burstingbrains.pocketmonsters.actor;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.GameLogic;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;
	BBSHandler handler;

	private MapTile dummyMapTile;
	private MapTile activeMapTile;
	private MapTile[][] grid;

	private int posX = -1;
	private int posY = -1;

	public Grid(final Universe universe) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager(), DrawType.STATIC);
		this.universe = universe;

		universe.registerTouchArea(this);
		universe.attachChild(this);

		// This dummyRectangle is what the activeRectangle will point to when 
		// it is in an inactive state
		dummyMapTile = new MapTile(0, 0, 1, 1, getVertexBufferObject());
		activeMapTile = dummyMapTile;

		grid = new MapTile[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];

		for(int column = 0; column < GRID_WIDTH_IN_METERS; column++){
			for(int row = 0; row < GRID_HEIGHT_IN_METERS; row++){
				MapTile tile = new MapTile(column * PIXELS_PER_METER, row * PIXELS_PER_METER, PIXELS_PER_METER,
						PIXELS_PER_METER, getVertexBufferObjectManager());
				tile.setColor(
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256));

				attachChild(tile);

				grid[column][row] = tile;
			}
		}
	}

	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		int posXInMeters = (int) (pTouchAreaLocalX / PIXELS_PER_METER);
		int posYInMeters = (int) (pTouchAreaLocalY / PIXELS_PER_METER);
		Log.d("Grid", "posX, posY: " + posXInMeters + ", " + posYInMeters);
		if (posXInMeters < GRID_WIDTH_IN_METERS &&
				posYInMeters < GRID_HEIGHT_IN_METERS) {

			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
				GameLogic.actionDown(grid[posXInMeters][posYInMeters]);
				break;
			case TouchEvent.ACTION_MOVE:
				GameLogic.actionMove(grid[posXInMeters][posYInMeters]);
				break;
			case TouchEvent.ACTION_UP:
				GameLogic.actionUp(grid[posXInMeters][posYInMeters]);
				break;
			}
		}
		return true;
	}

	public MapTile getMapTileAt(int x, int y) {
		return grid[x][y];
	}
}
