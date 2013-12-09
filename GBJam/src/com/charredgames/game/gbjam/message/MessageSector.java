package com.charredgames.game.gbjam.message;

import java.util.ArrayList;

import com.charredgames.game.gbjam.Controller;

public class MessageSector {

	private Message message;
	ArrayList<MessageBlock> blocks = new ArrayList<MessageBlock>();
	private boolean displayed = false;
	
	public MessageSector(Message message){
		this.message = message;
		Controller.addMessageSector(this);
		String block = "", text = message.getText();
		for(int i = 0; i < text.length(); i++){
			if(i % 100 != 0) block += Character.toString(text.charAt(i));
			else{
				if(block.equals("") || block.equals(" ") || block == null) continue;
				MessageBlock mb = new MessageBlock(block);
				blocks.add(mb);
				block = "";
			}
		}
	}
	
	public MessageBlock getNextBlock(){
		for(MessageBlock block : blocks){
			if(!block.hasDisplayed()) return block;
		}
		displayed = true;
		return null;
	}
	
	public void toggleCurrentBlock(){
		MessageBlock currentBlock = getNextBlock();
		for(MessageBlock block : blocks){
			if(block == currentBlock) block.toggleDisplayed(true);
		}
	}
	
	public boolean hasDisplayed(){
		return displayed;
	}
	
}
