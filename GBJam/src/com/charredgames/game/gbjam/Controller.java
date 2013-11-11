package com.charredgames.game.gbjam;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.MobType;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Controller {

	public static Map<Integer, Tile> tileColours = new HashMap<Integer, Tile>();
	public static Map<Integer, Mob> mobIdentifiers = new HashMap<Integer, Mob>();
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static ArrayList<Mob> removeMobs = new ArrayList<Mob>();
	private static ArrayList<String> names = new ArrayList<String>();
	private static ArrayList<String> phrases = new ArrayList<String>();
	
	public static boolean soundOn = true;
	public static int money = 0;
	public static int tickCount = 0;
	private static int count, phraseCount = 0;

	public static void update(){
		tickCount++;
	}
	
	public static void reset(){
		money = 0;
		tickCount = 0;
		loadStrings();
	}
	
	private static void loadStrings(){
		Scanner s;
		try {
			s = new Scanner(new File(Controller.class.getResource(("/strings/names.txt")).toURI()));//.useDelimiter(System.getProperty("line.separator"));
			while (s.hasNext()){
				names.add(s.next());
			}
			s.close();
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (URISyntaxException e) {e.printStackTrace();}
		
		try {
			s = new Scanner(new File(Controller.class.getResource(("/strings/phrases.txt")).toURI()));//.useDelimiter(System.getProperty("line.separator"));
			while (s.hasNext()){
				phrases.add(s.next());
			}
			s.close();
		} catch (FileNotFoundException e) {e.printStackTrace();} catch (URISyntaxException e) {e.printStackTrace();}
	}
	
	/**
	 * @author joeb3219 <joe@charredgames.com>
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
			if(mob.doesExist()) mob.update();
			else removeMobs.add(mob);
		}
		removeOldMobs();
	}
	
	public static void renderMobs(Screen screen){
		for(Mob mob : mobs){
			if(mob.doesExist()) mob.render(screen);
			else removeMobs.add(mob);
		}
		removeOldMobs();
	}
	
	public static void addTile(int identifier, Tile tile){
		tileColours.put(identifier, tile);
	}
	
	public static String getStringMoney(){
		return String.format("%07d", money);
	}
	
	public static int getMoney(){
		return money;
	}
	
	public static void addMoney(int num){
		money += num;
	}
	
	public static String getNextName(){
		if(names.isEmpty()) reset(); 
		if(names.size() <= count + 1) count = 0;
		count++;
		return names.get(count);
	}
	
	public static String getNextPhrase(){
		if(phrases.size() <= phraseCount + 1) phraseCount = 0;
		phraseCount++;
		return phrases.get(phraseCount);
	}
	
}
