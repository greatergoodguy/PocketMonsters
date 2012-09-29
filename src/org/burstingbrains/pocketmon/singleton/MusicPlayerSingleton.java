package org.burstingbrains.pocketmon.singleton;

import org.andengine.audio.music.Music;

public class MusicPlayerSingleton {

	private static final MusicPlayerSingleton musicPlayerSingleton = new MusicPlayerSingleton();
	
	private Music music;
	
	private MusicPlayerSingleton(){
		
	}
	
	public static MusicPlayerSingleton getSingleton(){
		return musicPlayerSingleton;
	}
	
	public void init(Music music){
		if(music != null){
			cleanUp();
		}
		
		this.music = music;
	}

	public void play(){
		if(music != null){
			music.play();
		}
	}

	public void pause(){
		if(music != null){
			music.pause();
		}
	}

	public void resume(){
		if(music != null){
			music.resume();
		}
	}

	public void stop(){
		if(music != null){
			music.stop();
		}
	}

	public void release(){
		if(music != null){
			music.release();
		}
	}
	
	private void cleanUp() {
		stop();
		release();
	}
}
