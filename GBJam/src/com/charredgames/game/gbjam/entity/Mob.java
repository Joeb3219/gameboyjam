package com.charredgames.game.gbjam.entity;

import java.util.Random;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.GBJam;
import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.battle.BattleMove;
import com.charredgames.game.gbjam.graphics.GameImage;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Mob extends Entity implements Cloneable{

	protected int health = 30, defaultHealth = 30, xMovement, yMovement;
	protected int strength = 5, dexterity = 5, defense = 5;
	protected int direction = 2, exp = 0, money = 0;
	protected boolean lostBattle, turning = false;
	protected MobMood mood;
	protected MobType type = MobType.NULL;
	protected BattleMove selectedMove = BattleMove.STAB;
	protected int viewDistance = 6;
	protected String name = "Bob Saget", phrase = "I like shorts! They're comfy!", losingPhrase = "I'll win next time!";
	protected Random rand = new Random();
	protected GameImage battleImage = GameImage.ITEM_SWORD;
	
	public Mob( int health, Sprite sprite, MobType type){
		this.sprite = sprite;
		this.health = health;
		this.defaultHealth = health;
		this.type = type;
		this.mood = type.getDefaultMood();
		inventory = new Inventory();
	}
	
	public Mob(MobType mobType, int x, int y, int health, Level level){
		this.x = x;
		this.y = y;
		this.originalX = x;
		this.originalY = y;
		this.health = health;
		this.defaultHealth = health;
		this.type = mobType;
		this.mood = type.getDefaultMood();
		this.level = level;
		inventory = new Inventory();
		Controller.addMob(this);
	}
	
	public Mob(Keyboard input, GBJam jamInstance){	
	}
	
	public void update(){
		if(exp < 0) exp = 0;
		move();
	}
	
	protected void move(){
		if(turning && rand.nextInt(150) == 0) direction = rand.nextInt(4);
	}
	
	protected BattleMove getRandomBattleMove(){
		return BattleMove.BLOCK;
	}
	
	public void render(Screen screen){
		Sprite facingSprite = Sprite.MOB2_FORWARD;
		if(direction == 1) facingSprite = Sprite.MOB2_LEFT;
		else if(direction == 2) facingSprite = Sprite.MOB2_BACKWARD;
		else if(direction == 3) facingSprite = Sprite.MOB2_RIGHT;
		screen.renderTile(this.x, this.y, facingSprite);
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
	
	protected boolean collision(int xCord, int yCord){
		for(int i = 0; i < 4; i++){ //Four point detection
			final int xPrime = ((this.x + xCord) + i % 2 * 10 + 2)/16;
			final int yPrime = ((this.y + yCord) + i % 2 * 12 + 2)/16;
			if(level.getTile(xPrime,yPrime).isSolid()) return true;
			for(Chest chest : level.getChests()){
				if(!chest.doesExist()) continue;
				if(chest.getX() == xPrime * 16 && chest.getY() == yPrime * 16) return true;
			}
			for(Mob mob : Controller.mobs){
				if(!mob.doesExist()) continue;
				if(mob.getX() == xPrime * 16 && mob.getY() == yPrime * 16) return true;
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
	
	public void face(int dir){
		if(dir == 0) this.direction = 2;
		else if(dir == 1) this.direction = 3;
		else if(dir == 2) this.direction = 0;
		else if(dir == 3) this.direction = 1;		
	}
	
	public void moveTowards(Mob mob, int xPrime, int yPrime){
		if(mob.x == xPrime && mob.y >= yPrime) mob.direction = 2;
		else if(mob.x == xPrime && mob.y >= yPrime) mob.direction = 0;
		else if(mob.x <= xPrime && mob.y == yPrime) mob.direction = 3;
		else if(mob.x >= xPrime && mob.y == yPrime) mob.direction = 1;

		if(mob.direction == 0) mob.y+= 16;
		else if(mob.direction == 1) mob.x -= 16;
		else if(mob.direction == 2) mob.y -= 16;
		else if(mob.direction == 3) mob.x += 16;
	}
	
	public boolean solidInWay(Mob mob, int xPrime, int yPrime){
		int direction = mob.getDirection();
		int x = mob.getX();
		int y = mob.getY();
		int distance = tileDistance(x, y, xPrime, yPrime);
		
		x /= 16;
		y /= 16;
		
		for(Chest chest : level.getChests()){
			int chestDistance = tileDistance(chest.getX(), chest.getY(), mob.getX(), mob.getY());
			int chestX = chest.getX() / 16;
			int chestY = chest.getY() / 16;
			if(!chest.doesExist() || (Math.abs(chestX - x) > mob.getViewDistance()) || (Math.abs(chestY - y) > mob.getViewDistance() * 16)) continue;
			if(direction == 0 && (chestX == x) && (chestDistance <= distance && chestY > y)) return true;
			if(direction == 2 && (chestX == x) && (chestDistance <= distance && chestY < y)) return true;
			if(direction == 3 && (chestY == y) && (chestDistance <= distance && chestX > x)) return true;
			if(direction == 1 && (chestY == y) && (chestDistance <= distance && chestX < x)) return true;
		}
		
		for(Mob otherMob : Controller.mobs){
			if(otherMob.getLevel() != mob.level) continue; 
			int chestDistance = tileDistance(otherMob.getX(), otherMob.getY(), mob.getX(), mob.getY());
			int mobX = otherMob.getX() / 16;
			int mobY = otherMob.getY() / 16;
			if(!otherMob.doesExist() || (Math.abs(mobX - x) > mob.getViewDistance()) || (Math.abs(mobY - y) > mob.getViewDistance() * 16)) continue;
			if(direction == 0 && (mobX == x) && (chestDistance <= distance && mobY > y)) return true;
			if(direction == 2 && (mobX == x) && (chestDistance <= distance && mobY < y)) return true;
			if(direction == 3 && (mobY == y) && (chestDistance <= distance && mobX > x)) return true;
			if(direction == 1 && (mobY == y) && (chestDistance <= distance && mobX < x)) return true;
		}
		
		return false;
	}
	
	public void setMovingDetails(int dir, boolean turns, int xMovement, int yMovement){
		if(dir >= 0 && dir <= 3) direction = dir;
		this.turning = turns;
		this.xMovement = xMovement;
		this.yMovement = yMovement;
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
	
	public GameImage getBattleImage(){
		return battleImage;
	}
	
	public BattleMove getSelectedMove(){
		return selectedMove;
	}
	
	public int getMoney(){
		return money;
	}
	
	public void setMoney(int num){
		this.money = num;
	}
	
	public void setMobStrings(String name, String phrase, String losingPhrase){
		this.name = name;
		this.phrase = phrase;
		this.losingPhrase = losingPhrase;
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
	
	public MobType getType(){
		return type;
	}
	
	public MobMood getMood(){
		return mood;
	}

	
}