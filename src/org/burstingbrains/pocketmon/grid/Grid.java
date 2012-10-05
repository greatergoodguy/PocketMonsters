package org.burstingbrains.pocketmon.grid;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.handler.IOnGridTouchUp;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;
	IOnGridTouchUp handler;
	
	private Rectangle dummyRectangle;
	private Rectangle activeRectangle;
	private Rectangle[][] grid2;
	
	private int posX = -1;
	private int posY = -1;
	
	public Grid(final Universe universe, IOnGridTouchUp handler) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager(), DrawType.STATIC);
		this.universe = universe;
		this.handler = handler;
		
		universe.registerTouchArea(this);
		universe.attachChild(this);
		
		// This dummyRectangle is what the activeRectangle will point to when 
		// it is in an inactive state
		dummyRectangle = new Rectangle(0, 0, 1, 1, getVertexBufferObject());
		activeRectangle = dummyRectangle;
		
		grid2 = new Rectangle[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		
		for(int column = 0; column < GRID_WIDTH_IN_METERS; column++){
			for(int row = 0; row < GRID_HEIGHT_IN_METERS; row++){
				Rectangle tile = new Rectangle(column * PIXELS_PER_METER, row * PIXELS_PER_METER, PIXELS_PER_METER,
											   PIXELS_PER_METER, getVertexBufferObjectManager());
				tile.setColor(
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256), 
						RandomSingleton.getRandomInt(256));
				
				attachChild(tile);
				
				grid2[column][row] = tile;
				
			}
		}
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		
		switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				int posXInMeters = (int) (pTouchAreaLocalX / PIXELS_PER_METER);
				int posYInMeters = (int) (pTouchAreaLocalY / PIXELS_PER_METER);
				Log.d("Grid", "posX, posY: " + posXInMeters + ", " + posYInMeters);
				
				Rectangle currentRectangle;
				if(posXInMeters < GRID_WIDTH_IN_METERS && posYInMeters < GRID_HEIGHT_IN_METERS) {
					currentRectangle = grid2[posXInMeters][posYInMeters];
					posX = posXInMeters + 1;
					posY = posYInMeters + 1;
				}
				else {
					currentRectangle = dummyRectangle;
					posX = 0;
					posY = 0;
				}
				
				if(activeRectangle != currentRectangle){
					activeRectangle.setColor(Color.GREEN);
					activeRectangle = currentRectangle;
					activeRectangle.setColor(Color.BLUE);
				}
				break;
			case TouchEvent.ACTION_UP:
				handler.onGridTouchUp();
				break;
		}
		return true;
	}
	
	public boolean isValidPosition() {
		return activeRectangle != dummyRectangle;
	}
	
	public int getPositionX() {
		return posX;
	}
	
	public int getPositionY() {
		return posY;
	}
}
