package com.charredgames.game.gbjam;

import java.applet.Applet;
import java.applet.AudioClip;


public class Sound {

	public AudioClip clip;
	
	public static Sound eating = new Sound("/sound/eating.wav");
	public static Sound breaking = new Sound("/sound/break.wav");
	public static Sound bgSong = new Sound("/sound/rough_overlay.wav");
	
	public Sound(String path){
			this.clip = Applet.newAudioClip(Sound.class.getResource(path));
	}

	public void playSound() {
		if(Controller.soundOn) clip.play();
	}
}
