package com.charredgames.game.gbjam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImage {

	private BufferedImage img;
	
	public static GameImage HEART_LEFT = new GameImage("/textures/heart_left.png");
	public static GameImage HEART_RIGHT = new GameImage("/textures/heart_right.png");
	public static GameImage MONEY = new GameImage("/textures/money.png");
	
	public static GameImage ITEM_SWORD = new GameImage("/textures/item_sword.png");
	public static GameImage ITEM_APPLE = new GameImage("/textures/item_apple.png");
	
	public GameImage(String path){
		try{
			img = ImageIO.read(GameImage.class.getResource(path));
		}catch(IOException e){e.printStackTrace();}
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}
