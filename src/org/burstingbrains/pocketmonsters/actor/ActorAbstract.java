/**
 * @author Taylor McKinney
 */
package org.burstingbrains.pocketmonsters.actor;
import org.burstingbrains.pocketmonsters.util.MapPosition;

public class ActorAbstract {
	private MapPosition position;
	
	// Constructors
	public ActorAbstract(int map_position_x, int map_position_y){
		setPosition(new MapPosition(map_position_x, map_position_y));
	}

	// Getters and setters
	public MapPosition getPosition() {
		return position;
	}
	
	public void setPosition(MapPosition position) {
		this.position = position;
	}
}
