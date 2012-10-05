package org.burstingbrains.pocketmon.singleton;

import java.util.Random;

public class RandomSingleton {
	private static final RandomSingleton randomSingleton = new RandomSingleton();
	
	Random random;
	
	private RandomSingleton(){
		random = new Random();
		
	}
	
	private static RandomSingleton getSingleton(){
		return randomSingleton;
	}
	
	
	
	public static int getRandomInt(int sizeOfRange){
		
		return getSingleton().random.nextInt(sizeOfRange);
	}
}
