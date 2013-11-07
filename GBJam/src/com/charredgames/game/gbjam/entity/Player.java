package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.inventory.Item;
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
		inventory.addItem(Item.APPLE, 200);
		inventory.addItem(Item.NULL, 10);
	}
	
	public void reset(){
		this.health = 20;
	}

	public void update(){
		moving = false;
		if(input.right) {
			direction = 3;
			x ++;
			moving = true;
		}
		if(input.left) {
			direction = 1;
			x --;
			moving = true;
		}
		if(input.up) {
			direction = 2;
			y --;
			moving = true;
		}
		if(input.down) {
			direction = 0;
			y ++;
			moving = true;
		}
		if(moving && level.getTile(x / 16, y / 16).dropped()) Controller.addMoney(1);
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

	
}