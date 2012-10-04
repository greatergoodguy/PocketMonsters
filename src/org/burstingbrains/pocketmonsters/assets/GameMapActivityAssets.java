package org.burstingbrains.pocketmonsters.assets;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.util.debug.Debug;
import org.burstingbrains.andengineext.BBSGameActivity;

public class GameMapActivityAssets implements IAssets{
	private static final GameMapActivityAssets gameMapActivityAssets = new GameMapActivityAssets();
	
	public Music haven_v2Music;

	public BuildableBitmapTextureAtlas badlyDrawnMonsterTexture;
	public ITextureRegion badlyDrawnMonsterUp1TextureRegion;
	public ITextureRegion badlyDrawnMonsterLeft1TextureRegion;
	public ITextureRegion badlyDrawnMonsterDown1TextureRegion;
	public ITextureRegion badlyDrawnMonsterDown2TextureRegion;
	public ITextureRegion badlyDrawnMonsterRight1TextureRegion;
	

	public BuildableBitmapTextureAtlas orangeMonTexture;
	public ITextureRegion orangeMonUpTextureRegion;
	public ITextureRegion orangeMonLeftTextureRegion;
	public ITextureRegion orangeMonDownTextureRegion;
	public ITextureRegion orangeMonRightTextureRegion;
	

	public BuildableBitmapTextureAtlas menuButtonTexture;
	public ITextureRegion menuButtonOkTextureRegion;
	public ITextureRegion menuButtonResetTextureRegion;
	public ITextureRegion menuButtonQuitTextureRegion;
	
	private boolean isInitialized;
	
	private GameMapActivityAssets(){
		isInitialized = false;
	}
	
	public static GameMapActivityAssets getSingleton(){
		return gameMapActivityAssets;
	}

	@Override
	public void init(BBSGameActivity bbsGameActivity) {
		//======================================
		// Music
		//======================================
		
		MusicFactory.setAssetBasePath("music/");
		try {
			//haven_v2Music = MusicFactory.createMusicFromAsset(baseGameActivity.getEngine().getMusicManager(), baseGameActivity, "Haven_v2.mp3");
			haven_v2Music = MusicFactory.createMusicFromAsset(bbsGameActivity.getEngine().getMusicManager(), bbsGameActivity, "dummySong.mp3");
			//haven_v2Music.setLooping(true);
		} catch (final IOException e) {
			Debug.e(e);
		}

		//======================================
		// Badly Drawn Monster
		//======================================
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/monsters/");
		badlyDrawnMonsterTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		badlyDrawnMonsterUp1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterUp1.png");
		badlyDrawnMonsterLeft1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterLeft1.png");
		badlyDrawnMonsterDown1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterDown1.png");
		badlyDrawnMonsterDown2TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterDown2.png");		
		badlyDrawnMonsterRight1TextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterRight1.png");		
		try{ 
			badlyDrawnMonsterTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		//======================================
		// OrangeMon
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/monsters/");
		orangeMonTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		orangeMonUpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonUp.png");
		orangeMonLeftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonLeft.png");
		orangeMonDownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonDown.png");
		orangeMonRightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonRight.png");
		try{ 
			orangeMonTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		//======================================
		// Buttons
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/buttons/");
		menuButtonTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		menuButtonOkTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonTexture, bbsGameActivity, "menu_ok.png");
		menuButtonResetTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonTexture, bbsGameActivity, "menu_reset.png");
		menuButtonQuitTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(menuButtonTexture, bbsGameActivity, "menu_quit.png");
		try{ 
			menuButtonTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		isInitialized = true;
		
	}

	@Override
	public void load() {
		if(isInitialized){
			badlyDrawnMonsterTexture.load();
			orangeMonTexture.load();
			menuButtonTexture.load();
		}
	}

	@Override
	public void unload() {
		if(isInitialized){
			badlyDrawnMonsterTexture.unload();
			orangeMonTexture.unload();
		}
	}
}
