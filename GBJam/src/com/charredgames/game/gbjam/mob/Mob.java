package com.charredgames.game.gbjam.mob;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;

public class Mob {

	protected int x, y;
	protected int health;
	protected boolean alive = true;
	protected Sprite sprite = Sprite.testSprite;
	
	public Mob(int x, int y, int health, Sprite sprite){
		this.x = x;
		this.y = y;
		this.sprite = sprite;
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input){	
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
}
