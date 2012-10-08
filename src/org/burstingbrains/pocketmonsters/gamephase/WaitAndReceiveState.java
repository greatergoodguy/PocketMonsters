package org.burstingbrains.pocketmonsters.gamephase;

import java.util.List;

import android.util.Log;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;

public class WaitAndReceiveState implements IPlayerState{

	AmazonSimpleDBClient sdbClient;
	
	public WaitAndReceiveState(AmazonSimpleDBClient sdbClient){
		this.sdbClient = sdbClient;
		
//		GetAttributesRequest gar = new GetAttributesRequest("ActiveGame", "1" );
//		GetAttributesResult response = sdbClient.getAttributes(gar);
//		
//		List<Attribute> attributes = response.getAttributes();
//		Log.d("WaitAndReceiveState", "attributes.get(0).getName(): " + attributes.get(0).getName());
//		Log.d("WaitAndReceiveState", "attributes.get(0).getValue(): " + attributes.get(0).getValue());
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		Log.d("WaitAndReceiveState", "qwe");
		
	}

	@Override
	public boolean isStateFinished() {
		// TODO Auto-generated method stub
		return false;
	}

}
