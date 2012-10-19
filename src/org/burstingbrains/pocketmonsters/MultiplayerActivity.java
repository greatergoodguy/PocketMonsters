package org.burstingbrains.pocketmonsters;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.burstingbrains.andengineext.BBSGameActivity;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.singleton.MusicPlayerSingleton;
import org.burstingbrains.pocketmonsters.actor.Grid;
import org.burstingbrains.pocketmonsters.actor.Monster;
import org.burstingbrains.pocketmonsters.actor.SharedMonsterMenu;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;
import android.view.KeyEvent;

public class MultiplayerActivity extends BBSGameActivity implements IUpdateHandler, GameConstants{
	private final String TAG = this.getClass().getSimpleName();
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private Camera camera;

	private Universe gameMapUniverse;
	
	private Grid grid;

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
		grid = new Grid(gameMapUniverse);
		
//		Sprite sprite = new Sprite(800, 300, assets.badlyDrawnMonsterDown2TextureRegion, getVertexBufferObjectManager());
//		gameMapUniverse.getGameScene().attachChild(sprite);
		
		
		//---------------------------------------------------------------------
		// Create monsters here !!
		// GameLogic.addMonster(new Monster(), grid.getMapTileAt(x, y))
		//---------------------------------------------------------------------
		GameLogic.addMonster(new Monster(gameMapUniverse), grid.getMapTileAt(4, 4));
		
		SharedMonsterMenu menu = new SharedMonsterMenu(gameMapUniverse, 4);
		
//		menu.setZIndex(2);
//		Scene scene = gameMapUniverse.getGameScene();
//		Log.d("qwe", "" + scene.getChildCount());
//		for(int i=0; i<3; ++i){
//			IEntity qwe = scene.getChildByIndex(i);
//			Log.d("qwe", "qwe" + (i+1) + " , " + qwe.toString() + " " + qwe.getZIndex());
//		}
		
		musicPlayer.play();
		
		gameMapUniverse.registerUpdateHandler(this);
		
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
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	
	private class MoveMonsterHandler implements BBSHandler{
		@Override
		public void onGridTouchUp() {
//			if(grid.isValidPosition()){
//				if(activeMonster != null){
//					activeMonster.setGridPos(grid.getPositionX(), grid.getPositionY());
//				}
//			}
		}

		@Override
		public void onMonsterSelected(Monster m) {
//			activeMonster = m;
		}
	}

	@Override
	public boolean onKeyUp(final int pKeyCode, final KeyEvent pEvent) {
		
		if(pKeyCode == KeyEvent.KEYCODE_BACK) {
			saveGameStateAndPauseMusic();
			return super.onKeyUp(pKeyCode, pEvent);
		} 
		else if(pKeyCode == KeyEvent.KEYCODE_HOME) {
			saveGameStateAndPauseMusic();
			
			return super.onKeyUp(pKeyCode, pEvent);	
		}
		else {
			return super.onKeyUp(pKeyCode, pEvent);
		}
	}
}