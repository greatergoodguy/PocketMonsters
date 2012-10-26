package org.burstingbrains.pocketmonsters.menu;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.input.touch.TouchEvent;
import org.burstingbrains.pocketmonsters.actor.IMonster;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.menu.SharedMonsterMenu.SharedMonsterMenuHandler;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class TileInputMenu extends Rectangle implements GameConstants{

	public static final int DEFAULT_POS_X = 0;
	public static final int DEFAULT_POS_Y = 0;
	
	private SharedMonsterMenuHandler handler;
	
	private MovementSelectorGrid movementSelectorGrid;
	private EnemySelectorGrid enemySelectorGrid;
	
	public TileInputMenu(final Universe universe, SharedMonsterMenuHandler handler) {
		super(0, 0, GRID_WIDTH_IN_METERS*PIXELS_PER_METER, GRID_HEIGHT_IN_METERS*PIXELS_PER_METER, universe.getVertexBufferObjectManager());
		
		this.handler = handler;
		movementSelectorGrid = new MovementSelectorGrid(universe);
		enemySelectorGrid = new EnemySelectorGrid(universe, handler);
		
		// Set the overall scene to be transparent
		this.setColor(0, 0, 0, 0);
		

		universe.attachChild(this);
		universe.registerTouchArea(this);
	}
	
	public void activate() {
		setPosition(DEFAULT_POS_X, DEFAULT_POS_Y);
		setVisible(true);
	}

	public void deactivate() {
		setPosition(VOID_ZONE_POS_X, VOID_ZONE_POS_Y);
		setVisible(false);		
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, 
			final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		int posXInMeters = (int) (pTouchAreaLocalX / PIXELS_PER_METER);
		int posYInMeters = (int) (pTouchAreaLocalY / PIXELS_PER_METER);
		if (posXInMeters < GRID_WIDTH_IN_METERS &&
				posYInMeters < GRID_HEIGHT_IN_METERS) {

			switch(pSceneTouchEvent.getAction()) {
			case TouchEvent.ACTION_DOWN:
			case TouchEvent.ACTION_MOVE:
				break;
			case TouchEvent.ACTION_UP:
				handler.moveMonster(posXInMeters, posYInMeters);
				break;
			}
		}
		return true;
	}
	
	public void activateMovementTiles(IMonster monster) {	movementSelectorGrid.activateTiles(monster);}
	public void deactivateMovementTiles() {					movementSelectorGrid.deactivateTiles();}
	public void activeEnemyTiles(IMonster monster){			enemySelectorGrid.activateTiles(monster);}
	public void deactivateEnemyTiles(){						enemySelectorGrid.deactivateTiles();}
	
	
	
}
