package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Mob extends Entity{

	protected int  identifier, direction, health;
	protected boolean moving = false;
	protected MobMood mood = MobMood.NULL;
	protected MobType type = MobType.NULL;
	
	public static Mob testing = new Mob(0xFF111111, 10, Sprite.PLAYER_FORWARD);
	
	public Mob(int identifier, int health, Sprite sprite){
		this.sprite = sprite;
		this.health = health;
		inventory = new Inventory();
		Controller.mobIdentifiers.put(identifier, this);
	}
	
	public Mob(int x, int y, int health, Sprite sprite, Level level){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		inventory = new Inventory();
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input){	
	}
	
	public void spawn(int x, int y, Level level){
		new Mob(x, y, this.health, this.sprite, level);
	}
	
	public void render(Screen screen){
		screen.renderTile(this.x, this.y, this.sprite);
	}
	
	public Level getLevel(){
		return level;
	}

	public void damage(int num){
		health -= num;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public MobType getType(){
		return type;
	}
	
	public MobMood getMood(){
		return mood;
	}
	
}