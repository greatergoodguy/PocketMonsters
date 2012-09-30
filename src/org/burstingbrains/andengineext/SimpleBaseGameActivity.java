package org.burstingbrains.andengineext;

import org.andengine.entity.scene.Scene;

public abstract class SimpleBaseGameActivity extends BaseGameActivity {

	protected abstract void onCreateResources();
	protected abstract Scene onCreateScene();

	@Override
	public final void onCreateResources(final OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
		this.onCreateResources();

		pOnCreateResourcesCallback.onCreateResourcesFinished();
	}

	@Override
	public final void onCreateScene(final OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
		final Scene scene = this.onCreateScene();

		pOnCreateSceneCallback.onCreateSceneFinished(scene);
	}

	@Override
	public final void onPopulateScene(final Scene pScene, final OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}
	

	@Override
	public synchronized void onResumeGame() {
		super.onResumeGame();
	}
}
