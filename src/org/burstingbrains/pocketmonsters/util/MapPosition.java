package org.burstingbrains.pocketmonsters.util;

public class MapPosition {
	private int position_x;
	private int position_y;
	
	public MapPosition(int _x, int _y){
		setPosition_x(_x);
		setPosition_y(_y);
	}

	public int getPosition_x() {
		return position_x;
	}

	public void setPosition_x(int position_x) {
		this.position_x = position_x;
	}

	public int getPosition_y() {
		return position_y;
	}

	public void setPosition_y(int position_y) {
		this.position_y = position_y;
	}
}
