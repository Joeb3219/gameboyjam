package com.charredgames.game.gbjam.graphics;

import com.charredgames.game.gbjam.Controller;

public class Tile {

	protected Sprite sprite;
	protected boolean isSolid;
	protected int identifier;
	
	public static Tile GRASS = new Tile(0xFF448844, Sprite.GRASS, false);

	public static Tile test = new Tile(0xFF222222, Sprite.testSprite, false);
	
	public static Tile nullTile = new Tile(0xFFFFFFFF, Sprite.nullSprite, true);
	
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
	
}
