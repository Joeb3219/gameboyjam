package com.charredgames.game.gbjam.entity;

import java.util.Map.Entry;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.GameEvent;
import com.charredgames.game.gbjam.GameMessage;
import com.charredgames.game.gbjam.GameState;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.battle.Battle;
import com.charredgames.game.gbjam.battle.BattleMove;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.InventorySlot;
import com.charredgames.game.gbjam.inventory.Item;
import com.charredgames.game.gbjam.inventory.ItemType;
import com.charredgames.game.gbjam.level.Level;

public class Player extends Mob{

	private Keyboard input;
	private int maxHealth = 20;
	
	public Player(Keyboard input) {
		super(input);
		inventory = new Inventory();
		this.input = input;
		this.sprite = Sprite.PLAYER_FORWARD;
		type = MobType.PLAYER;
		this.viewDistance = 1;
		inventory.addItem(Item.SWORD, 1);
		inventory.addItem(Item.APPLE, 100);
	}
	
	public void reset(){
		this.health = maxHealth;
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
			else if(inventory.getSelectedItem().getItem().getType() == ItemType.EDIBLE && health < maxHealth){
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
			if(input.a && tileDistance(x, y, mob.getX(), mob.getY()) == 1){
				if(isFacing(direction, x, y, mob.getX(), mob.getY())){
					GBJam.setHUDMob(mob);
					GBJam.toggleBottomHud(true);
					mob.remove();
					//if(mob.getMood() != MobMood.PASSIVE) battle();
					addXP(100);
					return true;
				}
			}
			else if(mob.getMood() == MobMood.AGRESSIVE && tileDistance(x, y, mob.getX(), mob.getY()) < mob.getViewDistance()){
				if(isFacing(mob.getDirection(), x, y, mob.getX(), mob.getY())){
					GBJam.setHUDMob(mob);
					GBJam.toggleBottomHud(true);
					if(mob.getMood() != MobMood.PASSIVE) battle(mob);
					return true;
				}
			}
		}
		GBJam.toggleBottomHud(false);
		return false;
	}
	
	private void battle(Mob mob){
		GBJam.setGameState(GameState.BATTLE);

		Battle battle = new Battle(this, mob, this.level);
		if(!battle.attack(true, BattleMove.STAB)){
			if(battle.getWinner() != this) setPosition(level.getHospitalX(), level.getHospitalY());
			else mob.remove();
		}
		
		GBJam.setGameState(GameState.GAME);
	}
	
	private boolean checkChests(){
		for(Chest chest : level.getChests()){
			if(chest.doesExist() && isFacing(direction, x, y, chest.getX(), chest.getY())){
				if(tileDistance(x, y, chest.getX(), chest.getY()) <= viewDistance){
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
		health += num;
		if(health > maxHealth)  health = maxHealth;
	}

	public void damage(int num){
		if(health - num >= 0) health -= num;
	}
}