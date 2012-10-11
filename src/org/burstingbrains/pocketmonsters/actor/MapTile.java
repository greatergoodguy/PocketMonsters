package org.burstingbrains.pocketmonsters.actor;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.primitive.vbo.IRectangleVertexBufferObject;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.burstingbrains.pocketmon.constants.GameConstants;

public class MapTile extends Rectangle{
	public MapTile(float pX, float pY, float pWidth, float pHeight,
			IRectangleVertexBufferObject iRectangleVertexBufferObject) {
		super(pX, pY, pWidth, pHeight, iRectangleVertexBufferObject);
	}
	public MapTile(int pX, int pY, int pixelsPerMeter, int pixelsPerMeter2,
			VertexBufferObjectManager vertexBufferObjectManager) {
		super(pX, pY, pixelsPerMeter, pixelsPerMeter2, vertexBufferObjectManager);
	}
	
	public int getGridX() {
		return (int) (getX() / GameConstants.PIXELS_PER_METER) + 1;
	}
	
	public int getGridY() {
		return (int) (getY() / GameConstants.PIXELS_PER_METER) + 1;
	}
	
	// Keep track of monsters on the grid position
	Monster monster = null;

	public Monster getMonster() {
		return monster;
	}
	public void setMonster(Monster monster) {
		this.monster = monster;
	}
}
