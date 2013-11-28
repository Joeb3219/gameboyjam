package com.charredgames.game.gbjam.inventory;

import java.util.HashMap;
import java.util.Map;

import com.charredgames.game.gbjam.graphics.GameImage;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Item {

	private String name, description;
	@SuppressWarnings("unused")
	private int id, cost, value;
	private boolean droppable;
	private ItemType type;
	private GameImage inventoryImage;
	private static Map<Integer, Item> items = new HashMap<Integer, Item>();
	
	public static final Item NULL = new Item(0, "Nullificate", ItemType.WEAPON, 1000000000, 0, false, GameImage.ITEM_SWORD);
	public static final Item SWORD = new Item(1, "Sword", ItemType.WEAPON, 100, 5, false, GameImage.ITEM_SWORD);
	public static final Item APPLE = new Item(2, "Apple", ItemType.EDIBLE, 40, 1, true, GameImage.ITEM_APPLE);
	public static final Item STRENGTH_POTION = new Item(3, "Strength Potion", ItemType.POTION, 2000, 1, true, GameImage.ITEM_STRENGTH_POTION);
	public static final Item DEXTERITY_POTION = new Item(4, "Dexterity Potion", ItemType.POTION, 2500, 1, true, GameImage.ITEM_DEXTERITY_POTION);
	public static final Item DEFENSE_POTION = new Item(5, "Defense Potion", ItemType.POTION, 2500, 1, true, GameImage.ITEM_DEFENSE_POTION);	
	public static final Item HEALTH_POTION_20 = new Item(6, "Health Potion [20]", ItemType.EDIBLE, 6000, 20, true, GameImage.ITEM_HEALTH_POTION);
	public static final Item HEALTH_POTION_10 = new Item(7, "Health Potion [10]", ItemType.EDIBLE, 3000, 10, true, GameImage.ITEM_HEALTH_POTION);
	public static final Item HEARTPIECE = new Item(8, "Heart piece", ItemType.HEARTPIECE, 10000, 1, true, GameImage.INVENTORY_SELECT);
	
	public Item(int id,String name, ItemType type, int cost, int value, boolean droppable, GameImage inventoryImage){
		this.name = name;
		this.id = id;
		this.value = value;
		this.cost = cost;
		this.type = type;
		this.inventoryImage = inventoryImage;
		this.droppable = droppable;
		items.put(id, this);
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
	
	public static Item getItem(int id){
		if(items.containsKey(id)) return items.get(id);
		return Item.NULL;
	}
	
	public boolean isDroppable(){
		return droppable;
	}
}