package com.charredgames.game.gbjam.entity;

public enum MobType {

	//MAIN MOBS
	SALESMAN("Salesman", MobMood.AGRESSIVE), 
	
	HIPPIE("Hippie", MobMood.PASSIVE),
	
	YOUNGSTER("Youngster", MobMood.PASSIVE_AGRESSIVE), ENGINEER("Engineer", MobMood.PASSIVE_AGRESSIVE), SCIENTIST("Scientist", MobMood.PASSIVE_AGRESSIVE),
	
	BIKER("Biker", MobMood.PASSIVE_AGRESSIVE), ROCKER("Rocker", MobMood.AGRESSIVE),
	
	EXECUTIVE("Executive", MobMood.AGRESSIVE),
	 
	JEDI("Jedi", MobMood.AGRESSIVE), NINJA("Ninja", MobMood.AGRESSIVE),
	
	//OTHERS
	PLAYER("Player", MobMood.NULL), NULL("MissingNo?!", MobMood.PASSIVE);
	
	private String typeName;
	private MobMood mood;
	
	private MobType(String typeName, MobMood mood){
		this.typeName = typeName;
		this.mood = mood;
	}
	
	public String getTypeName(){
		return typeName;
	}
	
	public MobMood getDefaultMood(){
		return mood;
	}
	
}
