package org.burstingbrains.pocketmonsters.monstercard;

import java.util.ArrayList;
import java.util.List;

import org.andengine.entity.Entity;
import org.andengine.entity.IEntity;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.constants.GameConstants;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class MonsterCard extends Rectangle implements GameConstants{
	
	private final static int MONSTERCARD_WIDTH = 500;
	private final static int MONSTERCARD_HEIGHT = 250;
	
	private final static int MAX_ICONS = 6;
	
	GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private Monster monster;
	private Sprite profilePicture;
	
	private IEntity hpIconsEntity;
	private List<Sprite> hpIcons;
	
	private IEntity attackIconsEntity;
	private List<Sprite> attackIcons;
	
	public MonsterCard(Universe universe, Monster monster){
		super(0, 0, MONSTERCARD_WIDTH, MONSTERCARD_HEIGHT, universe.getVertexBufferObjectManager());

		this.monster = monster;
		this.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		
		// Init and Attach Profile Picture
		profilePicture = universe.createSprite(assets.orangeMonDownTextureRegion);
		profilePicture.setScale(0.4f);
		profilePicture.setPosition(-90, -90);
		attachChild(profilePicture);
		
		// Init and Attach Health Point Icons
		hpIconsEntity = new Entity();
		hpIcons = new ArrayList<Sprite>();
		for(int i=0; i<MAX_ICONS; ++i){
			Sprite hpIcon = universe.createSprite(assets.monsterCardHeartIconTextureRegion);
			hpIcon.setPosition(i*85, 0);
			hpIcon.setVisible(false);
			hpIcons.add(hpIcon);
			hpIconsEntity.attachChild(hpIcon);
		}
		for(int i=0; i<monster.getHealthPoints(); ++i){
			Sprite hpIcon = hpIcons.get(i); 
			hpIcon.setVisible(true);
		}
		hpIconsEntity.setPosition(110, 0);
		this.attachChild(hpIconsEntity);
		
		// Init and Attach Attack Point Icons
		attackIconsEntity = new Entity();
		attackIcons = new ArrayList<Sprite>();
		for(int i=0; i<MAX_ICONS; ++i){
			Sprite attackIcon = universe.createSprite(assets.monsterCardAttackIconTextureRegion);
			attackIcon.setPosition(i*85, 0);
			attackIcon.setVisible(false);
			attackIcons.add(attackIcon);
			attackIconsEntity.attachChild(attackIcon);
		}
		for(int i=0; i<monster.getAttackPower(); ++i){
			Sprite attackIcon = attackIcons.get(i); 
			attackIcon.setVisible(true);
		}
		attackIconsEntity.setPosition(110, 100);
		this.attachChild(attackIconsEntity);
		
		universe.attachChild(this);
		this.setPosition(2*CAMERA_WIDTH/3, 2*CAMERA_HEIGHT/3);
	}
	
	public void activate(){
		setVisible(true);
	}
	
	public void deactivate(){
		setVisible(false);
	}

}
