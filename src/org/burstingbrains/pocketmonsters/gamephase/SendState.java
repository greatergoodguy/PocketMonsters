package org.burstingbrains.pocketmonsters.gamephase;

import android.util.Log;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;

public class SendState implements IPlayerState{

	public SendState(AmazonSimpleDBClient sdbClient) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(float pSecondsElapsed) {
		Log.d("SendState", "qwe");
		
	}

	@Override
	public boolean isStateFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
