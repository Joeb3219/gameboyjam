package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Mob extends Entity{

	protected int  identifier, direction, health;
	protected boolean moving = false;
	protected MobMood mood = MobMood.NULL;
	protected MobType type = MobType.NULL;
	protected int viewDistance = 6;
	protected String name = "Bob Saget", phrase = "I like shorts! They're comfy!";
	
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
	
	public String getPhrase(){
		return phrase;
	}
	
	public String getName(){
		return name;
	}
	
	public Level getLevel(){
		return level;
	}

	public void damage(int num){
		health -= num;
	}
	
	public void heal(int num){
		health += num;
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

	protected void canMove(int xCord, int yCord){
		if(xCord > 0){direction = 3;}
		if(xCord < 0){direction = 1;}
		if(yCord > 0){direction = 2;}
		if(yCord < 0){direction = 0;}
		if(!collision(xCord,0)){
			x += xCord;
			moving = true;
		}
		if(!collision(0,yCord)){
			y += yCord;
			moving = true;
		}
	}
	
	private boolean collision(int xCord, int yCord){
		for(int i = 0; i < 4; i++){ //Four point detection
			int xPrime = ((this.x + xCord) + i % 2 * 10 + 2)/16;
			int yPrime = ((this.y + yCord) + i % 2 * 12 + 2)/16;
			if(level.getTile(xPrime,yPrime).isSolid()) return true;
		}
		return false;
	}
	
	public int tileDistance(int x, int y, int xPrime, int yPrime){
		int xDist = Math.abs(xPrime - x)/16;
		int yDist = Math.abs(yPrime - y)/16;
		return xDist + yDist;
	}
	
	public int getRelativeDirection(int x, int y, int xPrime, int yPrime){
		int xDelta = xPrime - x;
		int yDelta = yPrime - y;
		if(xDelta < 0) return 1;
		if(xDelta > 0) return 3;
		if(yDelta < 0) return 2;
		if(yDelta > 0) return 0;
		return 4; //Standing on the same tile
	}
	
	public int getViewDistance(){
		return viewDistance;
	}
}