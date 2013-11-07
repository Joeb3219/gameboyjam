package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Chest extends Entity{

	private int damage = 0;
	
	public Chest(Level level, int x, int y, int damage, Inventory inventory){
		this.x = x;
		this.y = y;
		this.level = level;
		this.damage = damage;
		this.inventory = inventory;
	}
	
	public Chest(Level level, int x, int y, int damage){
		this.x = x;
		this.y = y;
		this.level = level;
		this.damage = damage;
		randomInventory();
	}
	
	private void randomInventory(){
		
	}
	
	public int getDamage(){
		return damage;
	}
	
	public void render(Screen screen){
		screen.renderTile(x, y, sprite);
	}
	
}