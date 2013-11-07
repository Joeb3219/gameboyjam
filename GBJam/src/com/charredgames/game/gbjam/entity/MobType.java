package com.charredgames.game.gbjam.entity;

public enum MobType {

	PLAYER("Player"), NULL("MissingNo?!");
	
	private String name;
	
	private MobType(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
}
