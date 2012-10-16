package org.burstingbrains.pocketmon.singleton;

import org.burstingbrains.pocketmonsters.util.Pool;
import org.burstingbrains.pocketmonsters.util.Pool.PoolObjectFactory;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;

public class PoolManagerSingleton {
	private static final PoolManagerSingleton singleton = new PoolManagerSingleton();
	
	public Pool<ReplaceableAttribute> replacableAttributePool;
	
	private PoolManagerSingleton(){
		replacableAttributePool = new Pool<ReplaceableAttribute>(new PoolObjectFactory<ReplaceableAttribute>(){

			@Override
			public ReplaceableAttribute createObject() {
				return new ReplaceableAttribute();
			}
		}, 200);
			
	}
	
	public static PoolManagerSingleton getSingleton(){
		return singleton;
	}
	

}
