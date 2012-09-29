package org.burstingbrains.pocketmonsters;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSLogger;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.burstingbrains.pocketmon.constants.GameConstants;
import org.burstingbrains.pocketmon.grid.Grid;
import org.burstingbrains.pocketmonsters.universe.Universe;

public class GameMapActivity extends SimpleBaseGameActivity implements GameConstants{
	private Camera camera;
	private BitmapTextureAtlas cardDeckTexture;

	private Universe universe;

	@Override
	public EngineOptions onCreateEngineOptions() {
		camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		final EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), this.camera);
		engineOptions.getTouchOptions().setNeedsMultiTouch(true);

		return engineOptions;
	}

	@Override
	public void onCreateResources() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");

		cardDeckTexture = new BitmapTextureAtlas(getTextureManager(), 1024, 512, TextureOptions.BILINEAR);
		BitmapTextureAtlasTextureRegionFactory.createFromAsset(cardDeckTexture, this, "carddeck_tiled.png", 0, 0);
		cardDeckTexture.load();
	}

	@Override
	public Scene onCreateScene() {
		mEngine.registerUpdateHandler(new FPSLogger());
				
		universe = new Universe(this, new Scene());
		new Grid(universe);

		return universe.getGameScene();
	}
}
