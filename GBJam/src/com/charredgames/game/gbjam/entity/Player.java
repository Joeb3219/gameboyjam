package com.charredgames.game.gbjam.entity;

import java.util.Map.Entry;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.GameEvent;
import com.charredgames.game.gbjam.GameMessage;
import com.charredgames.game.gbjam.GameState;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.battle.Battle;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.InventorySlot;
import com.charredgames.game.gbjam.inventory.Item;
import com.charredgames.game.gbjam.inventory.ItemType;
import com.charredgames.game.gbjam.level.Level;

public class Player extends Mob{

	private Keyboard input;
	private static GBJam gbjam;
	private int tickCount = 0;
	public Battle currentBattle;
	
	public Player(Keyboard input, GBJam jamInstance) {
		super(input, jamInstance);
		inventory = new Inventory();
		this.input = input;
		gbjam = jamInstance;
		this.sprite = Sprite.PLAYER_FORWARD;
		type = MobType.PLAYER;
		this.viewDistance = 1;
		inventory.addItem(Item.SWORD, 1);
		inventory.addItem(Item.APPLE, 100);
	}
	
	public void reset(){
		this.defaultHealth = 30;
		this.health = defaultHealth;
	}

	public void update(){
		if(exp < 0) exp = 0;
		tickCount ++;
		if(health <= 0) die();
		moving = false;
		int xPrime = 0, yPrime = 0;
		if(input.right) xPrime += 16;
		if(input.left) xPrime -= 16;
		if(input.up) yPrime -= 16;
		if(input.down) yPrime += 16;
		
		if(xPrime != 0 || yPrime != 0) canMove(xPrime, yPrime);
		
		if(moving && level.getTile(x / 16, y / 16).dropped()) Controller.addMoney(1);
		
		if(checkMobs()) return;
		else if(input.a && GBJam.currentEvent == GameEvent.NULL){
			Item selectedItem = inventory.getSelectedItem().getItem();
			if(checkChests()) GameEvent.setEvent(GameEvent.OPENED_CHEST);
			else if(selectedItem.getType() == ItemType.EDIBLE && health < defaultHealth){
				heal(selectedItem.getValue());
				inventory.removeItem(selectedItem, 1);
				new GameMessage("You ate 1 of " + selectedItem.getName());
				GameEvent.setEvent(GameEvent.EATING);
			}
			else if(selectedItem.getType() == ItemType.POTION){
				if(selectedItem == Item.STRENGTH_POTION) strength += Item.STRENGTH_POTION.getValue();
				if(selectedItem == Item.DEXTERITY_POTION) dexterity += Item.DEXTERITY_POTION.getValue();
				if(selectedItem == Item.DEFENSE_POTION) defense += Item.DEFENSE_POTION.getValue();
				new GameMessage("You drank 1 of " + selectedItem.getName());
				inventory.removeItem(selectedItem, 1);
				GameEvent.setEvent(GameEvent.EATING);
			}
		}
		
		//Hospital management
		if(tileDistance(x, y, level.getHospitalX(), level.getHospitalY()) <= 4){
			if(tickCount % GBJam._DESIREDTPS == 0){
				heal(1);
			}
		}
	}

	private boolean checkMobs(){
		for(Mob mob : Controller.mobs){
			if(mob.getLevel() != level) continue;
			if(input.a && tileDistance(x, y, mob.getX(), mob.getY()) == 1){
				if(isFacing(direction, x, y, mob.getX(), mob.getY()) || (!mob.didLose() && mob.getMood() == MobMood.AGRESSIVE && isFacing(mob.getDirection(), mob.getX(), y, x, mob.getY()))){
					GBJam.setHUDMob(mob);
					gbjam.showBottomHUD = true;
					mob.face(this.direction);
					if(!mob.didLose() && mob.getMood() != MobMood.PASSIVE) battle(mob);
					return true;
				}
			}
			else if(!mob.didLose() && mob.getMood() == MobMood.AGRESSIVE && tileDistance(x, y, mob.getX(), mob.getY()) < mob.getViewDistance()){
				if(isFacing(mob.getDirection(), mob.getX(), y, x, mob.getY()) && !solidInWay(mob, x, y)){
					GBJam.setHUDMob(mob);
					mob.face(this.direction);
					gbjam.showBottomHUD = true;
					battle(mob);
					return true;
				}
			}
		}
		gbjam.showBottomHUD = false;
		return false;
	}

	@SuppressWarnings("unused")
	private void battle(Mob mob){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nanoSeconds = 1000000000.0 / GBJam._DESIREDTPS;
		double delta = 0;
		int frames = 0, ticks = 0;
		Battle battle = new Battle(this, mob);
		currentBattle = battle;
		
		GBJam.setGameState(GameState.BATTLE);

		while(!battle.isOver()){
			long now = System.nanoTime();
			delta+= (now - lastTime) / nanoSeconds;
			lastTime = now;
			while(delta >= 1){
				gbjam.tick();
				ticks++;
				delta--;
				//Actual battle move code.
				if(tileDistance(x, y, mob.getX(), mob.getY()) > 1 || gbjam.showBottomHUD){
					if(tileDistance(x, y, mob.getX(), mob.getY()) > 1) mob.moveTowards(mob, this.x, this.y);
					else{
						if(input.a) gbjam.showBottomHUD = false;
						continue;
					}
					continue;
				}
				else{
					if(battle.isPlayerTurn()) {
						if(input.a) battle.attack(selectedMove);
					}
					else battle.attack(mob.getRandomBattleMove());
					if(input.right) selectedMove = Controller.getNextMove(selectedMove); 
					if(input.left) selectedMove = Controller.getPreviousMove(selectedMove); 
				}
				
			}
			gbjam.render();
			frames++;
			if((System.currentTimeMillis() - timer) > 1000){
				timer += 1000;
				ticks = 0;
				frames = 0;
			}
		}
		if(battle.getWinner() != this) lostBattle(battle);
		else wonBattle(battle);
		
		mob.setPosition(mob.originalX, mob.originalY);
		
		GBJam.setGameState(GameState.GAME);
	}
	
	private void lostBattle(Battle battle){
		addXP(-1 * battle.getWinningXP());
		die();
	}
	
	private void wonBattle(Battle battle){
		addXP(battle.getWinningXP());
		battle.getLoser().toggleBattleLost(true);
		new GameMessage("You defeated " + battle.getLoser().getName() + " and gained $" + battle.getLoser().getMoney());
		Controller.addMoney(battle.getLoser().getMoney());
	}
	
	private void die(){
		health = defaultHealth;
		setPosition(level.getHospitalX(), level.getHospitalY());
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
		else if(direction==2) player = Sprite.PLAYER_BACKWARD;
		else if(direction==3) player = Sprite.PLAYER_RIGHT;
		screen.renderTile(this.x, this.y, player);
	}
	
	public void setLevel(Level level){
		this.level = level;
	}

	public void heal(int num){
		health += num;
		if(health > defaultHealth)  health = defaultHealth;
	}

	public void damage(int num){
		health -= num;
	}
}