package com.charredgames.game.gbjam.entity;

import java.util.ArrayList;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 8, 2013
 */
public class EXP{

	private int level, exp;
	private static ArrayList<EXP> levels = new ArrayList<EXP>();
	
	public static final EXP ONE = new EXP(1, 0);
	public static final EXP TWO = new EXP(2, 100);
	public static final EXP THREE = new EXP(3, 400);
	public static final EXP FOUR = new EXP(4, 1000);
	public static final EXP FIVE = new EXP(5, 2500);
	public static final EXP SIX = new EXP(6, 4200);
	public static final EXP SEVEN = new EXP(7, 7800);
	public static final EXP EIGHT = new EXP(8, 10000);
	public static final EXP NINE = new EXP(9, 13500);
	public static final EXP TEN = new EXP(10, 17000);
	public static final EXP ELEVEN = new EXP(11, 22000);
	public static final EXP TWELVE = new EXP(12, 28000);
	public static EXP THIRTEEN = new EXP(13, 33500);
	public static EXP FOURTEEN = new EXP(14, 39000);
	public static EXP FIFTEEN = new EXP(15, 46000);
	
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
