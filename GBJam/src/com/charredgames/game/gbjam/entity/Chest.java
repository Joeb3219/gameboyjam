package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public class Chest extends Entity{
	
	public Chest(Level level, int x, int y, Inventory inventory){
		this.sprite = Sprite.CHEST;
		this.x = x;
		this.y = y;
		this.level = level;
		this.inventory = inventory;
	}
	
	public Chest(Level level, int x, int y, int damage){
		this.sprite = Sprite.CHEST;
		this.x = x;
		this.y = y;
		this.level = level;
		randomInventory();
	}
	
	private void randomInventory(){
		
	}
	
	public void render(Screen screen){
		screen.renderTile(x, y, sprite);
	}
	
}