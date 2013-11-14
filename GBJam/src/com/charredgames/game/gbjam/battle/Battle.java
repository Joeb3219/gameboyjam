package com.charredgames.game.gbjam.battle;

import java.util.Random;

import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.Player;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 9, 2013
 */
public class Battle {

	private Player player;
	private Mob opponent, winner = null;
	private boolean over = false, playerTurn = true;
	private final Random rand = new Random();
	private BattleMove previousMove = BattleMove.STAB;
	
	public Battle(Player player, Mob opponent){
		this.player = player;
		this.opponent = opponent;
	}
	
	public void attack(BattleMove move){
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
		if(move == BattleMove.STAB) damage = ( (2 * attacker.getXPLevel()) + ((1/2 * attacker.getStrength()) + (1/8 * attacker.getDexterity())) / ((rand.nextInt(3) + 1)) ) + (rand.nextInt(4) / (rand.nextInt(4) + 1) ); 
		else if(move == BattleMove.SLASH) damage = ( (2 * attacker.getXPLevel()) + ((1/8 * attacker.getStrength()) + (1/2 * attacker.getDexterity())) / ((rand.nextInt(5) + 1)) ) + (6 / (rand.nextInt(4) + 1) ); 
		else if(move == BattleMove.BLOCK) damage = 0;
		
		if(previousMove == BattleMove.BLOCK) damage /= (rand.nextInt(Math.abs(damage)) + 1);
		
		if(attacker == player) opponent.damage(damage);
		else player.damage(damage);
		
		previousMove = move;
		
		if(player.getHealth() <= 0){
			winner = opponent;
			opponent.heal((opponent.getDefaultHealth() - opponent.getHealth()));
		}
		else if(opponent.getHealth() <= 0) winner = player;
		if(winner != null) over = true;
	}
	
	public Mob getWinner(){
		return winner;
	}
	
	public Mob getLoser(){
		if(winner == opponent) return player;
		return opponent;
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
