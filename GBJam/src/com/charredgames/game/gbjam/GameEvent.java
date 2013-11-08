package com.charredgames.game.gbjam;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 7, 2013
 */
public enum GameEvent {

	EATING(1, Sound.eating), WEAPON(1, null), TALKING(1, null), OPENED_CHEST(1, Sound.breaking), NULL(0, null);
	
	private int delay;
	private static int counter = 0;
	private Sound sound;
	
	private GameEvent(int delay, Sound sound){
		this.delay = delay * GBJam._DESIREDTPS;
		this.sound = sound;
	}
	
	public int getDelay(){
		return delay;
	}
	
	public static void updateCounter(){
		if(counter > 0) counter --;
		else GBJam.currentEvent = NULL;
	}
	
	public static void setEvent(GameEvent event){
		counter = event.getDelay();
		GBJam.currentEvent = event;
		if(event.sound != null) event.sound.playSound();
	}
}
