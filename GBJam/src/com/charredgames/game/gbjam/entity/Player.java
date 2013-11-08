package com.charredgames.game.gbjam.entity;

import java.util.Map.Entry;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.GameEvent;
import com.charredgames.game.gbjam.GameMessage;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.InventorySlot;
import com.charredgames.game.gbjam.inventory.Item;
import com.charredgames.game.gbjam.inventory.ItemType;
import com.charredgames.game.gbjam.level.Level;

public class Player extends Mob{

	private Keyboard input;
	
	public Player(Keyboard input) {
		super(input);
		inventory = new Inventory();
		this.input = input;
		this.sprite = Sprite.PLAYER_FORWARD;
		type = MobType.PLAYER;
		inventory.addItem(Item.SWORD, 1);
		inventory.addItem(Item.APPLE, 2000);
	}
	
	public void reset(){
		this.health = 20;
	}

	public void update(){
		moving = false;
		int xPrime = 0, yPrime = 0;
		if(input.right) xPrime ++;
		if(input.left) xPrime --;
		if(input.up) yPrime --;
		if(input.down) yPrime ++;
		
		if(xPrime != 0 || yPrime != 0) canMove(xPrime, yPrime);
		
		if(moving && level.getTile(x / 16, y / 16).dropped()) Controller.addMoney(1);
		
		if(input.a && GBJam.currentEvent == GameEvent.NULL){
			if(checkChests() || checkMobs()){
				
			}
			else if(inventory.getSelectedItem().getItem().getType() == ItemType.EDIBLE){
				heal(Item.APPLE.getValue());
				inventory.removeItem(Item.APPLE, 1);
				new GameMessage("You ate 1 of" + inventory.getSelectedItem().getItem().getName());
				GameEvent.setEvent(GameEvent.EATING);
			}
			else if(inventory.getSelectedItem().getItem().getType() == ItemType.WEAPON){
				GameEvent.setEvent(GameEvent.WEAPON);
			}
		}
		
	}

	private boolean checkMobs(){
		for(Mob mob : level.getMobs()){
			if(tileDistance(x, y, mob.getX(), mob.getY()) < viewDistance){
				
				return true;
			}
		}
		return false;
	}
	
	private boolean checkChests(){
		for(Chest chest : level.getChests()){
			if(chest.doesExist() && ((getRelativeDirection(x, y, chest.getX(), chest.getY()) == 4) || (getRelativeDirection(x, y, chest.getX(), chest.getY()) == direction))){
				if(tileDistance(x, y, chest.getX(), chest.getY()) < viewDistance){
					for(Entry<Integer, InventorySlot> entry : chest.getInventory().getSlots().entrySet()){
						if(entry.getValue() == null) continue;
						inventory.addItem(entry.getValue().getItem(), entry.getValue().getQuantity());
						chest.remove();
						new GameMessage("You gained " + entry.getValue().getQuantity() + " of " + entry.getValue().getItem().getName());
					}
					return true;
				}
			}
		}
		return false;
	}
	
	public int getHealth(){
		return health;
	}

	public void render(Screen screen){
		Sprite player = Sprite.PLAYER_FORWARD;
		if(direction==0) player = Sprite.PLAYER_FORWARD;
		else if(direction==1) player = Sprite.PLAYER_LEFT;
		else if(direction==2) player = Sprite.PLAYER_FORWARD;
		else if(direction==3) player = Sprite.PLAYER_RIGHT;
		screen.renderTile(this.x, this.y, player);
	}
	
	public void setLevel(Level level){
		this.level = level;
	}

	public void heal(int num){
		if(health + num <= 20) health += num;
	}

	public void damage(int num){
		if(health - num >= 0) health -= num;
	}
}