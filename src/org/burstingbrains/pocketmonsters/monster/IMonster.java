package org.burstingbrains.pocketmonsters.monster;

public interface IMonster {
	
	public static enum Dir{ DOWN, LEFT, UP, RIGHT };
	
	public void setPos(float posX, float posY);
	public void setGridPos(int coordX, int coordY);
}
