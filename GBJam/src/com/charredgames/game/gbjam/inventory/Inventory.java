package com.charredgames.game.gbjam.inventory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Inventory {

	private Map<Integer, InventorySlot> slots = new LinkedHashMap<Integer, InventorySlot>(); //Controlls Inventory slots
	//private Map<Item, Integer> items = new LinkedHashMap<Item, Integer>(); //Controlls item quantities in inventory.
	private Item selectedItem = Item.SWORD;
	
	public Inventory(){
		for(int i = 1; i <= 40; i++) slots.put(i, null);
	}
	
	public int getEmptySlot(){
		for(Entry<Integer, Item> entry : slots.entrySet()){
			if(entry.getValue() == null) return entry.getKey();
		}
		return 100;
	}
	
	public boolean isFull(){
		for(Entry<Integer, Item> entry : slots.entrySet()){
			if(entry.getValue() != null) return false;
		}
		return true;
	}
	
	public Item getItem(int slot){
		if(slots.get(slot) != null) return slots.get(slot);
		return null;
	}
	
	public int getSlot(Item item){
		for(Entry<Integer, Item> entry : slots.entrySet()){
			if(entry.getValue() == item) return entry.getKey();
		}
		return 1;
	}
	
	public void addItem(Item item){
		if(!slots.containsValue()) {
			if(!isFull()){
				InventorySlot = 
				slots.put(getEmptySlot(), item);
				items.put(item, 1);
			}
			return;
		}
		else items.put(item, items.get(item) + 1);
	}
	
	public void addItem(Item item, int amount){
		if(!items.containsKey(item)){
			if(!isFull()){
				slots.put(getEmptySlot(), item);
				items.put(item, amount);
			}
			return;
		}
		else items.put(item, items.get(item) + amount);
	}
	
	public void removeItem(Item item){
		for(Entry<Integer, Item> entry : slots.entrySet()){
			if(entry.getValue() == item){
				slots.put(entry.getKey(), null);
				break;
			}
		}
		items.remove(item);
	}
	
	public void removeItem(Item item, int amount){
		int itemCount = items.get(item);
		removeItem(item);
		if(itemCount - amount > 0){
			addItem(item, itemCount - amount);
		}
	}
	
	public void setItemQuantity(Item item, int amount){
		items.put(item, amount);
	}
	
	public int getQuantity(Item item){
		if(!items.containsKey(item)) return 0;
		return items.get(item);
	}
	
	public Map<Item, Integer> getItems(){
		return items;
	}
	
	public Item getSelectedItem(){
		//if(selectedItem == null) selectedItem = items.firstKey();
		return selectedItem;
	}
	
	public Item getNextItem(){
		int currentSlot = getSlot(selectedItem);
		if(getItem(currentSlot + 1) != null) selectedItem = getItem(currentSlot + 1);
		else selectedItem = getItem(1);
		return selectedItem;
	}
	
	public Item getPreviousItem(){
		int currentSlot = getSlot(selectedItem);
		if(getItem(currentSlot - 1) != null) selectedItem = getItem(currentSlot - 1);
		else selectedItem = getItem(1);
		return selectedItem;
	}
}
