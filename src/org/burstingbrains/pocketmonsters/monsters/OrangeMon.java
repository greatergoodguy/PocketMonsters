package org.burstingbrains.pocketmonsters.monsters;

import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class OrangeMon extends Monster{

	public OrangeMon(Universe universe) {
		super(universe);
	}
	
	@Override
	protected void initializeMonsterSprites(Universe universe) {
		monsterSpriteUp = universe.createSprite(assets.orangeMonUpTextureRegion);
		monsterSpriteLeft = universe.createSprite(assets.orangeMonLeftTextureRegion);
		monsterSpriteDown = universe.createSprite(assets.orangeMonDownTextureRegion);
		monsterSpriteRight = universe.createSprite(assets.orangeMonRightTextureRegion);
	}
}
