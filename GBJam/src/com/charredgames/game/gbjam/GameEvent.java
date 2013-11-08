package com.charredgames.game.gbjam;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 7, 2013
 */
public enum GameEvent {

	EATING(1), WEAPON(1), TALKING(1), NULL(0);
	
	private int delay;
	public static int counter = 0;
	
	private GameEvent(int delay){
		this.delay = delay;
	}
	
	public int getDelay(){
		return delay;
	}
	
	public static void updateCounter(){
		if(counter > 0) counter --;
		else GBJam.currentEvent = NULL;
	}
	
	public static void setEvent(GameEvent event){
		counter = event.getDelay() * 60;
		GBJam.currentEvent = event;
	}
}
