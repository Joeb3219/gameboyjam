package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 5, 2013
 */
public abstract class Entity {

	protected int x, y, originalX, originalY;
	protected Level level;
	protected boolean exists = true, moving = false;
	protected Inventory inventory;
	protected Sprite sprite = Sprite.nullSprite;
	
	public void update(){
		
	}
	
	public void setPosition(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public Inventory getInventory(){
		return inventory;
	}
	
	public boolean doesExist(){
		return exists;
	}
	
	public void remove(){
		exists = false;
	}
	
	public Sprite getSprite(){
		return sprite;
	}
	
}
