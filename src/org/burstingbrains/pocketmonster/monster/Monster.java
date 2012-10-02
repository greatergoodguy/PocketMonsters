package org.burstingbrains.pocketmonster.monster;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class Monster {
	
	GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	IEntity monsterEntity;

	Sprite monsterSpriteUp;
	Sprite monsterSpriteLeft;
	Sprite monsterSpriteDown;
	Sprite monsterSpriteRight;

	public Monster(Universe universe){
		monsterEntity = new Entity(0, 0);
		
		monsterSpriteUp = universe.createSprite(assets.orangeMonUpTextureRegion);
		monsterSpriteLeft = universe.createSprite(assets.orangeMonLeftTextureRegion);
		monsterSpriteDown = universe.createSprite(assets.orangeMonDownTextureRegion);
		monsterSpriteRight = universe.createSprite(assets.orangeMonRightTextureRegion);
		
		monsterEntity.attachChild(monsterSpriteUp);
		monsterEntity.attachChild(monsterSpriteLeft);
		monsterEntity.attachChild(monsterSpriteDown);
		monsterEntity.attachChild(monsterSpriteRight);
		
		universe.attachChild(monsterEntity);
	}
	
}
