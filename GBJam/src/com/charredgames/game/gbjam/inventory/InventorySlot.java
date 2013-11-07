package com.charredgames.game.gbjam.inventory;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 6, 2013
 */
public class InventorySlot {

	private int quantity;
	private Item item;
	
	public InventorySlot(Item item, int quantity){
		this.item = item;
		this.quantity = quantity;
	}
	
	public Item getItem(){
		return item;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public void setQuantity(int num){
		this.quantity = num;
	}
	
	public void add(int num){
		this.quantity += num;
	}
	
}
