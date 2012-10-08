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
import org.burstingbrains.pocketmon.singleton.SettingsSingleton;
import org.burstingbrains.pocketmon.singleton.SettingsSingleton.Player;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.gamephase.IPlayerState;
import org.burstingbrains.pocketmonsters.gamephase.InitConnectionState;
import org.burstingbrains.pocketmonsters.gamephase.SendState;
import org.burstingbrains.pocketmonsters.gamephase.WaitAndReceiveState;
import org.burstingbrains.pocketmonsters.handler.BBSHandler;
import org.burstingbrains.pocketmonsters.monster.Monster;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.os.AsyncTask;
import android.view.KeyEvent;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.PutAttributesRequest;
import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class MultiplayerActivity extends BBSGameActivity implements IUpdateHandler, GameConstants{
	private final String TAG = this.getClass().getSimpleName();
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private AmazonSimpleDBClient sdbClient;
	
	private Camera camera;

	private Universe gameMapUniverse;
	
	private Grid grid;
	private List<Monster> monsters;
	private Monster activeMonster;
	private int monsterSelector = 0;

	private List<IPlayerState> playerStates;
	IPlayerState activePlayerState;
	
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
		
        AWSCredentials credentials = new BasicAWSCredentials(PropertyLoader.getInstance().getAccessKey(), PropertyLoader.getInstance().getSecretKey()  );
        sdbClient = new AmazonSimpleDBClient(credentials);
        new PutAttributesInUserAccountRequestTask().execute("MultiplayerActivity", "onCreateResources()");

        playerStates = new ArrayList<IPlayerState>();
        playerStates.add(new InitConnectionState(sdbClient));
        playerStates.add(new WaitAndReceiveState(sdbClient));
        playerStates.add(new SendState(sdbClient));
        activePlayerState = playerStates.get(0);
	}

	@Override
	public Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
		gameMapUniverse = new Universe(this, new Scene());
		grid = new Grid(gameMapUniverse, new MoveMonsterHandler());
		
		monsters = new ArrayList<Monster>();
		monsters.add(new Monster(gameMapUniverse, new MoveMonsterHandler()));
		monsters.get(0).setGridPos(4, 4);
		monsters.add(new Monster(gameMapUniverse, new MoveMonsterHandler()));
		monsters.get(1).setGridPos(2, 2);
		
		activeMonster = monsters.get(0);
		
//		Sprite sprite = new Sprite(800, 300, assets.badlyDrawnMonsterDown2TextureRegion, getVertexBufferObjectManager());
//		gameMapUniverse.getGameScene().attachChild(sprite);
		
		gameMapUniverse.registerUpdateHandler(this);
		
		musicPlayer.play();
		
		return gameMapUniverse.getGameScene();
	}
	


	@Override
	public void onUpdate(float pSecondsElapsed) {
		// Log.d("MultiplayerActivity", "onUpdate()");
		
		if(activePlayerState.isStateFinished())
			togglePlayerState();
		else
			activePlayerState.onUpdate(pSecondsElapsed);
		
		
		
	}

	private void togglePlayerState() {
		if(activePlayerState == playerStates.get(0)){
			if(SettingsSingleton.player == Player.PlayerOne)
				activePlayerState = playerStates.get(1);
			else
				activePlayerState = playerStates.get(2);	
		}
		else if(activePlayerState == playerStates.get(1)){
			activePlayerState = playerStates.get(2);
		}
		else if(activePlayerState == playerStates.get(2)){
			activePlayerState = playerStates.get(1);
		}
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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
	
	private class MoveMonsterHandler implements BBSHandler{
		@Override
		public void onGridTouchUp() {
			if(grid.isValidPosition()){
				if(activeMonster != null)
					activeMonster.setGridPos(grid.getPositionX(), grid.getPositionY());
				/*
				monsterSelector += 1;
				monsterSelector %= monsters.size();
				activeMonster = monsters.get(monsterSelector);
				*/
			}
		}

		@Override
		public void onMonsterSelected(Monster m) {
			activeMonster = m;
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
	

	class PutAttributesInUserAccountRequestTask extends AsyncTask<String, Void, Void> {
	    @Override
	    protected Void doInBackground(String... keyValuePair) {
	        ReplaceableAttribute scoreAttribute = new ReplaceableAttribute( "Password", keyValuePair[1], Boolean.TRUE );
	        		
	        List<ReplaceableAttribute> attrs = new ArrayList<ReplaceableAttribute>(1);
	        attrs.add( scoreAttribute );
	        		
	        PutAttributesRequest par = new PutAttributesRequest("UserAccount", keyValuePair[0], attrs);		
	        sdbClient.putAttributes( par );
	    	
	    	return null;
	    }
	 }
}
