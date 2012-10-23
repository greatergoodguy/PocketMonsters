package org.burstingbrains.pocketmonsters.actor;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.sprite.Sprite;
import org.andengine.util.debug.Debug;
import org.andengine.util.modifier.ease.EaseLinear;
import org.burstingbrains.pocketmonsters.actor.MonsterGrid.MonsterGridHandler;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.constants.Team;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.sharedlibs.handler.IButtonHandler;

public abstract class Monster implements IMonster, GameConstants{
	protected GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();

	protected final String TAG = this.getClass().getSimpleName();
	
	private MonsterGridHandler monsterGridHandler;
	
	// Monster Data Members
	private IEntity monsterEntity;

	protected Sprite monsterSpriteUp;
	protected Sprite monsterSpriteLeft;
	protected Sprite monsterSpriteDown;
	protected Sprite monsterSpriteRight;

	private Sprite activeSprite;
	private Dir monsterDir;

	private int gridPosX;
	private int gridPosY;
	
	private final int healthPoints;
	private final int attackPower;
	private final int movementPoints;
	
	private float scaleFactor = 0.6f;

	boolean isMonsterMenuVisible;
	boolean isMonsterEntityModifierActive;
	
	Team team;

	public Monster(Universe universe, MonsterGridHandler handler, Team team, int healthPoints, int attackPower, int movementPoints){
		this.monsterGridHandler = handler;
		
		this.healthPoints = healthPoints;
		this.attackPower = attackPower;
		this.movementPoints = movementPoints;
		
		this.team = team;
		
		monsterEntity = new Entity(0, 0);
		
		initializeMonsterSprites(universe);
		attachMonsterSprites();
		setFaceDirection(Dir.LEFT);

		monsterEntity.setScale(scaleFactor);

		universe.attachChild(monsterEntity);
		
		setGridPos(0, 0);
	}

	private void setPos(float posX, float posY) {
		monsterEntity.setPosition(posX - scaleFactor * monsterSpriteLeft.getWidth()/2 + PIXELS_PER_METER/2,
								  posY - scaleFactor * monsterSpriteLeft.getHeight()/2 + PIXELS_PER_METER/2);
		
	}

	@Override
	public void setGridPos(int coordX, int coordY) {	
		final int oldGridPosX = getGridPosX();
		final int oldGridPosY = getGridPosY();
		
		gridPosX = coordX;
		gridPosY = coordY;
		
		setPos(coordX * PIXELS_PER_METER, coordY * PIXELS_PER_METER);
		
		monsterGridHandler.updateGrid(this, oldGridPosX, oldGridPosY, gridPosX, gridPosY);
	}

	protected abstract void initializeMonsterSprites(Universe universe);
	
	private void attachMonsterSprites() {
		monsterSpriteUp.setVisible(false);
		monsterEntity.attachChild(monsterSpriteUp);

		monsterSpriteLeft.setVisible(false);
		monsterEntity.attachChild(monsterSpriteLeft);
		
		monsterSpriteDown.setVisible(false);
		monsterEntity.attachChild(monsterSpriteDown);
		
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

	@Override
	public void turnLeft(){
		
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

	private void translateMonster() {
		if(!isMonsterEntityModifierActive){
			float durationInSec = 3;
			final Path path = new Path(2).to(10, 10).to(600, 10); // Creates a new 'Path' that can hold 2 coordinates

			monsterEntity.registerEntityModifier(new PathModifier(durationInSec, path, null, new IPathModifierListener() {
				@Override
				public void onPathStarted(final PathModifier pPathModifier, final IEntity pEntity) {
					Debug.d("onPathStarted");

					isMonsterEntityModifierActive = true;
				}

				@Override
				public void onPathWaypointStarted(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					Debug.d("onPathWaypointStarted:  " + pWaypointIndex);
				}

				@Override
				public void onPathWaypointFinished(final PathModifier pPathModifier, final IEntity pEntity, final int pWaypointIndex) {
					Debug.d("onPathWaypointFinished: " + pWaypointIndex);
				}

				@Override
				public void onPathFinished(final PathModifier pPathModifier, final IEntity pEntity) {
					Debug.d("onPathFinished");

					isMonsterEntityModifierActive = false;
				}
			}, EaseLinear.getInstance()));
		}

	}

	public int getMaxMovement() { return 3; }
	
	@Override
	public int getHealthPoints() {
		return healthPoints;
	}

	@Override
	public int getAttackPower() {
		return attackPower;
	}

	@Override
	public int getMovementPoints() {
		return movementPoints;
	}
	
	@Override
	public int getGridPosX() {
		return gridPosX;
	}
	
	@Override
	public int getGridPosY() {
		return gridPosY;
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

	public class TranslateMonsterButtonHandler implements IButtonHandler{
		@Override
		public void onButtonUp(){
			translateMonster();
		}
	};
}
