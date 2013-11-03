package com.charredgames.game.gbjam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int size = 256;
	public int[] pixels;
	
	public static SpriteSheet SCENERY = new SpriteSheet("/textures/scenery.png");
	public static SpriteSheet CHARACTERS = new SpriteSheet("/textures/characters.png");
	
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
