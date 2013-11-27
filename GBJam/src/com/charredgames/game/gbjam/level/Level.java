package com.charredgames.game.gbjam.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.entity.Biker;
import com.charredgames.game.gbjam.entity.Chest;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.MobType;
import com.charredgames.game.gbjam.entity.Salesman;
import com.charredgames.game.gbjam.entity.Youngster;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Tile;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.Item;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Level {

	protected int width, height;
	protected int[] tiles;
	protected ArrayList<Mob> mobs = new ArrayList<Mob>();
	protected ArrayList<Chest> chests = new ArrayList<Chest>();
	protected int hospitalX, hospitalY = -1000;
	protected int martX, martY = -1000;
	
	public static Level spawnLevel = new Level("/levels/spawnlevel");
	
	public Level(String path){
		loadMap(path + "/map.png");
		loadLevelCGF(path + "/level.cgf");
	}
	
	protected void loadLevelCGF(String path){
		SAXBuilder builder = new SAXBuilder();
		File xmlFile;
		try {
			xmlFile = new File((Level.class.getResource(path)).toURI());
		try {
			Document document = (Document) builder.build(xmlFile);
			Element rootNode = document.getRootElement();
			
			//Handles loading world chests
			List<Element> list = rootNode.getChildren("chest");
			for (int i = 0; i < list.size(); i++) {				
				Element node = (Element) list.get(i);
				Inventory chestInventory = new Inventory();
				List<Element> inv = node.getChildren("item");
				for(int j = 0; j < inv.size(); j++){
					Element invNode = (Element) inv.get(j);
					chestInventory.addItem(Item.getItem(Integer.parseInt(invNode.getAttributeValue("id"))), Integer.parseInt(invNode.getAttributeValue("quantity")));
				}
				Chest chest = new Chest(this, Integer.parseInt(node.getChild("position").getAttributeValue("x")) * 16, Integer.parseInt(node.getChild("position").getAttributeValue("y")) * 16, chestInventory);
				addChest(chest);
			}
			
			//Handles loading level mobs
			List<Element> mobs = rootNode.getChildren("mob");
			for(int i = 0; i < mobs.size(); i++){
				Element mob = (Element) mobs.get(i);
				
				
				String mobType = mob.getChild("data").getAttributeValue("mobType");
				int x = Integer.parseInt(mob.getChild("position").getAttributeValue("x")), y = Integer.parseInt(mob.getChild("position").getAttributeValue("y"));
				int health = Integer.parseInt(mob.getChild("data").getAttributeValue("health"));
				
				Mob newMob;
				
				if(mobType.equalsIgnoreCase(MobType.SALESMAN.getTypeName())) newMob = new Salesman(MobType.SALESMAN, x * 16, y * 16, health, this);
				else if(mobType.equalsIgnoreCase(MobType.YOUNGSTER.getTypeName())) newMob = new Youngster(MobType.YOUNGSTER, x * 16, y * 16, health, this);
				else if(mobType.equalsIgnoreCase(MobType.BIKER.getTypeName())) newMob = new Biker(MobType.BIKER, x * 16, y * 16, health, this);
				else if(mobType.equalsIgnoreCase(MobType.DOCTOR.getTypeName())) newMob = new Biker(MobType.DOCTOR, x * 16, y * 16, health, this);
				else newMob = new Salesman(MobType.SALESMAN, 0, 0, health, this);

						
				newMob.setMobStrings(mob.getChild("info").getAttributeValue("name"), mob.getChild("info").getAttributeValue("phrase"), mob.getChild("info").getAttributeValue("losingPhrase"));
				newMob.setMoney(Integer.parseInt(mob.getChild("data").getAttributeValue("money")));
				newMob.setMovingDetails(Integer.parseInt(mob.getChild("position").getAttributeValue("direction")), Boolean.parseBoolean(mob.getChild("movement").getAttributeValue("turns")), Integer.parseInt(mob.getChild("movement").getAttributeValue("xMovement")), Integer.parseInt(mob.getChild("movement").getAttributeValue("yMovement")));
			}
			
			//Handles loading level buildings
			List<Element> buildings = rootNode.getChildren("building");
			for(int i = 0; i < buildings.size(); i++){
				Element building = (Element) buildings.get(i);
				
				if(building.getAttributeValue("id").equalsIgnoreCase("hospital")){
					this.hospitalX = Integer.parseInt(building.getChild("position").getAttributeValue("x")) * 16;
					this.hospitalY = Integer.parseInt(building.getChild("position").getAttributeValue("y")) * 16;
				}
				else if(building.getAttributeValue("id").equalsIgnoreCase("mart")){
					this.martX = Integer.parseInt(building.getChild("position").getAttributeValue("x")) * 16;
					this.martY = Integer.parseInt(building.getChild("position").getAttributeValue("y")) * 16;
				}

			}
		  } catch (IOException e) {e.printStackTrace();} catch (JDOMException e) {e.printStackTrace();  }} catch (URISyntaxException e) {e.printStackTrace();}
	}
	
	protected void loadMap(String path){
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

		if(!(this instanceof Building) && (x == hospitalX / 16) && y == hospitalY / 16) return Tile.HOSPITAL_DOOR;
		if(!(this instanceof Building) && (Math.abs( x - hospitalX / 16 ) <= 2) && 
			(y - hospitalY / 16) >= -2 && y - hospitalY / 16 < 1){
			int xPos = x - hospitalX / 16 ;
			int yPos = (y - hospitalY / 16);

			if(xPos == -2 && yPos == -2) return Tile.HOSPITAL_1;
			if(xPos == -1 && yPos == -2) return Tile.HOSPITAL_2;
			if(xPos == 0 && yPos == -2) return Tile.HOSPITAL_3;
			if(xPos == 1 && yPos == -2) return Tile.HOSPITAL_4;
			if(xPos == 2 && yPos == -2) return Tile.HOSPITAL_5;
			if(xPos == -2 && yPos == -1) return Tile.HOSPITAL_6;
			if(xPos == -1 && yPos == -1) return Tile.HOSPITAL_7;
			if(xPos == 0 && yPos == -1) return Tile.HOSPITAL_8;
			if(xPos == 1 && yPos == -1) return Tile.HOSPITAL_9;
			if(xPos == 2 && yPos == -1) return Tile.HOSPITAL_10;
			if(xPos == -2 && yPos == 0) return Tile.HOSPITAL_11;
			if(xPos == -1 && yPos == 0) return Tile.HOSPITAL_12;
			if(xPos == 1 && yPos == 0) return Tile.HOSPITAL_14;
			if(xPos == 2 && yPos == 0) return Tile.HOSPITAL_15;
		}
		if(!(this instanceof Building) && (x == martX / 16) && y == martY / 16) return Tile.MART_DOOR;
		if(!(this instanceof Building) && (Math.abs( x - martX / 16 ) <= 2) && 
			(y - martY / 16) >= -2 && y - martY / 16 < 1){
			int xPos = x - martX / 16 ;
			int yPos = (y - martY / 16);

			if(xPos == -2 && yPos == -2) return Tile.MART_1;
			if(xPos == -1 && yPos == -2) return Tile.MART_2;
			if(xPos == 0 && yPos == -2) return Tile.MART_3;
			if(xPos == 1 && yPos == -2) return Tile.MART_4;
			if(xPos == 2 && yPos == -2) return Tile.MART_5;
			if(xPos == -2 && yPos == -1) return Tile.MART_6;
			if(xPos == -1 && yPos == -1) return Tile.MART_7;
			if(xPos == 0 && yPos == -1) return Tile.MART_8;
			if(xPos == 1 && yPos == -1) return Tile.MART_9;
			if(xPos == 2 && yPos == -1) return Tile.MART_10;
			if(xPos == -2 && yPos == 0) return Tile.MART_11;
			if(xPos == -1 && yPos == 0) return Tile.MART_12;
			if(xPos == 1 && yPos == 0) return Tile.MART_14;
			if(xPos == 2 && yPos == 0) return Tile.MART_15;
		}
		
		if(Controller.tileColours.containsKey(tileColour)) return Controller.tileColours.get(tileColour);
		return Tile.nullTile;
	}
	
	public void addChest(Chest chest){
		chests.add(chest);
	}
	
	public ArrayList<Chest> getChests(){
		return chests;
	}
	
	public int getHospitalX(){
		return hospitalX;
	}
	
	public int getHospitalY(){
		return hospitalY;
	}

	public int getMartX(){
		return martX;
	}
	
	public int getMartY(){
		return martY;
	}
}
