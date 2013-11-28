package com.charredgames.game.gbjam.message;

public class MessageBlock {

	public String text;
	public boolean displayed = false;
	
	public MessageBlock(String text){
		this.text = text;
	}
	
	public String getText(){
		return text;
	}
	
	public boolean hasDisplayed(){
		return displayed;
	}
	
	public void toggleDisplayed(boolean val){
		this.displayed = val;
	}
	
}
