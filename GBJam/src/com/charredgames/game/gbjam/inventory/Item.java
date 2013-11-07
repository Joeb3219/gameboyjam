package com.charredgames.game.gbjam.inventory;

import com.charredgames.game.gbjam.graphics.GameImage;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Item {

	private String name;
	private int id, cost, value;
	private ItemType type;
	private GameImage inventoryImage;
	
	public static Item NULL = new Item(0, "Nullificate", ItemType.WEAPON, 1000000000, 0, GameImage.ITEM_SWORD);
	public static Item SWORD = new Item(1, "Sword", ItemType.WEAPON, 100, 5, GameImage.ITEM_SWORD);
	public static Item APPLE = new Item(2, "Apple", ItemType.EDIBLE, 40, 1, GameImage.ITEM_APPLE);
	
	
	public Item(int id,String name, ItemType type, int cost, int value, GameImage inventoryImage){
		this.name = name;
		this.id = id;
		this.value = value;
		this.cost = cost;
		this.type = type;
		this.inventoryImage = inventoryImage;
	}
	
	public String getName(){
		return name;
	}
	
	public int getCost(){
		return cost;
	}
	
	public ItemType getType(){
		return type;
	}
	
	
	public GameImage getImage(){
		return inventoryImage;
	}
	
	public int getValue(){
		return value;
	}
	
}