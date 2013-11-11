package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.Controller;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.inventory.Inventory;
import com.charredgames.game.gbjam.level.Level;

public class Salesman extends Mob{

	
	public Salesman(int identifier, int health, Sprite sprite, MobType type) {
		super(identifier, health, sprite, type);
	}

	public Salesman(MobType mobType, int x, int y, int health, Level level){
		super(mobType, x, y, health, level);
	}
	
	//public void move(){
		
	//}
	
	public void render(Screen screen){
		Sprite facingSprite = Sprite.MOB_FORWARD;
		if(direction == 1) facingSprite = Sprite.MOB_LEFT;
		else if(direction == 2) facingSprite = Sprite.MOB_BACKWARD;
		else if(direction == 3) facingSprite = Sprite.MOB_RIGHT;
		screen.renderTile(this.x, this.y, facingSprite);
	}
	
}
