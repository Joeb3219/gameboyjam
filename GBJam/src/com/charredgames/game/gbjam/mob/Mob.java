package com.charredgames.game.gbjam.mob;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.level.Level;

public class Mob {

	protected int x, y, identifier;
	protected int health;
	protected boolean alive = true;
	protected Sprite sprite = Sprite.testSprite;
	private Level level;
	
	public static Mob testing = new Mob(0xFF111111, 10, Sprite.mob);
	
	public Mob(int identifier, int health, Sprite sprite){
		this.sprite = sprite;
		this.health = health;
		Controller.mobIdentifiers.put(identifier, this);
	}
	
	public Mob(int x, int y, int health, Sprite sprite, Level level){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input){	
	}
	
	public void spawn(int x, int y, Level level){
		new Mob(x, y, this.health, this.sprite, level);
	}
	
	public void update(){
		
	}
	
	public void render(Screen screen){
		screen.renderTile(this.x, this.y, this.sprite);
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
	
	public Sprite getSprite(){
		return sprite;
	}
	
	public boolean isAlive(){
		return alive;
	}
	
	public void kill(){
		alive = false;
	}
	
	public Level getLevel(){
		return level;
	}
}
