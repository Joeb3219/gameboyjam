package com.charredgames.game.gbjam.graphics;

import com.charredgames.game.gbjam.Controller;

public class Tile {

	private Sprite sprite;
	private boolean isSolid;

	public static Tile test = new Tile(0xFF222222, Sprite.testSprite, false);
	
	public static Tile nullTile = new Tile(0xFFFFFF, Sprite.nullSprite, true);
	
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
	
	public Sprite getSprite(){
		return sprite;
	}
	
}
