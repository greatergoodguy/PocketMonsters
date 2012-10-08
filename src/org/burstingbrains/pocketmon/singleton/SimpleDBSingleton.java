package org.burstingbrains.pocketmon.singleton;

public class SimpleDBSingleton {
	private static final SimpleDBSingleton simpleDBSingleton = new SimpleDBSingleton();
	
	private SimpleDBSingleton(){
		
	}
	
	private static SimpleDBSingleton getSingleton(){
		return simpleDBSingleton;
	}
	
}
