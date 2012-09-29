package org.burstingbrains.pocketmon.grid;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.andengine.util.color.Color;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class Grid extends Rectangle implements GameConstants{
	Universe universe;

	public Grid(final Universe universe) {
		super(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT, universe.getVertexBufferObjectManager(), DrawType.STATIC);
		this.universe = universe;
		
		universe.registerTouchArea(this);
		universe.attachChild(this);
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				setColor(Color.CYAN);
				break;
			case TouchEvent.ACTION_OUTSIDE:
			case TouchEvent.ACTION_CANCEL:
			case TouchEvent.ACTION_UP:
				setColor(Color.WHITE);
				break;
		}
		return true;
	}
}
