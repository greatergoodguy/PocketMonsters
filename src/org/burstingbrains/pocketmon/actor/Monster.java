package org.burstingbrains.pocketmon.actor;

public class Monster extends ActorAbstract{
	// Stats
	String name;
	int attack;
	int health;
	// AbilityInterface ability;

	public Monster(int map_position_x, int map_position_y, String _name, int _attack, int _health){
		super(map_position_x, map_position_y);
		name = _name;
		attack = _attack;
		health = _health;
	}
}
