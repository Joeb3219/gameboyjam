package com.charredgames.game.gbjam;

public enum PauseState {

	RESUME(1, "Resume"), INVENTORY(2, "Inventory"), SAVE_GAME(3, "Save Game"), MENU(4, "Main Menu"), NULL(0, "");
	
	private int order;
	private String output;
	
	private PauseState(int ord, String out){
		Controller.addPauseState(this);
		this.order = ord;
		this.output = out;
	}
	
	public int getOrder(){
		return order;
	}
	
	public String getOutput(){
		return output;
	}
	
}
