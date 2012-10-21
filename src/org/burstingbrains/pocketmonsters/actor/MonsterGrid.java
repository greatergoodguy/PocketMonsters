package org.burstingbrains.pocketmonsters.actor;

import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.monsters.BadlyDrawnMonster;
import org.burstingbrains.pocketmonsters.monsters.OrangeMon;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class MonsterGrid implements GameConstants{
	private MonsterGridHandler handler;

	private IMonster[][] monsters;
	
	public MonsterGrid(Universe universe){
		handler = new MonsterGridHandler();

		monsters = new Monster[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		
		Monster monster1 = new BadlyDrawnMonster(universe, handler);
		monster1.setGridPos(1, 1);
		monsters[1][1] = monster1;
		
		Monster monster2 = new OrangeMon(universe, handler);
		monster2.setGridPos(3, 4);
		monsters[3][4] = monster2;
	}
	
	public IMonster get(final int coordX, final int coordY){
		return monsters[coordX][coordY];
	}
	
	public class MonsterGridHandler{

		public void updateGrid(Monster monster, int oldX,
				int oldY, int newX, int newY) {
			
			monsters[oldX][oldY] = null;
			monsters[newX][newY] = monster;
		}
		
	}
}
