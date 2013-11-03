package com.charredgames.game.gbjam.mob;

import com.charredgames.game.gbjam.Keyboard;
import com.charredgames.game.gbjam.graphics.Sprite;

public class Player extends Mob{

	private Keyboard input;
	
	public Player(Keyboard input) {
		super(input);
		this.input = input;
		this.sprite = Sprite.PLAYER;
	}
	
	public void reset(){
		this.health = 20;
	}

	public void update(){
		if(input.right) x ++;
		if(input.left) x --;
		if(input.up) y --;
		if(input.down) y ++;
	}
	
	public int getHealth(){
		return health;
	}
	
}