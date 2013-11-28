package com.charredgames.game.gbjam.inventory;

import com.charredgames.game.gbjam.Controller;

public enum InventoryState {

	/*
	 * Used to determine what you've selected on an item.
	 */
	
	USE("Use"), USE_ALL("Use All"), EXAMINE("Examine"), DROP("Drop"), NULL("");
	
	private String name;
	
	private InventoryState(String name){
		this.name = name;
		Controller.addInventoryState(this);
	}
	
	public String getName(){
		return name;
	}
	
}
