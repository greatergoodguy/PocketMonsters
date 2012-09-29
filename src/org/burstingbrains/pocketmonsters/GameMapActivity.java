package org.burstingbrains.pocketmonsters;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.grid.Grid;
import org.burstingbrains.pocketmon.singleton.MusicPlayerSingleton;
import org.burstingbrains.pocketmonsters.assets.GameMapActivityAssets;
import org.burstingbrains.pocketmonsters.universe.Universe;

import android.util.Log;

public class GameMapActivity extends SimpleBaseGameActivity implements GameConstants{
	private final String TAG = this.getClass().getSimpleName();
	
	private static MusicPlayerSingleton musicPlayer = MusicPlayerSingleton.getSingleton();
	private static GameMapActivityAssets assets = GameMapActivityAssets.getSingleton();
	
	private Camera camera;
	private BitmapTextureAtlas cardDeckTexture;

	private Universe gameMapUniverse;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.camera);
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
		new Grid(gameMapUniverse);
		
		musicPlayer.play();
		
		return gameMapUniverse.getGameScene();
	}
	
	@Override
	public void onPauseGame() {
		super.onPauseGame();
		
		musicPlayer.pause();
	}

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
		
		musicPlayer.resume();
	}
	
	@Override
	protected void onDestroy(){

		super.onDestroy();
	}
}
