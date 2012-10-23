package org.burstingbrains.pocketmonsters.monstercard;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class MonsterCard extends Rectangle implements GameConstants{
	
	private final static int MONSTERCARD_WIDTH = 500;
	private final static int MONSTERCARD_HEIGHT = 250;
	
	GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private Monster monster;
	private Sprite profilePicture;
	
	public MonsterCard(Universe universe, Monster monster){
		super(0, 0, MONSTERCARD_WIDTH, MONSTERCARD_HEIGHT, universe.getVertexBufferObjectManager());

		this.monster = monster;
		this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		profilePicture = universe.createSprite(assets.orangeMonDownTextureRegion);
		profilePicture.setScale(0.4f);
		profilePicture.setPosition(-70, -70);
		attachChild(profilePicture);
		
		universe.attachChild(this);
		
		this.setPosition(2*CAMERA_WIDTH/3, 2*CAMERA_HEIGHT/3);
		
	}

}
