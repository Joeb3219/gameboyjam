package com.charredgames.game.gbjam.inventory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Inventory {

	private Map<Integer, InventorySlot> slots = new LinkedHashMap<Integer, InventorySlot>(); //Controlls Inventory slots
	private InventorySlot selectedSlot = new InventorySlot(Item.SWORD, 1);
	
	public Inventory(){
		for(int i = 1; i <= 40; i++) slots.put(i, null);
	}
	
	public int getFirstFullSlot(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) return entry.getKey();
		}
		return 1;
	}
	
	public int getEmptySlot(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() == null) return entry.getKey();
		}
		return 1;
	}
	
	public boolean isEmpty(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) return false;
		}
		return true;
	}
	
	public boolean isFull(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() == null) return false;
		}
		return true;
	}
	
	public InventorySlot getInventorySlot(int slot){
		if(slots.get(slot) != null) return slots.get(slot);
		return null;
	}
	
	public int getSlot(Item item){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item) return entry.getKey();
		}
		return 1;
	}
	
	public void addItem(Item item){
		boolean matchFound = false;
		InventorySlot inv = null;
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				matchFound = true;
				inv = entry.getValue();
				break;
			}
		}
		if(matchFound){
			inv.add(1);
		}
		else{
			if(isFull()) return;
			slots.put(getEmptySlot(), new InventorySlot(item, 1));
		}
	}
	
	public void addItem(Item item, int amount){
		boolean matchFound = false;
		InventorySlot inv = null;
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				matchFound = true;
				inv = entry.getValue();
				break;
			}
		}
		if(matchFound){
			inv.add(amount);
		}
		else{
			if(isFull()) return;
			slots.put(getEmptySlot(), new InventorySlot(item, amount));
		}
	}
	
	public void removeItem(Item item){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				if(selectedSlot.getItem() == item) selectedSlot = getInventorySlot(getFirstFullSlot());
				slots.put(entry.getKey(), null);
				break;
			}
		}
	}
	
	public void removeItem(Item item, int amount){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				InventorySlot invSlot = entry.getValue();
				if((invSlot.getQuantity() - amount) > 0) invSlot.add(-1 * amount);
				else {
					int slot = getSlot(item);
					removeItem(item);
					for(int i = slot; i <= slots.size(); i++){
						InventorySlot iSlot = slots.get(i);
						if(iSlot != null){
							if(slots.get(i - 1) == null) {
								slots.put(i, null);
								slots.put(i - 1, iSlot);
							}
						}
					}
				}
				break;
			}
		}
	}
	
	public void setItemQuantity(Item item, int amount){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				entry.getValue().setQuantity(amount);
				break;
			}
		}
	}
	
	public int getQuantity(Item item){
		if(isEmpty()) return 0;
		for(Entry <Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getItem() == item){
				return entry.getValue().getQuantity();
			}
		}
		return 0;
	}
	
	public Map<Integer, InventorySlot> getSlots(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null && entry.getValue().getQuantity() <= 0) removeItem(entry.getValue().getItem());
		}
		return slots;
	}
	
	public InventorySlot getSelectedItem(){
		//if(selectedItem == null) selectedItem = items.firstKey();
		return selectedSlot;
	}
	
	public InventorySlot getNextItem(){
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		if(getInventorySlot(currentSlot + 1) != null) selectedSlot = getInventorySlot(currentSlot + 1);
		else selectedSlot = getInventorySlot(1);
		if(selectedSlot == null) selectedSlot = getInventorySlot(currentSlot);
		return selectedSlot;
	}
	
	public InventorySlot getPreviousItem(){
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		if(getInventorySlot(currentSlot - 1) != null) selectedSlot = getInventorySlot(currentSlot - 1);
		else {
			for(int i = currentSlot; i <= 40; i++){
				if(getInventorySlot(i) != null) selectedSlot = getInventorySlot(i);
			}
		}
		if(selectedSlot == null) selectedSlot = getInventorySlot(currentSlot);
		return selectedSlot;
	}

	public int getFilledSlots(){
		int num = 0;
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) num++;
		}
		return num;
	}
}