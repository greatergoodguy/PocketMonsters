package org.burstingbrains.pocketmonsters.universe;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.scene.ITouchArea;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.burstingbrains.andengineext.BBSGameActivity;

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
		gameScene.setTouchAreaBindingOnActionDownEnabled(true);
	}
	
	public final void registerUpdateHandler(IUpdateHandler pUpdateHandler) {
		engine.registerUpdateHandler(pUpdateHandler);
	}

	public final void unregisterUpdateHandler(IUpdateHandler pUpdateHandler) {
		engine.unregisterUpdateHandler(pUpdateHandler);
		
	}

	public final void attachChildScene(Scene scene) {
		gameScene.attachChild(scene);
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

	public void registerTouchArea(ITouchArea touchArea) {
		gameScene.registerTouchArea(touchArea);		
	}

	public void attachChild(IEntity entity) {
		gameScene.attachChild(entity);
	}
}
