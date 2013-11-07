package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.inventory.Inventory;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class ItemDrop extends Entity{

	/*
	 * 
	 * Debating turning item drops into actual entities.
	 * 
	 */
	
	public ItemDrop(int x, int y, Inventory inventory){
		this.x = x;
		this.y = y;
		this.inventory = inventory;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public void render(Screen screen){
		//screen.renderTile(x, y, );
	}
	
}
