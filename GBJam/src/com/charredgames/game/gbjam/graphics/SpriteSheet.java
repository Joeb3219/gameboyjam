package com.charredgames.game.gbjam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int size = 256;
	public int[] pixels;
	
	public static final SpriteSheet SCENERY = new SpriteSheet("/textures/scenery.png");
	public static final SpriteSheet CHARACTERS = new SpriteSheet("/textures/characters.png");
	public static final SpriteSheet PLAYER = new SpriteSheet("/textures/player.png");
	public static final SpriteSheet MOB = new SpriteSheet("/textures/Mob.png");
	public static final SpriteSheet BUILDINGS = new SpriteSheet("/textures/buildings.png");
	
	public SpriteSheet(String path){
		this.path = path;
		pixels = new int[size * size];
		load();
	}
	
	private void load(){
		try{
			BufferedImage img = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = img.getWidth();
			int h = img.getHeight();
			img.getRGB(0, 0, w, h, pixels, 0, h);
		}catch(IOException e){e.printStackTrace();}
	}
	
}
