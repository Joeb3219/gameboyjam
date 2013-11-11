package com.charredgames.game.gbjam.entity;

import java.util.HashMap;
import java.util.Map;

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
	//We can clean this later!
	private Map<String, MobType> types = new HashMap<String, MobType>();
	
	private MobType(String typeName, MobMood mood){
		this.typeName = typeName;
		this.mood = mood;
		types.put(typeName, this);
	}
	
	public String getTypeName(){
		return typeName;
	}
	
	public MobMood getDefaultMood(){
		return mood;
	}
	
}
