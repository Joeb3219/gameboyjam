package com.charredgames.game.gbjam.graphics;

public class Sprite {

	public final int size;
	private int x, y;
	public int[] pixels;
	private SpriteSheet spriteSheet;
	
	public static Sprite testSprite = new Sprite(16, 0xFF448844);
	public static Sprite mob = new Sprite(1, 0xFFFF88BB);
	
	public Sprite(int size, int x, int y, SpriteSheet spriteSheet){
		this.size = size;
		this.x = x;
		this.y = y;
		this.spriteSheet = spriteSheet;
		pixels = new int[size * size];
		load();
	}
	
	public Sprite(int size, int colour){
		this.size = size;
		pixels = new int[size * size];
		for(int i = 0; i < pixels.length; i++) pixels[i] = colour;
	}
	
	private void load(){
		for(int y = 0; y < size; y ++){
			for(int x = 0; x < size; x ++){
				pixels[x + y * size] = spriteSheet.pixels[(x + this.x) + (y + this.y) * spriteSheet.size];
			}
		}
	}
	
}
