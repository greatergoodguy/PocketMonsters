package org.burstingbrains.pocketmonsters;
import java.util.ArrayList;
import java.util.List;

import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.actor.*;

public class GameLogic {
	private GameLogic() {} //  Designed for static methods

	static MapTile currentMapTile = null;
	static Color savedColor = Color.GREEN;

	public static void addMonster(Monster m, MapTile t) {
		t.setMonster(m);
		m.setGridPos(t.getGridX(), t.getGridY());
	}

	/**
	 * The value of t.monster WILL BE CHANGED !!
	 * @param t 
	 */
	private static void moveMonster(MapTile t) {
		t.setMonster(currentMapTile.getMonster());
		currentMapTile.setMonster(null);

		t.getMonster().setGridPos(t.getGridX(), t.getGridY());
	}

	private static void setTile(MapTile t) {
		if (currentMapTile != null) {
			currentMapTile.setColor(savedColor);
		}
		if (t != null) {
			savedColor.set(t.getColor());
			t.setColor(Color.RED);
		}
		currentMapTile = t;
	}

	public static void selectTile(MapTile t) {
		// We don't have a tile selected yet and are going to select one
		// or
		// We already have a tile selected and are clicking another tile
		// No monster on our current Map Tile, we just select the new tile
		if (currentMapTile == null || currentMapTile.getMonster() == null) { setTile(t); }

		// We already have a tile selected and wish to deselect it
		else if (currentMapTile == t) { setTile(null); }


		// We have a monster
		else if (t.getMonster() == null) {
			moveMonster(t);
			setTile(t);
		}

		// This is where we attack !!
		else {
			// TODO ATTACK !!
		}
	}
}
