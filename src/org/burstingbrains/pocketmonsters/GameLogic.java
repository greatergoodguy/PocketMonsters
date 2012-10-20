package org.burstingbrains.pocketmonsters;

import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.actor.*;

public class GameLogic {
	public GameLogic() {} //  Designed for static methods

	static MapTile startTile = null;
	static MapTile finishTile = null;
	static Color savedStartTileColor = Color.GREEN;
	static Color savedFinishTileColor = Color.GREEN;

	public static void addMonster(Monster m, MapTile t) {
		t.setMonster(m);
		m.setGridPos(t.getGridX(), t.getGridY());
	}

	/**
	 * The value of t.monster WILL BE CHANGED !!
	 * The value of startTile.monster WILL BE CHANGED !!
	 * @param t 
	 */
	private static void moveMonster(MapTile t) {
		t.setMonster(startTile.getMonster());
		startTile.setMonster(null);

		t.getMonster().setGridPos(t.getGridX(), t.getGridY());
	}

	private static void setStartTile(MapTile t) {
		if (startTile != null) {
			startTile.setColor(savedStartTileColor);
		}
		if (t != null) {
			savedStartTileColor.set(t.getColor());
			t.setColor(Color.RED);
		}
		startTile = t;
	}

	private static void setFinishTile(MapTile t) {
		if (finishTile != null) {
			finishTile.setColor(savedStartTileColor);
		}
		if (t != null) {
			savedFinishTileColor.set(t.getColor());
			t.setColor(Color.RED);
		}
		finishTile = t;
	}

	public static void actionDown(MapTile t) {
		// We don't have a tile selected yet and are going to select one
		// or
		// We already have a tile selected and are clicking another tile
		// No monster on our current Map Tile, we just select the new tile
		if (startTile == null) {
			setStartTile(t);
			setFinishTile(t);
		}
	}

	public static void actionMove(MapTile t) { setFinishTile(t); }

	public static void actionUp(MapTile t) {
		// We have a monster
		if (startTile != null & startTile.getMonster() != null) {
			int distance = Math.abs(t.getGridX() - startTile.getGridX()) + 
					Math.abs(t.getGridY() - startTile.getGridY());
			if (distance <= startTile.getMonster().getMaxMovement()) {
				if (t.getMonster() == null) {
					moveMonster(finishTile);
				} else {
					// TODO ATTACK !!
				}
				setStartTile(null);
			}
		} else setStartTile(t); // Just move to the new tile
		setFinishTile(null);
	}
	
	public void usesTheGrid(Grid g) {
	}
}
