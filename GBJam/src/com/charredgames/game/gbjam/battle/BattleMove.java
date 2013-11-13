package com.charredgames.game.gbjam.battle;

import com.charredgames.game.gbjam.Controller;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 8, 2013
 */
public enum BattleMove {

	STAB("Stab", 20, 25), SLASH("Slash", 160, 25), BLOCK("Block", 300, 25),
	
	INVENTORY("Inventory", 75, 55), RUN("Run", 235, 55);
	
	private String name;
	public int xOffset, yOffset;

	private BattleMove(String name, int xOffset, int yOffset){
		Controller.moves.add(this);
		this.xOffset = xOffset;
		this.yOffset = yOffset;
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
