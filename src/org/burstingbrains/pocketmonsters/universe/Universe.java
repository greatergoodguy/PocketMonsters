package org.burstingbrains.pocketmonsters.universe;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.burstingbrains.andengineext.BBSGameActivity;
import org.burstingbrains.pocketmonsters.sprite.ButtonSprite;
import org.burstingbrains.sharedlibs.handler.IButtonHandler;

public class Universe{
	
	protected final BBSGameActivity gameActivity;	
	protected final Scene gameScene;
	protected final VertexBufferObjectManager vertexBufferObjectManager;
	protected final Engine engine;
	
	public Universe(BBSGameActivity gameActivity, Scene gameScene){		
		this.gameActivity = gameActivity;
		this.gameScene = gameScene;
		this.vertexBufferObjectManager = gameActivity.getVertexBufferObjectManager();
		this.engine = gameActivity.getEngine();

		gameScene.setOnAreaTouchTraversalFrontToBack();
		//gameScene.setOnAreaTouchTraversalBackToFront();
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	public BBSGameActivity getGameActivity(){
		return gameActivity;
	}
	
	public Scene getGameScene(){
		return gameScene;
	}
	
	public VertexBufferObjectManager getVertexBufferObjectManager(){
		return vertexBufferObjectManager;
	}
	
	public Engine getEngine(){
		return engine;
	}
	
	public Sprite createSprite(final ITextureRegion textureRegion){
		return new Sprite(0, 0, textureRegion, vertexBufferObjectManager);
	}
	
	public Sprite createButtonSprite(final ITextureRegion textureRegion){
		return new ButtonSprite(0, 0, textureRegion, vertexBufferObjectManager);
	}

	public Sprite createButtonSprite(ITextureRegion textureRegion, IButtonHandler buttonHandler) {
		return new ButtonSprite(0, 0, textureRegion, vertexBufferObjectManager, buttonHandler);
	}
	
	public final void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
		engine.registerUpdateHandler(pUpdateHandler);
	}

	public final void unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
		engine.unregisterUpdateHandler(pUpdateHandler);
		
	}

	public void registerTouchArea(ITouchArea touchArea) {
		gameScene.registerTouchArea(touchArea);		
	}

	public void attachChild(IEntity entity) {
		gameScene.attachChild(entity);
	}

}
