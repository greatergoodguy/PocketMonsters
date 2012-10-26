package org.burstingbrains.pocketmonsters.actor;

import org.andengine.opengl.texture.region.ITextureRegion;

public interface IMonster {
	
	public static enum Dir{ DOWN, LEFT, UP, RIGHT };
	
	public void setGridPos(int coordX, int coordY);

	public void turnLeft();
	
	public int getHealthPoints();
	public int getAttackPower();
	public int getMovementPoints();
	public ITextureRegion getProfilePicTextureRegion();
	
	public int getGridPosX();
	public int getGridPosY();

	public void takeDamage(int attackPower);	
}
