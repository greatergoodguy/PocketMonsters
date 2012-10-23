package org.burstingbrains.pocketmonsters.actor;

import java.util.HashMap;

import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.constants.TeamColorEnum;
import org.burstingbrains.pocketmonsters.monstercard.MonsterCard;
import org.burstingbrains.pocketmonsters.monsters.BadlyDrawnMonster;
import org.burstingbrains.pocketmonsters.monsters.OrangeMon;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class MonsterGrid implements GameConstants{
	//============================================================
	// Members
	//============================================================
	private MonsterGridHandler handler;
	private IMonster[][] monsters;
	private HashMap<IMonster, MonsterCard> monsterCardHashMap;
	
	Team RedTeam;
	Team BlueTeam;
	
	//============================================================
	// Constructors
	//============================================================
	
	public MonsterGrid(Universe universe){
		handler = new MonsterGridHandler();

		monsters = new Monster[GRID_WIDTH_IN_METERS][GRID_HEIGHT_IN_METERS];
		monsterCardHashMap = new HashMap<IMonster, MonsterCard>();
		
		RedTeam = new Team("Red Team", TeamColorEnum.RED_TEAM);
		BlueTeam = new Team("Blue Team", TeamColorEnum.BLUE_TEAM);
		
		Monster monster1 = new BadlyDrawnMonster(universe, handler, RedTeam);
		monster1.setGridPos(1, 1);
		monsters[1][1] = monster1;
		monsterCardHashMap.put(monster1, new MonsterCard(universe, monster1));
		
		Monster monster2 = new OrangeMon(universe, handler, BlueTeam);
		monster2.setGridPos(3, 4);
		monsters[3][4] = monster2;
	}
	
	//============================================================
	// Methods
	//============================================================
	
	public IMonster get(final int coordX, final int coordY){
		return monsters[coordX][coordY];
	}
	
	//============================================================
	// Inner Classes
	//============================================================
	
	public class MonsterGridHandler{
		public void updateGrid(Monster monster, int oldX,
				int oldY, int newX, int newY) {
			monsters[oldX][oldY] = null;
			monsters[newX][newY] = monster;
		}
	}
}
