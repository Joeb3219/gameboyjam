package com.charredgames.game.gbjam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.charredgames.game.gbjam.battle.BattleMove;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Controller {

	public static Map<Integer, Tile> tileColours = new HashMap<Integer, Tile>();
	public static ArrayList<Mob> mobs = new ArrayList<Mob>();
	public static ArrayList<BattleMove> moves = new ArrayList<BattleMove>();
	public static ArrayList<PauseState> pauseStates = new ArrayList<PauseState>();
	
	public static boolean soundOn = true;
	public static int money = 0;
	public static int tickCount = 0;

	public static void update(){
		tickCount++;
	}
	
	public static void reset(){
		money = 0;
		tickCount = 0;
	}
	
	public static void addMob(Mob mob){
		mobs.add(mob);
	}
	
	public static void updateMobs(Level level){
		for(Mob mob : mobs){
			if(level == mob.getLevel()) mob.update();
		}
	}
	
	public static void renderMobs(Level level, Screen screen){
		for(Mob mob : mobs){
			if(level == mob.getLevel()) mob.render(screen);
		}
	}
	
	public static void addTile(int identifier, Tile tile){
		tileColours.put(identifier, tile);
	}
	
	public static String getStringMoney(){
		return String.format("%07d", money);
	}
	
	public static void addMoney(int num){
		money += num;
	}
	
	public static BattleMove getNextMove(BattleMove currentMove){
		int position = moves.indexOf(currentMove);
		if(moves.size() - 1 >= position + 1) return moves.get(position + 1);
		return BattleMove.STAB;
	}
	
	public static BattleMove getPreviousMove(BattleMove currentMove){
		int position = moves.indexOf(currentMove);
		if(position - 1 >= 0) return moves.get(position - 1);
		return BattleMove.RUN;
	}
	
	public static void addPauseState(PauseState state){
		pauseStates.add(state);
	}
	
	public static PauseState getNextPauseState(PauseState current){
		int nextPost = pauseStates.indexOf(current) + 1;
		PauseState next = PauseState.NULL;
		if(pauseStates.size() > nextPost) next = pauseStates.get(nextPost);
		else next = pauseStates.get(0);
		if(next == PauseState.NULL)  return PauseState.RESUME;
		return next;
	}
	
	public static PauseState getLastPauseState(PauseState current){
		int lastPost = pauseStates.indexOf(current) - 1;
		PauseState next = PauseState.NULL;
		if(lastPost >= 0) next = pauseStates.get(lastPost);
		else next = pauseStates.get(pauseStates.size() - 2);
		if(next == PauseState.NULL)  return PauseState.RESUME;
		return next;
	}
	
}
