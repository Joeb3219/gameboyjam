package com.charredgames.game.gbjam;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 7, 2013
 */
public class GameMessage {

	private String message;
	private int y;
	private int timer = 100;
	public boolean visible = true;
	public static ArrayList<GameMessage> messages = new ArrayList<GameMessage>();
	
	public GameMessage(String message){
		this.message = message;
		this.y = getY();
		messages.add(0, this);
	}
	
	public String getMessage(){
		return message;
	}
	
	public int getY(){
		return 16 * messages.indexOf(this);
	}
	
	public void update(){
			if(timer > 0) timer--;
			else remove();
	}
	
	private void remove(){
		messages.set(messages.indexOf(this),null);
	}
	
	public static void updateMessages(){
		for(GameMessage message : messages){
			if(message == null) continue;
			if(message.visible) message.update();
		}
	}
	
}
