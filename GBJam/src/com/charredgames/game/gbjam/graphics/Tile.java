package com.charredgames.game.gbjam.graphics;

import java.util.Random;

import com.charredgames.game.gbjam.Controller;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Tile {

	protected Sprite sprite;
	protected boolean isSolid;
	protected int identifier;
	protected int dropChance = 0;
	protected static Random rand = new Random();
	
	public static Tile SAND = new Tile(0xFFFFF66E, Sprite.SAND, false);
	public static Tile GRASS = new Tile(0xFF448844, Sprite.GRASS, true);
	public static Tile TALL_GRASS = new Tile(0xFF559955, Sprite.TALL_GRASS, false, 1);
	public static Tile CHEST = new Tile(0xFF444444, Sprite.CHEST, true);
	
	public static Tile nullTile = new Tile(0xFFFFFFFF, Sprite.nullSprite, true);
	
	public Tile(int identifier, Sprite sprite, boolean solid, int dropChance){
		this.sprite = sprite;
		this.isSolid = solid;
		this.identifier = identifier;
		this.dropChance = dropChance;
		Controller.addTile(identifier, this);
	}
	
	public Tile(int identifier, Sprite sprite, boolean solid){
		this.sprite = sprite;
		this.isSolid = solid;
		this.identifier = identifier;
		Controller.addTile(identifier, this);
	}
	
	public void render(int x, int y, Screen screen){
		screen.renderTile(x, y, this.sprite);
	}
	
	public boolean isSolid(){
		return isSolid;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public int getIdentifier(){
		return identifier;
	}
	
	public boolean dropped(){
		if(dropChance == 0) return false;
		if(rand.nextInt(100) < dropChance && rand.nextInt(100) < dropChance) return true;
		return false;
	}
	
}
