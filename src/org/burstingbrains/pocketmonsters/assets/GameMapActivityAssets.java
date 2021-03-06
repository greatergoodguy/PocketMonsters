package org.burstingbrains.pocketmonsters.assets;

import java.io.IOException;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.debug.Debug;
import org.burstingbrains.andengineext.BBSGameActivity;

public class GameMapActivityAssets implements IAssets{
	private static final GameMapActivityAssets gameMapActivityAssets = new GameMapActivityAssets();
	


	private static final int FONT_SIZE_SMALL = 30;
	private static final int FONT_SIZE_MEDIUM = 50;
	private static final int FONT_SIZE_LARGE = 70;
	

	public Font fontJokalSmall;
	public Font fontJokalMedium;
	public Font fontJokalLarge;
	
	public Music haven_v2Music;
	
	public BuildableBitmapTextureAtlas skyConceptBGTexture;
	public ITextureRegion skyConceptBGTextureRegion;

	public BuildableBitmapTextureAtlas monsterCardIconsTexture;
	public ITextureRegion monsterCardHeartIconTextureRegion;
	public ITextureRegion monsterCardAttackIconTextureRegion;

	public BuildableBitmapTextureAtlas badlyDrawnMonsterTexture;
	public ITiledTextureRegion badlyDrawnMonsterUpTextureRegion;
	public ITiledTextureRegion badlyDrawnMonsterLeftTextureRegion;
	public ITiledTextureRegion badlyDrawnMonsterDownTextureRegion;
	public ITiledTextureRegion badlyDrawnMonsterRightTextureRegion;
	public ITextureRegion badlyDrawnMonsterProfilePicTextureRegion;

	public BuildableBitmapTextureAtlas orangeMonTexture;
	public ITextureRegion orangeMonUpTextureRegion;
	public ITextureRegion orangeMonLeftTextureRegion;
	public ITextureRegion orangeMonDownTextureRegion;
	public ITextureRegion orangeMonRightTextureRegion;
	public ITextureRegion orangeMonProfilePicTextureRegion;	

	public BuildableBitmapTextureAtlas menuButtonTexture;
	public ITextureRegion menuButtonOkTextureRegion;
	public ITextureRegion menuButtonResetTextureRegion;
	public ITextureRegion menuButtonQuitTextureRegion;
	

	public BuildableBitmapTextureAtlas touchTargetTexture;
	public ITextureRegion touchTargetTransparentTextureRegion;
	public ITextureRegion touchTargetLightBlueTextureRegion;
	
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
		// Font
		//======================================
		FontFactory.setAssetBasePath("font/");
		final ITexture jokalFontSmallTexture = new BitmapTextureAtlas(bbsGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		fontJokalSmall = FontFactory.createFromAsset(bbsGameActivity.getFontManager(), jokalFontSmallTexture, bbsGameActivity.getAssets(), "Jokal.ttf", FONT_SIZE_SMALL, true, android.graphics.Color.BLACK);
		
		final ITexture jokalFontMediumTexture = new BitmapTextureAtlas(bbsGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		fontJokalMedium = FontFactory.createFromAsset(bbsGameActivity.getFontManager(), jokalFontMediumTexture, bbsGameActivity.getAssets(), "Jokal.ttf", FONT_SIZE_MEDIUM, true, android.graphics.Color.BLACK);

		final ITexture jokalFontLargeTexture = new BitmapTextureAtlas(bbsGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		fontJokalLarge = FontFactory.createFromAsset(bbsGameActivity.getFontManager(), jokalFontLargeTexture, bbsGameActivity.getAssets(), "Jokal.ttf", FONT_SIZE_LARGE, true, android.graphics.Color.BLACK);

	
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
		// Backgrounds
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/backgrounds/");
		skyConceptBGTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		skyConceptBGTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(skyConceptBGTexture, bbsGameActivity, "skyconcept.jpg");
		try{ 
			skyConceptBGTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		//======================================
		// Backgrounds
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/icons/");
		monsterCardIconsTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 256, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		monsterCardHeartIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(monsterCardIconsTexture, bbsGameActivity, "heartIcon.png");
		monsterCardAttackIconTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(monsterCardIconsTexture, bbsGameActivity, "attackIcon.png");
		try{
			monsterCardIconsTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		//======================================
		// Badly Drawn Monster
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/monsters/badlydrawnmonster/");
		badlyDrawnMonsterTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		badlyDrawnMonsterUpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterUp.png", 1, 2);
		badlyDrawnMonsterLeftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterLeft.png", 2, 1);
		badlyDrawnMonsterDownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterDown.png", 1, 2);
		badlyDrawnMonsterRightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterRight.png", 1, 2);
		badlyDrawnMonsterProfilePicTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(badlyDrawnMonsterTexture, bbsGameActivity, "BadlyDrawnMonsterProfilePic.png");
		try{ 
			badlyDrawnMonsterTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

		//======================================
		// OrangeMon
		//======================================
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/monsters/orangemon/");
		orangeMonTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 2048, 2048, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		orangeMonUpTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonUp.png");
		orangeMonLeftTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonLeft.png");
		orangeMonDownTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonDown.png");
		orangeMonRightTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonRight.png");
		orangeMonProfilePicTextureRegion =  BitmapTextureAtlasTextureRegionFactory.createFromAsset(orangeMonTexture, bbsGameActivity, "OrangeMonProfilePic.png");
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
		

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("images/touchtargets/");
		touchTargetTexture = new BuildableBitmapTextureAtlas(bbsGameActivity.getTextureManager(), 1024, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		touchTargetTransparentTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(touchTargetTexture, bbsGameActivity, "Transparent.png");
		touchTargetLightBlueTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(touchTargetTexture, bbsGameActivity, "LightBlue.png");
		try{ 
			touchTargetTexture.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(1, 1, 1));
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
		
		isInitialized = true;
	}

	@Override
	public void load() {
		if(isInitialized){
			fontJokalSmall.load();
			fontJokalMedium.load();
			fontJokalLarge.load();
			
			skyConceptBGTexture.load();
			monsterCardIconsTexture.load();
			badlyDrawnMonsterTexture.load();
			orangeMonTexture.load();
			menuButtonTexture.load();
			
			touchTargetTexture.load();
		}
	}

	@Override
	public void unload() {
		if(isInitialized){
			fontJokalSmall.unload();
			fontJokalMedium.unload();
			fontJokalLarge.unload();
			
			skyConceptBGTexture.unload();
			monsterCardIconsTexture.unload();
			badlyDrawnMonsterTexture.unload();
			orangeMonTexture.unload();
			menuButtonTexture.unload();
			
			touchTargetTexture.unload();
		}
	}
}
