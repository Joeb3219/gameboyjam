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
	private boolean over = false, playerTurn = true;
	private final Random rand = new Random();
	
	public Battle(Player player, Mob opponent, Level level){
		this.player = player;
		this.opponent = opponent;
		this.level = level;
	}
	
	public boolean attack(BattleMove move){
		Mob attacker;
		int damage = 0;
		if(playerTurn) {
			playerTurn = false;
			attacker = player;
		}
		else {
			playerTurn = true;
			attacker = opponent;
		}
		if(move == BattleMove.STAB) damage = ( ((2 * attacker.getXPLevel()) * (1/2 * attacker.getStrength()) ) + (attacker.getStrength() / (rand.nextInt(100) + 1)/2) );// + 1;
		else if(move == BattleMove.SLASH) damage = ( ((2 * attacker.getXPLevel()) * (1/2 * attacker.getStrength()) ) + (attacker.getStrength() / (rand.nextInt(100) + 1)/2) );// + 1;
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
	
	public Mob getOpponent(){
		return opponent;
	}
	
	public boolean isPlayerTurn(){
		return playerTurn;
	}
	
}
