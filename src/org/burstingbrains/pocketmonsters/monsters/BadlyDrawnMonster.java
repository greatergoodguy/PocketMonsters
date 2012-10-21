package org.burstingbrains.pocketmonsters.monsters;

import org.andengine.entity.sprite.AnimatedSprite;
import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class BadlyDrawnMonster extends Monster{

	private final static int MONSTER_HP = 3;
	private final static int MONSTER_ATTACK = 3;
	private final static int MONSTER_MOVEMENT = 3;
	
	public BadlyDrawnMonster(Universe universe) {
		super(universe, MONSTER_HP, MONSTER_ATTACK, MONSTER_MOVEMENT);
	}

	@Override
	protected void initializeMonsterSprites(Universe universe) {
		monsterSpriteUp = universe.createAnimatedSprite(assets.badlyDrawnMonsterUpTextureRegion);
		((AnimatedSprite) monsterSpriteUp).animate(500);

		monsterSpriteLeft = universe.createAnimatedSprite(assets.badlyDrawnMonsterLeftTextureRegion);
		((AnimatedSprite) monsterSpriteLeft).animate(500);

		monsterSpriteDown = universe.createAnimatedSprite(assets.badlyDrawnMonsterDownTextureRegion);
		((AnimatedSprite) monsterSpriteDown).animate(500);

		monsterSpriteRight = universe.createAnimatedSprite(assets.badlyDrawnMonsterRightTextureRegion);
		((AnimatedSprite) monsterSpriteRight).animate(500);
	}
}
