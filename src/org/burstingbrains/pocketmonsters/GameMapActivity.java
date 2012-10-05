package org.burstingbrains.pocketmonsters;

import java.util.ArrayList;
import java.util.List;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.burstingbrains.andengineext.BBSGameActivity;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.grid.Grid;
import org.burstingbrains.pocketmon.singleton.MusicPlayerSingleton;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.handler.IOnGridTouchUp;
import org.burstingbrains.pocketmonsters.monster.Monster;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.view.KeyEvent;

public class GameMapActivity extends BBSGameActivity implements IUpdateHandler, GameConstants{
	private final String TAG = this.getClass().getSimpleName();
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private Camera camera;

	private Universe gameMapUniverse;
	
	private Grid grid;
	private List<Monster> monsters;
	private Monster activeMonster;
	private int monsterSelector = 0;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, new FillResolutionPolicy(), this.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);
		engineOptions.getAudioOptions().setNeedsSound(true);
		engineOptions.getAudioOptions().setNeedsMusic(true);

		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		assets.init(this);
		assets.load();
		musicPlayer.init(assets.haven_v2Music);
	}

	@Override
	public Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		gameMapUniverse = new Universe(this, new Scene());
		grid = new Grid(gameMapUniverse, new MoveMonsterHandler());
		
		monsters = new ArrayList<Monster>();
		monsters.add(new Monster(gameMapUniverse));
		monsters.get(0).setGridPos(4, 4);
		monsters.add(new Monster(gameMapUniverse));
		monsters.get(1).setGridPos(2, 2);
		
		activeMonster = monsters.get(0);
		
//		Sprite sprite = new Sprite(800, 300, assets.badlyDrawnMonsterDown2TextureRegion, getVertexBufferObjectManager());
//		gameMapUniverse.getGameScene().attachChild(sprite);
		
		musicPlayer.play();
		
		return gameMapUniverse.getGameScene();
	}
	
	@Override
	public void onPauseGame() {
		super.onPauseGame();
		saveGameStateAndPauseMusic();
	}

	private void saveGameStateAndPauseMusic() {
		musicPlayer.pause();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		loadGameStateAndResumeMusic();
	}
	
	private void loadGameStateAndResumeMusic() {
		musicPlayer.resume();
	}

	@Override
	protected void onDestroy(){

		super.onDestroy();
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	private class MoveMonsterHandler implements IOnGridTouchUp{
		@Override
		public void onGridTouchUp() {
			if(grid.isValidPosition()){
				activeMonster.setGridPos(grid.getPositionX(), grid.getPositionY());
				monsterSelector += 1;
				monsterSelector %= monsters.size();
				activeMonster = monsters.get(monsterSelector);
			}
		}
		
	}

	@Override
	public boolean onKeyUp(final int pKeyCode, final KeyEvent pEvent) {
		
		if(pKeyCode == KeyEvent.KEYCODE_BACK) {
			saveGameStateAndPauseMusic();
			return super.onKeyDown(pKeyCode, pEvent);
		} 
		else if(pKeyCode == KeyEvent.KEYCODE_HOME) {
			saveGameStateAndPauseMusic();
			
			return super.onKeyDown(pKeyCode, pEvent);	
		}
		else {
			return super.onKeyDown(pKeyCode, pEvent);
		}
	}
}
