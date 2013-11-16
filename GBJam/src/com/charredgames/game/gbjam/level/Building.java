package com.charredgames.game.gbjam.level;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.charredgames.game.gbjam.entity.Biker;
import com.charredgames.game.gbjam.entity.Chest;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.MobType;
import com.charredgames.game.gbjam.entity.Salesman;
import com.charredgames.game.gbjam.entity.Youngster;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.Item;

public class Building extends Level{

	public static final Building HOSPITAL = new Building("/buildings/hospital");
	
	public Building(String path){
		super(path);
		this.hospitalX = -128;
		this.hospitalY = -128;
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
				else newMob = new Salesman(MobType.SALESMAN, 0, 0, health, this);

						
				newMob.setMobStrings(mob.getChild("info").getAttributeValue("name"), mob.getChild("info").getAttributeValue("phrase"), mob.getChild("info").getAttributeValue("losingPhrase"));
				newMob.setMoney(Integer.parseInt(mob.getChild("data").getAttributeValue("money")));
				newMob.setMovingDetails(Integer.parseInt(mob.getChild("position").getAttributeValue("direction")), Boolean.parseBoolean(mob.getChild("movement").getAttributeValue("turns")), Integer.parseInt(mob.getChild("movement").getAttributeValue("xMovement")), Integer.parseInt(mob.getChild("movement").getAttributeValue("yMovement")));
			}
		  } catch (IOException e) {e.printStackTrace();} catch (JDOMException e) {e.printStackTrace();  }} catch (URISyntaxException e) {e.printStackTrace();}
	}
	
}
