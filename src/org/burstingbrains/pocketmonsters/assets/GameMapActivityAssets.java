package org.burstingbrains.pocketmonsters.assets;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.util.debug.Debug;
import org.burstingbrains.andengineext.BBSGameActivity;

public class GameMapActivityAssets implements IAssets{
	private static final GameMapActivityAssets gameMapActivityAssets = new GameMapActivityAssets();
	
	public Music haven_v2Music;
	
	private boolean isInitialized;
	
	private GameMapActivityAssets(){
		isInitialized = false;
	}
	
	public static GameMapActivityAssets getSingleton(){
		return gameMapActivityAssets;
	}

	@Override
	public void init(BBSGameActivity baseGameActivity) {
		MusicFactory.setAssetBasePath("music/");
		try {
			//haven_v2Music = MusicFactory.createMusicFromAsset(baseGameActivity.getEngine().getMusicManager(), baseGameActivity, "Haven_v2.mp3");
			haven_v2Music = MusicFactory.createMusicFromAsset(baseGameActivity.getEngine().getMusicManager(), baseGameActivity, "dummySong.mp3");
			//haven_v2Music.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}
		
		isInitialized = true;
		
	}

	@Override
	public void load() {
		if(isInitialized){
			// Load Assets
		}
	}

	@Override
	public void unload() {
		if(isInitialized){
			// Unload Assets
		}
	}

}
