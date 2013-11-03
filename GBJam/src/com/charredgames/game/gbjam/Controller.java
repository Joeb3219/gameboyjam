package com.charredgames.game.gbjam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;
import com.charredgames.game.gbjam.mob.Mob;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Controller {

	public static Map<Integer, Tile> tileColours = new HashMap<Integer, Tile>();
	public static Map<Integer, Mob> mobIdentifiers = new HashMap<Integer, Mob>();
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static ArrayList<Mob> removeMobs = new ArrayList<Mob>();
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * Adds mob's id colour to a hashmap w/ mob type.
	 * Allows reading mob coords from map.
	 */
	public static void addMobIdentifier(int identifier, Mob mob){
		mobIdentifiers.put(identifier, mob);
	}
	
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
	
	public static void addTile(int identifier, Tile tile){
		tileColours.put(identifier, tile);
	}
	
}
