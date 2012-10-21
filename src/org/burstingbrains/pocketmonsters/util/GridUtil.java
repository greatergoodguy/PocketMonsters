package org.burstingbrains.pocketmonsters.util;

public class GridUtil {

	private GridUtil(){
	}
	
	public static int getDistance(int x1, int y1, int x2, int y2){
		
		int xDistance = Math.abs(x1 - x2);
		int yDistance = Math.abs(y1 - y2);
		
		return xDistance + yDistance;
	}
}
