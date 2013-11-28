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
	private InventoryState state = InventoryState.USE;
	public boolean showMenu = false, activated = false;
	
	public Inventory(){
		for(int i = 1; i <= 40; i++) slots.put(i, null);
	}
	
	public int getFirstFullSlot(){
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) return entry.getKey();
		}
		return 1;
	}
	
	public int getLastFullSlot(){
		int slot = 1;
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) slot = entry.getKey();
		}
		return slot;
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
	
	public InventorySlot scrollDown(){
		resetState();
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		int col = getCol(currentSlot);
		if((currentSlot + 1) != ((col + 1) * 7)){ //Check if the previous item is in the last column.
			selectedSlot = getInventorySlot(currentSlot + 1);
			if(selectedSlot == null) selectedSlot = getInventorySlot(getFirstFullSlot());
		}
		else {
			if(getInventorySlot(col * 7) != null) selectedSlot = getInventorySlot((col * 7));
			else selectedSlot = getInventorySlot(getFirstFullSlot());
		}
		if(selectedSlot == null) selectedSlot = getInventorySlot(currentSlot);
		return selectedSlot;
	}
	
	public InventorySlot scrollUp(){
		resetState();
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		int col = getCol(currentSlot);
		if((currentSlot - 1) != ((col - 1) * 7)){ //Check if the previous item is in the last column.
			selectedSlot = getInventorySlot(currentSlot - 1);
			if(selectedSlot == null) selectedSlot = getInventorySlot(getLastFullSlot());
		}
		else {
			if(getInventorySlot(col * 7) != null) selectedSlot = getInventorySlot(col * 7);
			else selectedSlot = getInventorySlot(getLastFullSlot());
		}
		if(selectedSlot == null) selectedSlot = getInventorySlot(currentSlot);
		return selectedSlot;
	}

	public InventorySlot scrollRight(){
		resetState();
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		if(currentSlot >= ((getCol(getLastFullSlot()) * 7) - 6) && currentSlot <= (getCol(getLastFullSlot()) * 7)){
			selectedSlot = getInventorySlot(7 - ((getCol(getLastFullSlot()) * 7) - currentSlot));
			if(selectedSlot == null) selectedSlot = getInventorySlot(getFirstFullSlot());
		}
		else{
			selectedSlot = getInventorySlot(currentSlot + 7);
			if(selectedSlot == null) selectedSlot = getInventorySlot(getLastFullSlot());
		}
		return selectedSlot;
	}
	
	public InventorySlot scrollLeft(){
		resetState();
		if(isEmpty()) return selectedSlot;
		int currentSlot = getSlot(selectedSlot.getItem());
		if(currentSlot >= 1 && currentSlot <= 7){
			selectedSlot = getInventorySlot((getCol(getLastFullSlot()) * 7) - (7 - currentSlot));
			if(selectedSlot == null) selectedSlot = getInventorySlot(getFirstFullSlot());
		}
		else{
			selectedSlot = getInventorySlot(currentSlot - 7);
			if(selectedSlot == null) selectedSlot = getInventorySlot(getLastFullSlot());
		}
		return selectedSlot;
	}
	
	public int getFilledSlots(){
		int num = 0;
		for(Entry<Integer, InventorySlot> entry : slots.entrySet()){
			if(entry.getValue() != null) num++;
		}
		return num;
	}
	
	public int getCol(int slot){
		return (int) Math.ceil(slot / 7) + 1;
	}
	
	public InventoryState getState(){
		return state;
	}
	
	public void setState(InventoryState state){
		this.state = state;
	}
	
	public void resetState(){
		this.state = InventoryState.USE;
		showMenu = false;
		activated = false;
	}
	
	public void getNextState(){
		if(state == InventoryState.USE) state =   InventoryState.USE_ALL;
		else if(state == InventoryState.USE_ALL) state =   InventoryState.EXAMINE;
		else if(state == InventoryState.EXAMINE) state =   InventoryState.DROP;
		else state =  InventoryState.USE;
	}
	
	public void getPreviousState(){
		if(state == InventoryState.DROP) state =   InventoryState.EXAMINE;
		else if(state == InventoryState.EXAMINE) state =   InventoryState.USE_ALL;
		else if(state == InventoryState.USE_ALL) state =   InventoryState.USE;
		else state =  InventoryState.DROP;
	}

}