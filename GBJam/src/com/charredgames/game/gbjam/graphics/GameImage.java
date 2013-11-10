package com.charredgames.game.gbjam.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GameImage {

	private BufferedImage img;
	
	public static final GameImage HEART_LEFT = new GameImage("/textures/heart_left.png");
	public static final GameImage HEART_RIGHT = new GameImage("/textures/heart_right.png");
	public static final GameImage MONEY = new GameImage("/textures/money.png");
	public static final GameImage INVENTORY_SELECT = new GameImage("/textures/inventory_select.png");
	
	public static final GameImage ITEM_SWORD = new GameImage("/textures/item_sword.png");
	public static final GameImage ITEM_APPLE = new GameImage("/textures/item_apple.png");
	public static final GameImage ITEM_STRENGTH_POTION = new GameImage("/textures/potion_strength.png");
	public static final GameImage ITEM_DEXTERITY_POTION = new GameImage("/textures/potion_dexterity.png");
	public static final GameImage ITEM_DEFENSE_POTION = new GameImage("/textures/potion_defense.png");

	public GameImage(String path){
		try{
			img = ImageIO.read(GameImage.class.getResource(path));
		}catch(IOException e){e.printStackTrace();}
	}
	
	public BufferedImage getImage(){
		return img;
	}
	
}
