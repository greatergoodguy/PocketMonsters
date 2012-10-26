package org.burstingbrains.pocketmonsters.menu;

import java.util.ArrayList;

import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.DrawType;
import org.burstingbrains.pocketmonsters.actor.IMonster;
import org.burstingbrains.pocketmonsters.actor.World.WorldHandler;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.singleton.RandomSingleton;
import org.burstingbrains.pocketmonsters.universe.Universe;
import org.burstingbrains.pocketmonsters.util.GridUtil;

public class SharedMonsterMenu extends Rectangle implements GameConstants{
	
	public static final int DEFAULT_POS_X = 2*CAMERA_WIDTH/3;
	public static final int DEFAULT_POS_Y = 0;
	
	public static final int ITEM_WIDTH = 500;
	public static final int ITEM_HEIGHT = 100;
	
	public static final int MAX_SIZE = 5;

	private GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private WorldHandler worldHandler;
	
	private TileInputMenu tileInputMenu;
	
	private ArrayList<Rectangle> buttons;
	private Rectangle selectedButton;
	
	private IMonster activeMonster;
	
	public SharedMonsterMenu(Universe universe, WorldHandler worldHandler){
		super(0, 0, ITEM_WIDTH, ITEM_HEIGHT*MAX_SIZE, universe.getVertexBufferObjectManager(), DrawType.STATIC);		
		
		this.worldHandler = worldHandler;
		
		tileInputMenu = new TileInputMenu(universe, new SharedMonsterMenuHandler());
		tileInputMenu.deactivate();
		
		// Create Buttons
		buttons = new ArrayList<Rectangle>();
		selectedButton = new Rectangle(0, 0, ITEM_WIDTH, ITEM_HEIGHT, universe.getVertexBufferObjectManager());
		selectedButton.setColor(51, 153, 102, 0.4f);
		
		for(int i=0; i<MAX_SIZE; ++i){
			Rectangle button = new Rectangle(0, ITEM_HEIGHT * i, ITEM_WIDTH, ITEM_HEIGHT, universe.getVertexBufferObjectManager());
			button.setColor(RandomSingleton.getRandomInt(256), RandomSingleton.getRandomInt(256), RandomSingleton.getRandomInt(256));
			buttons.add(button);
		}
		
		// Attach Buttons
		for(Rectangle button : buttons){
			this.attachChild(button);
		}
		this.attachChild(selectedButton);
		
		Text turnLeftText = new Text(0, 0, assets.fontJokalMedium, "Turn Left", universe.getVertexBufferObjectManager());
		turnLeftText.setPosition(buttons.get(0));
		this.attachChild(turnLeftText);

		Text moveText = new Text(0, 0, assets.fontJokalMedium, "Move", universe.getVertexBufferObjectManager());
		moveText.setPosition(buttons.get(1));
		this.attachChild(moveText);
		
		Text deactivateMoveText = new Text(0, 0, assets.fontJokalMedium, "Deactivate Move", universe.getVertexBufferObjectManager());
		deactivateMoveText.setPosition(buttons.get(2));
		this.attachChild(deactivateMoveText);
		
		Text attackText = new Text(0, 0, assets.fontJokalMedium, "Attack", universe.getVertexBufferObjectManager());
		attackText.setPosition(buttons.get(3));
		this.attachChild(attackText);
		
		Text deactivateAttackText = new Text(0, 0, assets.fontJokalMedium, "Deactivate Attack", universe.getVertexBufferObjectManager());
		deactivateAttackText.setPosition(buttons.get(4));
		this.attachChild(deactivateAttackText);
		
		universe.attachChild(this);
		universe.registerTouchArea(this);
	}
	
	@Override
	public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
		

		int selectedButtonIndex = ((int) pTouchAreaLocalY / ITEM_HEIGHT);

		switch(pSceneTouchEvent.getAction()) {
		case TouchEvent.ACTION_DOWN:
		case TouchEvent.ACTION_MOVE:
			if(selectedButtonIndex >= 0 && selectedButtonIndex < buttons.size()){
				selectedButton.setVisible(true);
				selectedButton.setPosition(buttons.get(selectedButtonIndex));
			}
			else{
				selectedButton.setVisible(false);
			}
			break;
		case TouchEvent.ACTION_UP:
			if(activeMonster != null && selectedButton.isVisible()){
				if(selectedButton.getY() == buttons.get(0).getY()){
					tileInputMenu.deactivate();
					tileInputMenu.deactivateMovementTiles();
					activeMonster.turnLeft();
				}
				else if(selectedButton.getY() == buttons.get(1).getY()){
					tileInputMenu.activate();
					tileInputMenu.activateMovementTiles(activeMonster);
				}
				else if(selectedButton.getY() == buttons.get(2).getY()){
					tileInputMenu.deactivate();
					tileInputMenu.deactivateMovementTiles();
				}
				else if(selectedButton.getY() == buttons.get(3).getY()){
					tileInputMenu.activate();
					tileInputMenu.activeEnemyTiles(activeMonster);
				}
				else if(selectedButton.getY() == buttons.get(4).getY()){
					tileInputMenu.deactivate();
					tileInputMenu.deactivateEnemyTiles();
				}
			}
			break;
		}

		return true;
	}
	
	public int getSize(){
		return MAX_SIZE;
	}

	public void activate() {
		setPosition(DEFAULT_POS_X, DEFAULT_POS_Y);
		setVisible(true);
	}

	public void deactivate() {
		tileInputMenu.deactivate();
		setPosition(VOID_ZONE_POS_X, VOID_ZONE_POS_Y);
		setVisible(false);		
	}

	public void setActiveMonster(IMonster activeMonster) {
		this.activeMonster = activeMonster;
	}
	
	public class SharedMonsterMenuHandler{

		public void moveMonster(int newX, int newY) {
			int oldX = activeMonster.getGridPosX();
			int oldY = activeMonster.getGridPosY();
			
			int distance = GridUtil.getDistance(oldX, oldY, newX, newY);
			
			if(distance <= activeMonster.getMovementPoints()){
				activeMonster.setGridPos(newX, newY);
				tileInputMenu.deactivateMovementTiles();
				deactivate();
			}
		}
		
		public void attackMonster(int attackedMonsterCoordX, int attackedMonsterCoordY) {
			assert activeMonster != null;
			worldHandler.attackMonster(activeMonster, attackedMonsterCoordX, attackedMonsterCoordY);
			tileInputMenu.deactivateEnemyTiles();
			deactivate();
		}

		public boolean isTileOccupied(int coordX, int coordY) {
			return worldHandler.isTileOccupied(coordX, coordY);
		}
	}

}
