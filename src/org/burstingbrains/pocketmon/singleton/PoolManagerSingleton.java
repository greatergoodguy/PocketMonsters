package org.burstingbrains.pocketmon.singleton;

import org.burstingbrains.pocketmonsters.util.Pool;
import org.burstingbrains.pocketmonsters.util.Pool.PoolObjectFactory;
import org.burstingbrains.pocketmonsters.waitingroom.WaitingRoomGameModel;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class PoolManagerSingleton {
	private static final PoolManagerSingleton singleton = new PoolManagerSingleton();
	
	public Pool<ReplaceableAttribute> replacableAttributePool;
	public Pool<WaitingRoomGameModel> gameModelPool;
	
	private PoolManagerSingleton(){
		
		replacableAttributePool = new Pool<ReplaceableAttribute>(new PoolObjectFactory<ReplaceableAttribute>(){

			@Override
			public ReplaceableAttribute createObject() {
				return new ReplaceableAttribute();
			}
		}, 200);
		
		gameModelPool = new Pool<WaitingRoomGameModel>(new PoolObjectFactory<WaitingRoomGameModel>(){

			@Override
			public WaitingRoomGameModel createObject() {
				return new WaitingRoomGameModel();
			}
		}, 200);		
			
	}
	
	public static PoolManagerSingleton getSingleton(){
		return singleton;
	}
	

}
