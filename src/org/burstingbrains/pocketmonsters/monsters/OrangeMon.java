package org.burstingbrains.pocketmonsters.monsters;

import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class OrangeMon extends Monster{

	private final static int MONSTER_HP = 3;
	private final static int MONSTER_ATTACK = 3;
	private final static int MONSTER_MOVEMENT = 3;
	
	public OrangeMon(Universe universe) {
		super(universe, MONSTER_HP, MONSTER_ATTACK, MONSTER_MOVEMENT);
	}
	
	@Override
	protected void initializeMonsterSprites(Universe universe) {
		monsterSpriteUp = universe.createSprite(assets.orangeMonUpTextureRegion);
		monsterSpriteLeft = universe.createSprite(assets.orangeMonLeftTextureRegion);
		monsterSpriteDown = universe.createSprite(assets.orangeMonDownTextureRegion);
		monsterSpriteRight = universe.createSprite(assets.orangeMonRightTextureRegion);
	}
}
