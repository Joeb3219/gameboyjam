package com.charredgames.game.gbjam.graphics;


/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Sprite {

	public final int size;
	private int x, y;
	public int[] pixels;
	private SpriteSheet spriteSheet;
	
	public static Sprite MOB_FORWARD = new Sprite(16, 0, 1, SpriteSheet.CHARACTERS);
	public static Sprite MOB_LEFT = new Sprite(16, 1, 1, SpriteSheet.CHARACTERS);
	public static Sprite MOB_RIGHT = new Sprite(16, 2, 1, SpriteSheet.CHARACTERS);
	public static Sprite MOB_BACKWARD = new Sprite(16, 3, 1, SpriteSheet.CHARACTERS);
	
	public static Sprite PLAYER_FORWARD = new Sprite(16, 0, 3, SpriteSheet.PLAYER);
	public static Sprite PLAYER_LEFT = new Sprite(16, 0, 1, SpriteSheet.PLAYER);
	public static Sprite PLAYER_RIGHT = new Sprite(16, 0, 2, SpriteSheet.PLAYER);
	public static Sprite PLAYER_BACKWARD = new Sprite(16, 0, 0, SpriteSheet.PLAYER);
	
	public static Sprite MOB2_FORWARD = new Sprite(16, 1, 3, SpriteSheet.MOB);
	public static Sprite MOB2_LEFT = new Sprite(16, 1, 1, SpriteSheet.MOB);
	public static Sprite MOB2_RIGHT = new Sprite(16, 1, 2, SpriteSheet.MOB);
	public static Sprite MOB2_BACKWARD = new Sprite(16, 1, 0, SpriteSheet.MOB);
	
	public static Sprite CHEST = new Sprite(16, 0, 2, SpriteSheet.SCENERY);
	public static Sprite GRASS = new Sprite(16, 0, 1, SpriteSheet.SCENERY);
	public static Sprite TALL_GRASS = new Sprite(16, 1, 1, SpriteSheet.SCENERY);
	public static Sprite SAND = new Sprite(16, 1, 0, SpriteSheet.SCENERY);

	public static Sprite mob = new Sprite(16, 0xFFFF88BB);
	
	public static Sprite nullSprite = new Sprite(16, 0xFF222222);
	
	public Sprite(int size, int x, int y, SpriteSheet spriteSheet){
		this.size = size;
		this.x = x * size;
		this.y = y * size;
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
