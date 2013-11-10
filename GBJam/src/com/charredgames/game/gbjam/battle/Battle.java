package com.charredgames.game.gbjam.battle;

import java.util.Random;

import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.Player;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 9, 2013
 */
public class Battle {

	private Player player;
	private Mob opponent, winner = null;
	private Level level;
	private boolean over = false;
	private final Random rand = new Random();
	
	public Battle(Player player, Mob opponent, Level level){
		this.player = player;
		this.opponent = opponent;
		this.level = level;
	}
	
	public boolean attack(boolean playerAttack, BattleMove move){
		Mob attacker;
		int damage = 0;
		if(playerAttack) attacker = player;
		else attacker = opponent;
		//if(move == BattleMove.STAB) damage = (4 * ((attacker.getXPLevel() + 2) ^ 2)) * (attacker.getStrength() / (rand.nextInt(100) + 1));
		if(move == BattleMove.STAB) damage = ( ((2 * attacker.getXPLevel()) * (1/2 * attacker.getStrength()) ) + (attacker.getStrength() / (rand.nextInt(100) + 1)/2) );
		System.out.println(attacker.getName() + " " + damage);
		if(attacker == player) opponent.damage(damage);
		else player.damage(damage);
		
		if(player.getHealth() <= 0){
			winner = opponent;
			opponent.heal((opponent.getDefaultHealth() - opponent.getHealth()));
		}
		else if(opponent.getHealth() <= 0) winner = player;
		if(winner != null) {
			over = true;
			return false;
		}
		return true;
	}
	
	public Mob getWinner(){
		return winner;
	}
	
	public Mob getLoser(){
		if(winner == opponent) return player;
		return opponent;
	}
	
	public Level getLevel(){
		return level;
	}
	
	public int getWinningXP(){
		return getLoser().getXPLevel() * 45;
	}
	
	public boolean isOver(){
		return over;
	}
	
}
