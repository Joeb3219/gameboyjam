package com.charredgames.game.gbjam.graphics;

import java.util.Random;

import com.charredgames.game.gbjam.Controller;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Tile {

	private Sprite sprite;
	private boolean isSolid;
	private int dropChance = 0;
	private static Random rand = new Random();
	
	public static final Tile SAND = new Tile(0xFFFFF66E, Sprite.SAND, false);
	public static final Tile GRASS = new Tile(0xFF448844, Sprite.GRASS, false);
	public static final Tile TALL_GRASS = new Tile(0xFF559955, Sprite.TALL_GRASS, false, 15);
	
	public static final Tile HOSPITAL = new Tile(0xFF111111, Sprite.HOSPITAL, true);
	public static final Tile HOSPITAL_DOOR = new Tile(0xFF8E8E8E, Sprite.HOSPITAL_13, false);
	public static final Tile MART_DOOR = new Tile(0xFF9F9F9F, Sprite.MART_13, false);
	public static final Tile HOSPITAL_FLOOR = new Tile(0xFF575757, Sprite.HOSPITAL_FLOOR, false);
	
	public static final Tile HOSPITAL_1 = new Tile(0xFF999999, Sprite.HOSPITAL_1, true);
	public static final Tile HOSPITAL_2 = new Tile(0xFF999999, Sprite.HOSPITAL_2, true);
	public static final Tile HOSPITAL_3 = new Tile(0xFF999999, Sprite.HOSPITAL_3, true);
	public static final Tile HOSPITAL_4 = new Tile(0xFF999999, Sprite.HOSPITAL_4, true);
	public static final Tile HOSPITAL_5 = new Tile(0xFF999999, Sprite.HOSPITAL_5, true);
	public static final Tile HOSPITAL_6 = new Tile(0xFF999999, Sprite.HOSPITAL_6, true);
	public static final Tile HOSPITAL_7 = new Tile(0xFF999999, Sprite.HOSPITAL_7, true);
	public static final Tile HOSPITAL_8 = new Tile(0xFF999999, Sprite.HOSPITAL_8, true);
	public static final Tile HOSPITAL_9 = new Tile(0xFF999999, Sprite.HOSPITAL_9, true);
	public static final Tile HOSPITAL_10 = new Tile(0xFF999999, Sprite.HOSPITAL_10, true);
	public static final Tile HOSPITAL_11 = new Tile(0xFF999999, Sprite.HOSPITAL_11, true);
	public static final Tile HOSPITAL_12 = new Tile(0xFF999999, Sprite.HOSPITAL_12, true);
	public static final Tile HOSPITAL_14 = new Tile(0xFF999999, Sprite.HOSPITAL_14, true);
	public static final Tile HOSPITAL_15 = new Tile(0xFF999999, Sprite.HOSPITAL_15, true);
	public static final Tile MART_1 = new Tile(0xFF999999, Sprite.MART_1, true);
	public static final Tile MART_2 = new Tile(0xFF999999, Sprite.MART_2, true);
	public static final Tile MART_3 = new Tile(0xFF999999, Sprite.MART_3, true);
	public static final Tile MART_4 = new Tile(0xFF999999, Sprite.MART_4, true);
	public static final Tile MART_5 = new Tile(0xFF999999, Sprite.MART_5, true);
	public static final Tile MART_6 = new Tile(0xFF999999, Sprite.MART_6, true);
	public static final Tile MART_7 = new Tile(0xFF999999, Sprite.MART_7, true);
	public static final Tile MART_8 = new Tile(0xFF999999, Sprite.MART_8, true);
	public static final Tile MART_9 = new Tile(0xFF999999, Sprite.MART_9, true);
	public static final Tile MART_10 = new Tile(0xFF999999, Sprite.MART_10, true);
	public static final Tile MART_11 = new Tile(0xFF999999, Sprite.MART_11, true);
	public static final Tile MART_12 = new Tile(0xFF999999, Sprite.MART_12, true);
	public static final Tile MART_14 = new Tile(0xFF999999, Sprite.MART_14, true);
	public static final Tile MART_15 = new Tile(0xFF999999, Sprite.MART_15, true);

	public static final Tile nullTile = new Tile(0xFFFFFFFF, Sprite.nullSprite, true);
	
	public Tile(int identifier, Sprite sprite, boolean solid, int dropChance){
		this.sprite = sprite;
		this.isSolid = solid;
		this.dropChance = dropChance;
		Controller.addTile(identifier, this);
	}
	
	public Tile(int identifier, Sprite sprite, boolean solid){
		this.sprite = sprite;
		this.isSolid = solid;
		Controller.addTile(identifier, this);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x, y, this.sprite);
	}
	
	public boolean isSolid(){
		return isSolid;
	}

	public boolean dropped(){
		if(dropChance == 0) return false;
		if(rand.nextInt(100) < dropChance && rand.nextInt(100) < dropChance) return true;
		return false;
	}
	
}
