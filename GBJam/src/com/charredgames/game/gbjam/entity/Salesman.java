package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;

public class Salesman extends Mob{

	
	public Salesman(int identifier, int health, Sprite sprite, MobType type) {
		super(identifier, health, sprite, type);
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
