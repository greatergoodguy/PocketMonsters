package org.burstingbrains.pocketmon.grid;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;
	
	private Rectangle dummyRectangle;
	private Rectangle activeRectangle;
	private Rectangle[][] grid2;
	
	public Grid(final Universe universe) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager(), DrawType.STATIC);
		this.universe = universe;
		
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
				//tile.setColor(Color.GREEN);
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
				}
				else {
					currentRectangle = dummyRectangle;
				}
				
				if(activeRectangle != currentRectangle){
					activeRectangle.setColor(Color.GREEN);
					activeRectangle = currentRectangle;
					activeRectangle.setColor(Color.BLUE);
				}
				break;
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_UP:
				break;
		}
		return true;
	}
}
