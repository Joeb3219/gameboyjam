package com.charredgames.game.gbjam;

import java.util.ArrayList;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.mob.Mob;

public class Controller {

	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static ArrayList<Mob> removeMobs = new ArrayList<Mob>();
	
	public static void addMob(Mob mob){
		mobs.add(mob);
	}
	
	public static void removeOldMobs(){
		for(Mob mob : removeMobs){
			if(mobs.contains(mob)) mobs.remove(mob);
		}
		removeMobs.clear();
	}
	
	public static void updateMobs(){
		for(Mob mob : mobs){
			if(mob.isAlive()) mob.update();
			else removeMobs.add(mob);
		}
		removeOldMobs();
	}
	
	public static void renderMobs(Screen screen){
		for(Mob mob : mobs){
			if(mob.isAlive()) mob.render(screen);
			else removeMobs.add(mob);
		}
		removeOldMobs();
	}
	
}
