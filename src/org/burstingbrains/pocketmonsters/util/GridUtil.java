package org.burstingbrains.pocketmonsters.util;

import org.burstingbrains.pocketmonsters.constants.GameConstants;

public class GridUtil implements GameConstants{

	private GridUtil(){
	}
	
	public static int getDistance(int x1, int y1, int x2, int y2){
		
		int xDistance = Math.abs(x1 - x2);
		int yDistance = Math.abs(y1 - y2);
		
		return xDistance + yDistance;
	}
	
	public static boolean isValidCoordinate(final int x, final int y){
		if(x >= 0 && x < GRID_WIDTH_IN_METERS && y >=0 && y < GRID_HEIGHT_IN_METERS){
			return true;
		}
		
		return false;
	}
}
