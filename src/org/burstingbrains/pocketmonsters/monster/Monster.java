package org.burstingbrains.pocketmonsters.monster;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.sprite.Sprite;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.sharedlibs.handler.IButtonHandler;

public class Monster implements IMonster, GameConstants{
	GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	// Monster Data Members
	IEntity monsterEntity;

	Sprite monsterSpriteUp;
	Sprite monsterSpriteLeft;
	Sprite monsterSpriteDown;
	Sprite monsterSpriteRight;
	Sprite monsterTouchTargetSprite;
	
	Sprite activeSprite;
	Dir monsterDir;

	// Menu Data Members
	IEntity monsterMenuEntity;
	
	Sprite buttonOk;
	Sprite buttonReset;
	Sprite buttonQuit;
	
	BBSHandler handler;
	
	boolean isMonsterMenuVisible;

	public Monster(Universe universe, BBSHandler handler){
		initializeMonsterSprites(universe);
		this.handler = handler;
		
		setFaceDirection(Dir.LEFT);
		
		initializeMonsterMenu(universe);
		
		monsterEntity.setScale(0.6f);
		
		universe.attachChild(monsterEntity);
	}

	private void setPos(float posX, float posY) {
		monsterEntity.setPosition(posX - monsterSpriteUp.getWidth()/2, posY - monsterSpriteUp.getHeight()/2);
		
	}
	
	@Override
	public void setGridPos(int coordX, int coordY) {
		setPos(coordX * PIXELS_PER_METER, coordY * PIXELS_PER_METER);
	};
	
	private void initializeMonsterSprites(Universe universe) {
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
		
		monsterTouchTargetSprite = universe.createButtonSprite(assets.touchTargetLightBlueTextureRegion, new ToggleMenuButtonHandler());
		universe.registerTouchArea(monsterTouchTargetSprite);
		monsterTouchTargetSprite.setVisible(true);
		monsterEntity.attachChild(monsterTouchTargetSprite);
		
		activeSprite = monsterSpriteDown;
	}

	private void initializeMonsterMenu(Universe universe) {
		monsterMenuEntity = new Entity(0, 0);
		
		buttonOk = universe.createButtonSprite(assets.menuButtonOkTextureRegion, new TurnLeftButtonHandler());
		universe.registerTouchArea(buttonOk);
		buttonOk.setPosition(0, -120);
		monsterMenuEntity.attachChild(buttonOk);
		
		buttonReset = universe.createButtonSprite(assets.menuButtonResetTextureRegion, new TurnRightButtonHandler());
		universe.registerTouchArea(buttonReset);
		buttonReset.setPosition(0, -60);
		monsterMenuEntity.attachChild(buttonReset);
		
		buttonQuit = universe.createButtonSprite(assets.menuButtonQuitTextureRegion);
		universe.registerTouchArea(buttonQuit);
		buttonQuit.setPosition(0, 0);
		monsterMenuEntity.attachChild(buttonQuit);
		
		monsterEntity.attachChild(monsterMenuEntity);
		
		isMonsterMenuVisible = true;
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

	private void toggleGridPos() {
		// TODO Auto-generated method stub
		
	}
	
	private void toggleMenuState() {
		if(isMonsterMenuVisible)
			monsterMenuEntity.setPosition(CAMERA_WIDTH * 5, CAMERA_HEIGHT * 5);
		else
			monsterMenuEntity.setPosition(0, 0);
		
		isMonsterMenuVisible = !isMonsterMenuVisible;
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

	public class ToggleGridPosButtonHandler implements IButtonHandler{
		@Override
		public void onButtonUp(){
			toggleGridPos();
		}
	};
	
	public class ToggleMenuButtonHandler implements IButtonHandler{
		@Override
		public void onButtonUp(){
			handler.onMonsterSelected(Monster.this);
			toggleMenuState();
		}
	}
}