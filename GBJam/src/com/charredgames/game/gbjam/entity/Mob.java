package com.charredgames.game.gbjam.entity;

import java.util.Random;

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

	protected int identifier, health = 30, defaultHealth;
	protected int strength = 5, dexterity = 5, defense = 5;
	protected int direction = 2, exp = 0;
	protected boolean lostBattle, moving = false;
	protected MobMood mood;
	protected MobType type = MobType.NULL;
	protected int viewDistance = 6;
	protected String name = "Bob Saget", phrase = "I like shorts! They're comfy!", losingPhrase = "I'll win next time!";
	protected Random rand = new Random();
	
	public static Mob testing = new Mob(0xFF111111, 30, Sprite.PLAYER_FORWARD, MobType.YOUNGSTER);
	public static Mob SALESMAN = new Salesman(0xFF222222, 30, Sprite.PLAYER_LEFT, MobType.SALESMAN);
	
	public Mob(int identifier, int health, Sprite sprite, MobType type){
		this.sprite = sprite;
		this.health = health;
		this.defaultHealth = health;
		this.type = type;
		this.mood = type.getDefaultMood();
		inventory = new Inventory();
		Controller.mobIdentifiers.put(identifier, this);
	}
	
	public Mob(MobType mobType, int x, int y, int health, Sprite sprite, Level level){
		this.x = x;
		this.y = y;
		this.health = health;
		this.defaultHealth = health;
		this.sprite = sprite;
		this.type = mobType;
		this.mood = type.getDefaultMood();
		this.level = level;
		inventory = new Inventory();
		Controller.addMob(this);
		setNameAndPhrase();
	}
	
	public Mob(Keyboard input){	
	}
	
	public void spawn(int x, int y, Level level){
		new Mob(this.type, x, y, this.health, this.sprite, level);
	}
	
	public void update(){
		if(exp < 0) exp = 0;
		move();
	}
	
	protected void move(){
		if(rand.nextInt(150) == 0) direction = rand.nextInt(4);
	}
	
	public void render(Screen screen){
		Sprite facingSprite = Sprite.MOB2_FORWARD;
		if(direction == 1) facingSprite = Sprite.MOB2_LEFT;
		else if(direction == 2) facingSprite = Sprite.MOB2_BACKWARD;
		else if(direction == 3) facingSprite = Sprite.MOB2_RIGHT;
		screen.renderTile(this.x, this.y, facingSprite);
	}
	
	private void setNameAndPhrase(){
		this.name = Controller.getNextName();
		this.phrase = Controller.getNextPhrase();
	}
	
	public String getPhrase(){
		return phrase;
	}
	
	public String getLosingPhrase(){
		return losingPhrase;
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
		int originalDirection = direction;
		if(xCord > 0){direction = 3;}
		if(xCord < 0){direction = 1;}
		if(yCord > 0){direction = 2;}
		if(yCord < 0){direction = 0;}
		if(direction != originalDirection) return;
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
			final int xPrime = ((this.x + xCord) + i % 2 * 10 + 2)/16;
			final int yPrime = ((this.y + yCord) + i % 2 * 12 + 2)/16;
			if(level.getTile(xPrime,yPrime).isSolid()) return true;
			for(Chest chest : level.getChests()){
				if(!chest.doesExist()) continue;
				if(chest.getX() == xPrime * 16 && chest.getY() == yPrime * 16) return true;
			}
		}
		return false;
	}
	
	public int tileDistance(int x, int y, int xPrime, int yPrime){
		int xDist = Math.abs(xPrime - x)/16;
		int yDist = Math.abs(yPrime - y)/16;
		return xDist + yDist;
	}
	
	public boolean isFacing(int direction, int x, int y, int xPrime, int yPrime){
		int xDelta = Math.abs(xPrime - x)/16;
		int yDelta = Math.abs(yPrime - y)/16;
		if(direction == 1 && yDelta == 0 && (xPrime - x) < 0) return true;
		else if(direction == 3 && yDelta == 0 && (xPrime - x) > 0) return true;
		else if(direction == 0 && xDelta == 0 && (yPrime - y) < 0) return true;
		else if(direction == 2 && xDelta == 0 && (yPrime - y) > 0) return true;
		return false;
	}
	
	public int getViewDistance(){
		return viewDistance;
	}
	
	public int getDirection(){
		return direction;
	}

	public int getExp(){
		return exp;
	}
	
	public int getXPLevel(){
		return EXP.getLevel(exp);
	}
	
	public void addXP(int num){
		exp += num;
	}
	
	public int getHealth(){
		return health;
	}
	
	public boolean didLose(){
		return lostBattle;
	}
	
	public void toggleBattleLost(boolean val){
		lostBattle = val;
	}
	
	public int getStrength(){
		return strength;
	}
	
	public int getDexterity(){
		return dexterity;
	}
	
	public int getDefense(){
		return defense;
	}
	
	public int getDefaultHealth(){
		return defaultHealth;
	}
}