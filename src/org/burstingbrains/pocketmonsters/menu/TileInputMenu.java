package org.burstingbrains.pocketmonsters.menu;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class TileInputMenu extends Rectangle implements GameConstants{

	public static final int DEFAULT_POS_X = 0;
	public static final int DEFAULT_POS_Y = 0;
	
	public TileInputMenu(final Universe universe) {
		super(0, 0, GRID_WIDTH_IN_METERS*PIXELS_PER_METER, GRID_HEIGHT_IN_METERS*PIXELS_PER_METER, universe.getVertexBufferObjectManager());
		
		// Set the overall scene to be transparent
		this.setColor(0, 0, 0, 0.5f);
		
		universe.attachChild(this);
		universe.registerTouchArea(this);
	}
	
	public void activate() {
		setPosition(DEFAULT_POS_X, DEFAULT_POS_Y);
		setVisible(true);
	}

	public void deactivate() {
		setPosition(VOID_ZONE_POS_X, VOID_ZONE_POS_Y);
		setVisible(false);		
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
				break;
			case TouchEvent.ACTION_UP:
				deactivate();
				break;
			}
		}
		return true;
	}
}
