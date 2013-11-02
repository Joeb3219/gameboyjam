package com.charredgames.game.gbjam.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;
import com.charredgames.game.gbjam.mob.Mob;

public class Level {

	protected int width, height;
	protected int[] tiles;
	protected ArrayList<Mob> mobs = new ArrayList<Mob>();
	
	public static Level spawnLevel = new Level("/levels/spawnlevel");
	
	public Level(String path){
		loadMap(path + "map.png");
		loadMobs(path + "mobs.png");
	}
	
	private void loadMobs(String path){
		try{
			BufferedImage img = ImageIO.read(Level.class.getResource(path));
			width = img.getWidth();
			height = img.getHeight();
			tiles = new int[width * height * 16];
			img.getRGB(0,  0, width, height, tiles, 0, width);
		}catch(IOException e){e.printStackTrace();}
	}
	
	private void loadMap(String path){
		try{
			BufferedImage img = ImageIO.read(Level.class.getResource(path));
			width = img.getWidth();
			height = img.getHeight();
			tiles = new int[width * height * 16];
			img.getRGB(0,  0, width, height, tiles, 0, width);
		}catch(IOException e){e.printStackTrace();}
	}
	
	public void render(int xScroll, int yScroll, Screen screen){
		
	}
	
	public Tile getTile(int x, int y){
		if( x < 0 || y < 0 || x >= width || y >= height) return Tile.nullTile;
		int tileColour = tiles[x + y * width];
		if(Controller.tileColours.containsKey(tileColour)) return Controller.tileColours.get(tileColour);
		return Tile.nullTile;
	}
	
}
