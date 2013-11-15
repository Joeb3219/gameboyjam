package com.charredgames.game.gbjam.entity;

import com.charredgames.game.gbjam.battle.BattleMove;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.graphics.Sprite;
import com.charredgames.game.gbjam.level.Level;

public class Biker extends Mob{

	public Biker(MobType mobType, int x, int y, int health, Level level){
		super(mobType, x, y, health, level);
		this.strength = 18;
		this.dexterity = 16;
		this.defense = 30;
		this.exp = EXP.FOUR.getMinXP();
		this.viewDistance = 4;
	}
	
	public BattleMove getRandomBattleMove(){
		int num = rand.nextInt(7);
		if(num < 4) return BattleMove.STAB;
		else if(num <= 6) return BattleMove.SLASH;
		else return BattleMove.BLOCK;
	}
	
	public void move(){
		if( ( xMovement > 0 && yMovement > 0) && rand.nextInt(200) == 1){
			int dir = rand.nextInt(4);
			int xChange = 0, yChange = 0;
			if(dir == 0) yChange += 16;
			else if(dir == 1) xChange -= 16;
			else if(dir == 2) yChange -= 16;
			else if(dir == 3) xChange += 16;
			if(!collision(xChange,0) && ( Math.abs((originalX + xChange) / 16)) < Math.abs((xMovement + originalX / 16))){
				x += xChange;
				moving = true;
			}
			if(!collision(0,yChange) && ( Math.abs((originalY + yChange) / 16)) < Math.abs((yMovement + originalY / 16))){
				y += yChange;
				moving = true;
			}
			direction = dir;
		}
	}
	
	public void render(Screen screen){
		Sprite facingSprite = Sprite.MOB2_FORWARD;
		if(direction == 1) facingSprite = Sprite.MOB2_LEFT;
		else if(direction == 2) facingSprite = Sprite.MOB2_BACKWARD;
		else if(direction == 3) facingSprite = Sprite.MOB2_RIGHT;
		screen.renderTile(this.x, this.y, facingSprite);
	}
	
}
