package org.burstingbrains.pocketmonsters.gamephase;

public interface IPlayerState {

	public void onUpdate(float pSecondsElapsed);

	public boolean isStateFinished();
}
