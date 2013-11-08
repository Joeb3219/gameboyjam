package com.charredgames.game.gbjam.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.entity.Chest;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Level {

	protected int width, height;
	protected int[] tiles;
	protected ArrayList<Mob> mobs = new ArrayList<Mob>();
	protected ArrayList<Chest> chests = new ArrayList<Chest>();
	
	public static Level spawnLevel = new Level("/levels/spawnlevel");
	
	public Level(String path){
		loadMobs(path + "/mobs.png");
		loadMap(path + "/map.png");
	}
	
	private void loadMobs(String path){
		loadMap(path);
		for(int y = 0; y < GBJam.getWindowHeight(); y++){
			for(int x = 0; x < GBJam.getWindowWidth(); x++){
				if( x < 0 || y < 0 || x >= width || y >= height) continue;
				int tileColour = tiles[x + y * width];
				if(Controller.mobIdentifiers.containsKey(tileColour)) {
					Mob.SALESMAN.spawn(x * 32, y * 32, this);
					Controller.mobIdentifiers.get(tileColour).spawn(x * 16, y * 16, this);
				}
			}
		}
		
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
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll / 16;
		int y0 = yScroll / 16;
		int x1 = (xScroll + screen.getWidth() + 16) / 16;
		int y1 = (yScroll + screen.getHeight() + 16) / 16;
		for(int y = y0; y < y1; y++){
			for(int x = x0; x < x1; x++){
				getTile(x,y).render(x * 16, y * 16, screen);
			}
		}
	}
	
	public Tile getTile(int x, int y){
		if( x < 0 || y < 0 || x >= width || y >= height) return Tile.nullTile;
		int tileColour = tiles[x + y * width];
		if(Controller.tileColours.containsKey(tileColour)) return Controller.tileColours.get(tileColour);
		return Tile.nullTile;
	}
	
	public void addChest(Chest chest){
		chests.add(chest);
	}
	
	public ArrayList<Chest> getChests(){
		return chests;
	}
	
	
}
