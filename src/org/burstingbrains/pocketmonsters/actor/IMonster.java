package org.burstingbrains.pocketmonsters.actor;

public interface IMonster {
	
	public static enum Dir{ DOWN, LEFT, UP, RIGHT };
	
	public void setGridPos(int coordX, int coordY);

	public void turnLeft();
}
