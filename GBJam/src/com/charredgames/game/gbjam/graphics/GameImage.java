package com.charredgames.game.gbjam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImage {

	private String path;
	private BufferedImage img;
	
	public static GameImage HEART_LEFT = new GameImage("/textures/heart_left.png");
	public static GameImage HEART_RIGHT = new GameImage("/textures/heart_right.png");
	
	public GameImage(String path){
		this.path = path;
		try{
			img = ImageIO.read(GameImage.class.getResource(path));
		}catch(IOException e){e.printStackTrace();}
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}
