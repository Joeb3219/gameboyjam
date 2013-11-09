package com.charredgames.game.gbjam.battle;

import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.Player;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 9, 2013
 */
public class Battle {

	private Player player;
	private Mob opponent, winner;
	private Level level;
	private boolean over = false;
	
	public Battle(Player player, Mob opponent, Level level){
		this.player = player;
		this.opponent = opponent;
		this.level = level;
	}
	
	public boolean attack(boolean playerAttack, BattleMove move){
		if(playerAttack){
			opponent.damage(100);
		}else{
			player.damage(10);
		}
		if(player.getHealth() <= 0) winner = opponent;
		else if(opponent.getHealth() <= 0) winner = player;
		if(winner == player || winner == opponent) {
			over = true;
			return false;
		}
		return true;
	}
	
	public Mob getWinner(){
		return winner;
	}
	
	public Level getLevel(){
		return level;
	}
	
}
