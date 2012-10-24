package org.burstingbrains.pocketmonsters.actor;

import java.util.ArrayList;

import org.andengine.util.color.Color;
import org.burstingbrains.pocketmonsters.constants.TeamColorEnum;

public class Team {
	//-----------------------------------------------------------------------//
	// Members                                                               //
	//-----------------------------------------------------------------------//
	String name;
	TeamColorEnum team;
	Color color;
	ArrayList<Monster> monsters;
	
	//-----------------------------------------------------------------------//
	// Constructors                                                          //
	//-----------------------------------------------------------------------//
	public Team(String name, TeamColorEnum t) {
		this.name = name;
		team = t;
		monsters = new ArrayList<Monster>();
		
		// Set the color appropriate for the team
		switch (t) {
		case RED_TEAM:
			color = Color.RED;
			break;
		case BLUE_TEAM:
			color = Color.BLUE; 
			break;
		default:
			color = Color.WHITE;
		}
	}
	
	//-----------------------------------------------------------------------//
	// Methods																 //
	//-----------------------------------------------------------------------//
	
	public boolean addMonster(Monster m) {
		return monsters.add(m);
	}
	
	//-----------------------------------------------------------------------//
	// Getters																 //
	//-----------------------------------------------------------------------//
	public Color getColor() { return color; }								 //
	public ArrayList<Monster> getMonsters() { return monsters; }         	 //
	//-----------------------------------------------------------------------//
}