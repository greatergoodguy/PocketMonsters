package org.burstingbrains.pocketmonsters.handler;

import org.burstingbrains.pocketmonsters.monster.Monster;

public interface BBSHandler {
	
	public void onGridTouchUp();
	public void onMonsterSelected(Monster m);
}
