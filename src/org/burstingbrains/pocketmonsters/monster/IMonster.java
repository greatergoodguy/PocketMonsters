package org.burstingbrains.pocketmonsters.monster;

public interface IMonster {
	
	public static enum Dir{ DOWN, LEFT, UP, RIGHT };
	
	public void setGridPos(int coordX, int coordY);
}
