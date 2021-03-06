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
	
	public static final Sprite MOB_FORWARD = new Sprite(16, 0, 1, SpriteSheet.CHARACTERS);
	public static final Sprite MOB_LEFT = new Sprite(16, 1, 1, SpriteSheet.CHARACTERS);
	public static final Sprite MOB_RIGHT = new Sprite(16, 2, 1, SpriteSheet.CHARACTERS);
	public static final Sprite MOB_BACKWARD = new Sprite(16, 3, 1, SpriteSheet.CHARACTERS);
	
	public static final Sprite PLAYER_FORWARD = new Sprite(16, 0, 3, SpriteSheet.PLAYER);
	public static final Sprite PLAYER_LEFT = new Sprite(16, 0, 1, SpriteSheet.PLAYER);
	public static final Sprite PLAYER_RIGHT = new Sprite(16, 0, 2, SpriteSheet.PLAYER);
	public static final Sprite PLAYER_BACKWARD = new Sprite(16, 0, 0, SpriteSheet.PLAYER);
	
	public static final Sprite MOB2_FORWARD = new Sprite(16, 1, 3, SpriteSheet.MOB);
	public static final Sprite MOB2_LEFT = new Sprite(16, 1, 1, SpriteSheet.MOB);
	public static final Sprite MOB2_RIGHT = new Sprite(16, 1, 2, SpriteSheet.MOB);
	public static final Sprite MOB2_BACKWARD = new Sprite(16, 1, 0, SpriteSheet.MOB);
	
	public static final Sprite CHEST = new Sprite(16, 0, 2, SpriteSheet.SCENERY);
	public static final Sprite GRASS = new Sprite(16, 0, 1, SpriteSheet.SCENERY);
	public static final Sprite TALL_GRASS = new Sprite(16, 1, 1, SpriteSheet.SCENERY);
	public static final Sprite SAND = new Sprite(16, 1, 0, SpriteSheet.SCENERY);

	public static final Sprite mob = new Sprite(16, 0xFFFF88BB);
	public static final Sprite HOSPITAL = new Sprite(16, 0xFFFFFFFF);
	public static final Sprite HOSPITAL_DOOR = new Sprite(16, 0xFF232323);
	public static final Sprite HOSPITAL_FLOOR = new Sprite(16, 1, 2, SpriteSheet.SCENERY);
	
	public static final Sprite HOSPITAL_1 = new Sprite(16, 0, 0, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_2 = new Sprite(16, 1, 0, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_3 = new Sprite(16, 2, 0, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_4 = new Sprite(16, 3, 0, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_5 = new Sprite(16, 4, 0, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_6 = new Sprite(16, 0, 1, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_7 = new Sprite(16, 1, 1, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_8 = new Sprite(16, 2, 1, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_9 = new Sprite(16, 3, 1, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_10 = new Sprite(16, 4, 1, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_11 = new Sprite(16, 0, 2, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_12 = new Sprite(16, 1, 2, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_13 = new Sprite(16, 2, 2, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_14 = new Sprite(16, 3, 2, SpriteSheet.BUILDINGS);
	public static final Sprite HOSPITAL_15 = new Sprite(16, 4, 2, SpriteSheet.BUILDINGS);
	public static final Sprite MART_1 = new Sprite(16, 0, 4, SpriteSheet.BUILDINGS);
	public static final Sprite MART_2 = new Sprite(16, 1, 4, SpriteSheet.BUILDINGS);
	public static final Sprite MART_3 = new Sprite(16, 2, 4, SpriteSheet.BUILDINGS);
	public static final Sprite MART_4 = new Sprite(16, 3, 4, SpriteSheet.BUILDINGS);
	public static final Sprite MART_5 = new Sprite(16, 4, 4, SpriteSheet.BUILDINGS);
	public static final Sprite MART_6 = new Sprite(16, 0, 5, SpriteSheet.BUILDINGS);
	public static final Sprite MART_7 = new Sprite(16, 1, 5, SpriteSheet.BUILDINGS);
	public static final Sprite MART_8 = new Sprite(16, 2, 5, SpriteSheet.BUILDINGS);
	public static final Sprite MART_9 = new Sprite(16, 3, 5, SpriteSheet.BUILDINGS);
	public static final Sprite MART_10 = new Sprite(16, 4, 5, SpriteSheet.BUILDINGS);
	public static final Sprite MART_11 = new Sprite(16, 0, 6, SpriteSheet.BUILDINGS);
	public static final Sprite MART_12 = new Sprite(16, 1, 6, SpriteSheet.BUILDINGS);
	public static final Sprite MART_13 = new Sprite(16, 2, 6, SpriteSheet.BUILDINGS);
	public static final Sprite MART_14 = new Sprite(16, 3, 6, SpriteSheet.BUILDINGS);
	public static final Sprite MART_15 = new Sprite(16, 4, 6, SpriteSheet.BUILDINGS);

	public static final Sprite nullSprite = new Sprite(16, 0xFF222222);
	
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
