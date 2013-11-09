package com.charredgames.game.gbjam.entity;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 8, 2013
 */
public class EXP{

	private int level, exp;
	private static ArrayList<EXP> levels = new ArrayList<EXP>();
	
	private static EXP ONE = new EXP(1, 0);
	private static EXP TWO = new EXP(2, 100);
	private static EXP THREE = new EXP(3, 400);
	private static EXP FOUR = new EXP(4, 1000);
	private static EXP FIVE = new EXP(5, 2500);
	private static EXP SIX = new EXP(6, 4200);
	private static EXP SEVEN = new EXP(7, 7800);
	private static EXP EIGHT = new EXP(8, 10000);
	private static EXP NINE = new EXP(9, 13500);
	private static EXP TEN = new EXP(10, 17000);
	private static EXP ELEVEN = new EXP(11, 22000);
	private static EXP TWELVE = new EXP(12, 28000);
	private static EXP THIRTEEN = new EXP(13, 33500);
	private static EXP FOURTEEN = new EXP(14, 39000);
	private static EXP FIFTEEN = new EXP(15, 46000);
	
	public EXP(int level, int exp){
		this.level = level;
		this.exp = exp;
		levels.add(this);
	}
	
	public static int getLevel(int xp){
		int level = 1;
		for(EXP exp : levels){
			if(xp >= exp.getMinXP()) level = exp.getLevel();
		}
		return level;
	}
	
	private int getLevel(){
		return level;
	}
	
	public int getMinXP(){
		return exp;
	}
	
}
