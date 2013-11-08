package com.charredgames.game.gbjam.entity;

import java.util.Map.Entry;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.GameEvent;
import com.charredgames.game.gbjam.GameMessage;
import com.charredgames.game.gbjam.GameState;
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
		if(input.right) xPrime += 16;
		if(input.left) xPrime -= 16;
		if(input.up) yPrime -= 16;
		if(input.down) yPrime += 16;
		
		if(xPrime != 0 || yPrime != 0) canMove(xPrime, yPrime);
		
		if(moving && level.getTile(x / 16, y / 16).dropped()) Controller.addMoney(1);
		
		checkMobs();
		
		if(input.a && GBJam.currentEvent == GameEvent.NULL){
			if(checkChests()) GameEvent.setEvent(GameEvent.OPENED_CHEST);
			else if(inventory.getSelectedItem().getItem().getType() == ItemType.EDIBLE){
				heal(inventory.getSelectedItem().getItem().getValue());
				inventory.removeItem(inventory.getSelectedItem().getItem(), 1);
				new GameMessage("You ate 1 of " + inventory.getSelectedItem().getItem().getName());
				GameEvent.setEvent(GameEvent.EATING);
			}
			else if(inventory.getSelectedItem().getItem().getType() == ItemType.WEAPON){
				GameEvent.setEvent(GameEvent.WEAPON);
			}
		}
		
	}

	private boolean checkMobs(){
		for(Mob mob : Controller.mobs){
			if(mob.getLevel() != level) continue;
			if(tileDistance(x, y, mob.getX(), mob.getY()) < mob.getViewDistance()){
				if(isFacing(mob.getDirection(), x, y, mob.getX(), mob.getY())){
					if(input.a || mob.getMood() == MobMood.AGRESSIVE){
						GBJam.setHUDMob(mob);
						GBJam.toggleBottomHud(true);
						if(mob.getMood() != MobMood.PASSIVE) battle();
						return true;
					}
				}
			}
		}
		GBJam.toggleBottomHud(false);
		return false;
	}
	
	private void battle(){
		GBJam.setGameState(GameState.BATTLE);
		System.out.println("GET READY TO FIGHT!");
	}
	
	private boolean checkChests(){
		for(Chest chest : level.getChests()){
			if(chest.doesExist() && isFacing(direction, x, y, chest.getX(), chest.getY())){
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