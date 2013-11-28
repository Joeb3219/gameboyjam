package com.charredgames.game.gbjam.message;

public class Message {

	private String text;
	private MessageType type;
	private MessageSector sector;
	
	public Message(String text, MessageType type){
		this.text = text;
		this.type = type;
		this.sector = new MessageSector(this);
	}
	
	public String getText(){
		return text;
	}
	
	public MessageType getType(){
		return type;
	}
	
	public MessageSector getSector(){
		return sector;
	}
	
}
