package com.charredgames.game.gbjam.battle;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 8, 2013
 */
public enum BattleMove {

	STAB("Stab"), SLASH("Slash"), BLOCK("Block"),
	
	INVENTORY("Inventory"), RUN("Run");
	
	private String name;

	private BattleMove(String name){
		
	}
	
	public String getName(){
		return name;
	}
}
