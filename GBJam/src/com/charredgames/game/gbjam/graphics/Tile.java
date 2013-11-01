package com.charredgames.game.gbjam.graphics;

public class Tile {

	private Sprite sprite;
	private boolean isSolid;

	public static Tile test = new Tile(Sprite.testSprite, false);
	
	public Tile(Sprite sprite, boolean solid){
		this.sprite = sprite;
		this.isSolid = solid;
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
