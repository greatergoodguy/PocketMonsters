package org.burstingbrains.pocketmonsters.gamephase;

import android.util.Log;

import com.amazonaws.services.simpledb.AmazonSimpleDBClient;
import com.amazonaws.services.simpledb.model.Attribute;
import com.amazonaws.services.simpledb.model.GetAttributesRequest;
import com.amazonaws.services.simpledb.model.GetAttributesResult;

public class InitConnectionState implements IPlayerState{

	private final static String ACTIVE_GAME_DOMAIN = "ActiveGame";
	private static final int POLLING_INTERVAL_IN_SECONDS = 3;
	
	private AmazonSimpleDBClient sdbClient;
	
	private GetAttributesRequest gar;
	private GetAttributesResult response;
	
	Attribute p1ReadyAttribute;
	
	private float pollingTime;
	private boolean isStateFinished;
	
	public InitConnectionState(AmazonSimpleDBClient sdbClient){
		this.sdbClient = sdbClient;
		
		gar = new GetAttributesRequest("ActiveGame", "1" ).withAttributeNames("P1Ready");
		response = sdbClient.getAttributes(gar);
		p1ReadyAttribute = response.getAttributes().get(0);
		
		Log.d("InitConnectionState", "attributes.get(0).getName(): " + p1ReadyAttribute.getName());
		Log.d("InitConnectionState", "attributes.get(0).getValue(): " + p1ReadyAttribute.getValue());
		
		pollingTime = 0;
		isStateFinished = false;
	}
	
	@Override
	public void onUpdate(float pSecondsElapsed) {
		Log.d("InitConnectionState", "pollingTime: " + pollingTime);
		
		pollingTime += pSecondsElapsed;
		
		if(pollingTime > POLLING_INTERVAL_IN_SECONDS){
			// Do a server request
			response = sdbClient.getAttributes(gar);
			p1ReadyAttribute = response.getAttributes().get(0);
			
			if(p1ReadyAttribute.getValue().equals("true")){
				isStateFinished = true;
			}
			
			
			pollingTime = 0;
		}
		
	}

	@Override
	public boolean isStateFinished() {
		return isStateFinished;
	}

}
