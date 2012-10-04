package org.burstingbrains.pocketmonsters.monster;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.sharedlibs.handler.IButtonHandler;

public class Monster implements IMonster{
	GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	// Monster Data Members
	IEntity monsterEntity;

	Sprite monsterSpriteUp;
	Sprite monsterSpriteLeft;
	Sprite monsterSpriteDown;
	Sprite monsterSpriteRight;
	
	Sprite activeSprite;
	Dir monsterDir;

	// Menu Data Members
	Sprite buttonOk;
	Sprite buttonReset;
	Sprite buttonQuit;
	

	public Monster(Universe universe){
		initializeSprites(universe);
		setFaceDirection(Dir.LEFT);
		
		buttonOk = universe.createButtonSprite(assets.menuButtonOkTextureRegion, new TurnLeftButtonHandler());
		universe.registerTouchArea(buttonOk);
		buttonOk.setPosition(0, 0);
		monsterEntity.attachChild(buttonOk);
		
		buttonReset = universe.createButtonSprite(assets.menuButtonResetTextureRegion, new TurnRightButtonHandler());
		universe.registerTouchArea(buttonReset);
		buttonReset.setPosition(0, 120);
		monsterEntity.attachChild(buttonReset);
		
		buttonQuit = universe.createButtonSprite(assets.menuButtonQuitTextureRegion);
		universe.registerTouchArea(buttonQuit);
		buttonQuit.setPosition(0, 240);
		monsterEntity.attachChild(buttonQuit);
		
		
		universe.attachChild(monsterEntity);
	}

	@Override
	public void setPosition(float posX, float posY) {
		monsterEntity.setPosition(posX, posY);
		
	}
	
	private void initializeSprites(Universe universe) {
		monsterEntity = new Entity(0, 0);
		
		monsterSpriteUp = universe.createSprite(assets.orangeMonUpTextureRegion);
		monsterSpriteUp.setVisible(false);
		monsterEntity.attachChild(monsterSpriteUp);

		monsterSpriteLeft = universe.createSprite(assets.orangeMonLeftTextureRegion);
		monsterSpriteLeft.setVisible(false);
		monsterEntity.attachChild(monsterSpriteLeft);

		monsterSpriteDown = universe.createSprite(assets.orangeMonDownTextureRegion);
		monsterSpriteDown.setVisible(false);
		monsterEntity.attachChild(monsterSpriteDown);

		monsterSpriteRight = universe.createSprite(assets.orangeMonRightTextureRegion);
		monsterSpriteRight.setVisible(false);
		monsterEntity.attachChild(monsterSpriteRight);	
		
		activeSprite = monsterSpriteDown;
	}

	private void setFaceDirection(Dir direction) {
		activeSprite.setVisible(false);
		
		switch(direction){
		case DOWN:
			activeSprite = monsterSpriteDown;
			break;
		case LEFT:
			activeSprite = monsterSpriteLeft;
			break;
		case UP:
			activeSprite = monsterSpriteUp;
			break;
		case RIGHT:
			activeSprite = monsterSpriteRight;
			break;
		}
		
		activeSprite.setVisible(true);
		monsterDir = direction;
	}
	
	private void turnLeft(){
		switch(monsterDir){
		case DOWN:
			setFaceDirection(Dir.RIGHT);
			break;
		case LEFT:
			setFaceDirection(Dir.DOWN);
			break;
		case UP:
			setFaceDirection(Dir.LEFT);
			break;
		case RIGHT:
			setFaceDirection(Dir.UP);
			break;
		}
	}
	
	private void turnRight(){
		switch(monsterDir){
		case DOWN:
			setFaceDirection(Dir.LEFT);
			break;
		case LEFT:
			setFaceDirection(Dir.UP);
			break;
		case UP:
			setFaceDirection(Dir.RIGHT);
			break;
		case RIGHT:
			setFaceDirection(Dir.DOWN);
			break;
		}
	}
	
	public class TurnLeftButtonHandler implements IButtonHandler{
		@Override
		public void onButtonUp(){
			turnLeft();
		}
	};

	
	public class TurnRightButtonHandler implements IButtonHandler{
		@Override
		public void onButtonUp(){
			turnRight();
		}
	};
}
