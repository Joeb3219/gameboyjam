package com.charredgames.game.gbjam;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;

import com.charredgames.game.gbjam.battle.Battle;
import com.charredgames.game.gbjam.battle.BattleMove;
import com.charredgames.game.gbjam.entity.Chest;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.Player;
import com.charredgames.game.gbjam.graphics.GameImage;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.inventory.InventorySlot;
import com.charredgames.game.gbjam.inventory.Item;
import com.charredgames.game.gbjam.level.Level;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @version 1.0.0
 * @since Nov 3, 2013
 */

public class GBJam extends Canvas implements Runnable{

	private static final long serialVersionUID = 1L;
	public static final int _WIDTH = 160;
	public static final int _HEIGHT = 144;
	public static final int _SCALE = 3;
	public static final int _DESIREDTPS = 10;
	public static String title = "GBJam";
	
	private static JFrame window;
	private static Graphics g;
	private BufferStrategy buffer;
	private Thread mainThread;
	private boolean isRunning = false;
	private BufferedImage img = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	@SuppressWarnings("unused")
	private static final Random rand = new Random();
	
	private Screen screen;
	private static Keyboard keyboard;
	private static Player player;
	private Level level = Level.spawnLevel;
	public static final int HUDHeight = 40;
	private static int HUD_BOTTOM_Height = 60;
	public boolean showBottomHUD = false;
	public static Mob BHUD_TARGET = Mob.testing;
	private static GameState gameState = GameState.GAME;
	public static GameEvent currentEvent = GameEvent.NULL;
	Font defaultFont;
	
	public void tick(){
		Controller.update();
		keyboard.update();
		GameMessage.updateMessages();
		GameEvent.updateCounter();
		if(gameState == GameState.GAME && !showBottomHUD){
			player.update();
			Controller.updateMobs();
		}
		else if(gameState == GameState.INVENTORY){
			if(keyboard.down) player.getInventory().scrollDown();
			else if(keyboard.up) player.getInventory().scrollUp();
			else if(keyboard.right) player.getInventory().scrollRight();
			else if(keyboard.left) player.getInventory().scrollLeft();
		}
		if(!showBottomHUD && keyboard.start && (Controller.tickCount%2 == 0)){
			if(gameState == GameState.INVENTORY) gameState = GameState.GAME;
			else gameState = GameState.INVENTORY;
			if(keyboard.a) keyboard.a = false;
			if(keyboard.b) keyboard.b = false;
		}
		if(keyboard.a && showBottomHUD && Controller.tickCount % 2 == 0) showBottomHUD = false;
	}
	
	public void render(){
		g.setFont(defaultFont);
		screen.clear();
		
		int xOffset = (player.getX()) - (_WIDTH/2);
		int yOffset = (player.getY()) - (_HEIGHT/2);
		int maxX = ((256 * 16) - (_WIDTH/2));
		int maxY = ((256 * 16) - (_HEIGHT/2));
		if(xOffset < 0) xOffset = 0;
		if(yOffset < 0) yOffset = 0;
		if(xOffset > maxX) xOffset = maxX;
		if(yOffset > maxY) yOffset = maxY;
		
		level.render(xOffset, yOffset, screen);
		for(Chest chest : level.getChests()) {
			if(chest.doesExist()) chest.render(screen);
		}
		Controller.renderMobs(screen);
		player.render(screen);
		
		for(int i = 0; i < pixels.length; i ++) pixels[i] = screen.pixels[i];
		
		g.drawImage(img, 0, 0, window.getWidth(), window.getHeight(), null);
		
		loadHUD();
		
		if(gameState == GameState.INVENTORY) loadInventory();
		
		if(showBottomHUD) {
			loadBottomHUD();
			buffer.show();
			return;
		}
		
		if(gameState == GameState.BATTLE) loadBattleScreen(); 
		
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 16));
		g.setColor(Color.WHITE);
		for(GameMessage message : GameMessage.messages){
			if(message == null) continue;
			if(((getWindowHeight()/2) - message.getY()) > 0){
				g.drawString(message.getMessage(), (getWindowWidth() - g.getFontMetrics().stringWidth(message.getMessage()) - 10), (getWindowHeight()/2) - message.getY());
				message.visible = true;
			}
			else message.visible = false;
		}
		
		buffer.show();
	}
	
	private void loadBattleScreen(){
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, window.getWidth(), window.getHeight());
		
		g.setColor(Color.BLACK);
		Battle battle = player.currentBattle;
		
		//Opponent rendering
		Mob opponent = battle.getOpponent();
		g.drawImage(opponent.getBattleImage().getImage(), 5, 5, 64, 64, null);
		int healthMultiplier = (350 / opponent.getDefaultHealth());
		int startingPos = 100;
		int startingYPos = 25;
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		g.drawString(opponent.getType().getTypeName() + " " + opponent.getName(), startingPos - 3, startingYPos - 7);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		g.drawString("Lvl " + opponent.getXPLevel() + ", " + opponent.getExp() + " EXP", (startingPos - 3) + 50 + g.getFontMetrics().stringWidth(opponent.getType().getTypeName() + " " + opponent.getName()), startingYPos - 7);
		g.setFont(defaultFont);
		
		g.setColor(Color.GRAY);
		g.fillRect(startingPos - 3, startingYPos - 3, 356 + g.getFontMetrics().stringWidth(opponent.getHealth() + "/" + opponent.getDefaultHealth()), 16);
		g.setColor(Color.BLACK);
		g.drawString(opponent.getHealth() + "/" + opponent.getDefaultHealth(), 446, startingYPos + 9);
		
		//Draw health bar
		for(int health = 0; health <= opponent.getDefaultHealth(); health++){
			int drawX = health * healthMultiplier + startingPos;
			if(opponent.getHealth() >= health) g.setColor(Color.GREEN);
			else g.setColor(Color.RED);
			g.fillRect(drawX, 25, healthMultiplier, 10);
		}
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(startingPos, startingYPos + 25, 375, 125, 20, 20);
		g.setColor(Color.GRAY);
		g.fillRoundRect(startingPos + 5, startingYPos + 30, 365, 115, 20, 20);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		if(false && battle.isPlayerTurn()) g.drawString("Player's turn", startingPos + 20, startingYPos + 50);
		else{
			int xPos = startingPos + 5;
			int yPos = startingYPos + 30;
			for(BattleMove move : Controller.moves){
				if(move == opponent.getSelectedMove()){
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect((xPos + move.xOffset) - 3, (yPos + move.yOffset - g.getFontMetrics().getHeight()) + 3, (g.getFontMetrics().stringWidth(move.getName())) + 3, (g.getFontMetrics().getHeight()) + 3);
				}
				g.setColor(Color.WHITE);
				g.drawString(move.getName(), xPos + move.xOffset, yPos + move.yOffset);
			}
		}
		
		//Player rendering
		g.setColor(Color.BLACK);
		g.drawImage(player.getBattleImage().getImage(), 5, 255, 64, 64, null);
		healthMultiplier = (350 / player.getDefaultHealth());
		startingPos = 100;
		startingYPos = 275;
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 16));
		g.drawString(player.getType().getTypeName() + " " + player.getName(), startingPos - 3, startingYPos - 7);
		g.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
		g.drawString("Lvl " + player.getXPLevel() + ", " + player.getExp() + " EXP", (startingPos - 3) + 50 + g.getFontMetrics().stringWidth(player.getType().getTypeName() + " " + player.getName()), startingYPos - 7);
		g.setFont(defaultFont);
		
		g.setColor(Color.GRAY);
		g.fillRect(startingPos - 3, startingYPos - 3, 356 + g.getFontMetrics().stringWidth(player.getHealth() + "/" + player.getDefaultHealth()), 16);
		g.setColor(Color.BLACK);
		g.drawString(player.getHealth() + "/" + player.getDefaultHealth(), 446, startingYPos + 9);
		
		//Draw health bar
		for(int health = 0; health <= player.getDefaultHealth(); health++){
			int drawX = health * healthMultiplier + startingPos;
			if(player.getHealth() >= health) g.setColor(Color.GREEN);
			else g.setColor(Color.RED);
			g.fillRect(drawX, startingYPos, healthMultiplier, 10);
		}
		
		g.setColor(Color.BLACK);
		g.fillRoundRect(startingPos, startingYPos + 25, 375, 125, 20, 20);
		g.setColor(Color.GRAY);
		g.fillRoundRect(startingPos + 5, startingYPos + 30, 365, 115, 20, 20);
		g.setColor(Color.WHITE);
		g.setFont(new Font(Font.DIALOG, Font.BOLD, 14));
		if(false && !battle.isPlayerTurn()) g.drawString("Opponent's turn", startingPos + 20, startingYPos + 50);
		else{
			int xPos = startingPos + 5;
			int yPos = startingYPos + 30;
			for(BattleMove move : Controller.moves){
				if(move == player.getSelectedMove()){
					g.setColor(Color.LIGHT_GRAY);
					g.fillRect((xPos + move.xOffset) - 3, (yPos + move.yOffset - g.getFontMetrics().getHeight()) + 3, (g.getFontMetrics().stringWidth(move.getName())) + 3, (g.getFontMetrics().getHeight()) + 3);
				}
				g.setColor(Color.WHITE);
				g.drawString(move.getName(), xPos + move.xOffset, yPos + move.yOffset);
			}
		}
		
	}
	
	private void loadInventory(){
		g.setColor(new Color(97, 97, 97, 200));
		int xPos = 20;//(getWindowWidth()/2)-150;
		int width = (window.getWidth()) - (xPos*2);
		int yPos = HUDHeight + 20;
		g.fillRect(xPos, yPos, width, window.getHeight()- HUDHeight - 40);
		Item selectedItem = player.getInventory().getSelectedItem().getItem();
		for(Entry<Integer, InventorySlot> entry : player.getInventory().getSlots().entrySet()){
			if(yPos >= (7 * 48) + HUDHeight){
				yPos = HUDHeight + 20;
				xPos += 48;
			}
			if(entry.getValue() == null) continue;
			Item item = entry.getValue().getItem();
			int amount = entry.getValue().getQuantity();
			if(selectedItem == item){
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(xPos, yPos, 48, 48);
			}
			g.drawImage(item.getImage().getImage(), xPos + 10, yPos, item.getImage().getImage().getWidth() * 2, item.getImage().getImage().getHeight() * 2, null);
			if(amount > 1) g.drawString("" + amount, xPos + 30, yPos + 12);
			yPos += 48;
		}
		g.setColor(Color.DARK_GRAY);
		int nameplateWidth = 200, nameplateHeight = 40;
		g.fillRect(window.getWidth()/2 - nameplateWidth/2, window.getHeight() - 70, nameplateWidth, nameplateHeight);
		g.setColor(Color.WHITE);
		g.drawString(selectedItem.getName(), (window.getWidth() - g.getFontMetrics().stringWidth(selectedItem.getName()))/2, (window.getHeight() - 70) + (70/2 + g.getFontMetrics().getHeight())/2);
	}

	private static void loadBottomHUD(){
		g.setColor(new Color(44, 44, 44, 100));
		g.fillRect(0, window.getHeight() - HUD_BOTTOM_Height, getWindowWidth(), HUD_BOTTOM_Height);
		g.setColor(Color.WHITE);
		if(!BHUD_TARGET.didLose()) g.drawString(BHUD_TARGET.getName() + " : " + BHUD_TARGET.getPhrase(), 10,(window.getHeight() - HUD_BOTTOM_Height) + 20 );
		else g.drawString(BHUD_TARGET.getName() + " : " + BHUD_TARGET.getLosingPhrase(), 10,(window.getHeight() - HUD_BOTTOM_Height) + 20 );
	}
	
	private void loadHUD(){
		g.setColor(new Color(44, 44, 44, 100));
		g.fillRect(0, 0, window.getWidth(), HUDHeight);
		int healthX = 3;
		int healthY = 3;
		for(int i = 0; i < player.getHealth(); i++){
			if(i%2==0){
				g.drawImage(GameImage.HEART_LEFT.getImage(), healthX, healthY, GameImage.HEART_LEFT.getImage().getWidth(), GameImage.HEART_LEFT.getImage().getHeight(), null);
				healthX += 8;
			}
			else {
				g.drawImage(GameImage.HEART_RIGHT.getImage(), healthX, healthY, GameImage.HEART_RIGHT.getImage().getWidth(), GameImage.HEART_RIGHT.getImage().getHeight(), null);
				healthX += 10;
			}
		}
		g.setColor(Color.WHITE);
		g.drawImage(GameImage.MONEY.getImage(), 3, 20, GameImage.MONEY.getImage().getWidth(), GameImage.MONEY.getImage().getHeight(), null);
		g.drawString(Controller.getStringMoney(), 22, 33);
		
		int startingX = 275;
		int startingY = 15;
		g.drawString("Selected", startingX, startingY);
		BufferedImage selectedImg = player.getInventory().getSelectedItem().getItem().getImage().getImage();
		g.drawImage(selectedImg, startingX + selectedImg.getWidth(), startingY + 5, selectedImg.getWidth(), selectedImg.getHeight(), null);
		if(player.getInventory().getSelectedItem().getQuantity() > 1) {
			g.setColor(Color.WHITE);
			g.drawString(player.getInventory().getSelectedItem().getQuantity() + " ", startingX + selectedImg.getWidth() + 10, startingY + 8 + selectedImg.getHeight());
		}
		
		g.drawString("Level " + player.getXPLevel() + " (" + player.getExp() + " EXP)", 100, 33);
		
		g.drawString("STR: " + player.getStrength(), 350, 10);
		g.drawString("DEX: " + player.getDexterity(), 350, 25);
		g.drawString("DEF: " + player.getDefense(), 350, 40);
		
	}
	
	private void reset(){
		player.reset();
		Controller.reset();
	}
	
	private void init(){
		createBufferStrategy(3);
		buffer = getBufferStrategy();
		g = buffer.getDrawGraphics();
		
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFF222222;
		
		defaultFont = g.getFont();
		
		reset();
	}
	
	public void run(){
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double nanoSeconds = 1000000000.0 / _DESIREDTPS;
		double delta = 0;
		int frames = 0, ticks = 0;
		requestFocus();
		while(isRunning){
			long now = System.nanoTime();
			delta+= (now - lastTime) / nanoSeconds;
			lastTime = now;
			while(delta >= 1){
				tick();
				ticks++;
				delta--;
			}
			render();
			frames++;
			if((System.currentTimeMillis() - timer) > 1000){
				timer += 1000;
				window.setTitle(title + " (" + ticks + " TPS, " + frames + "FPS)");
				ticks = 0;
				frames = 0;
			}
		}
		stop();
	}
	
	public GBJam(){
		Dimension wSize = new Dimension(_WIDTH * _SCALE, _HEIGHT * _SCALE);
		setPreferredSize(wSize);
		window = new JFrame();
		screen = new Screen(_WIDTH, _HEIGHT);
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		player = new Player(keyboard, this);
		player.reset();
		player.setLevel(level);
	}
	
	public static void main(String[] args){
		GBJam game = new GBJam();
		
		window.add(game);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setTitle(title);
		window.setVisible(true);
		
		game.start();
	}

	private void start(){
		isRunning = true;
		init();
		mainThread = new Thread(this, "MainThread");
		mainThread.start();
	}
	
	private void stop(){
		try {mainThread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}

	/**
	 * @author joeb3219 <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @return Returns the correct height of the window.
	 */
	public static int getWindowHeight(){
		return _HEIGHT * _SCALE;
	}
	
	/**
	 * @author joeb3219 <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @return Returns the correct Width of the window.
	 */
	public static int getWindowWidth(){
		return _WIDTH * _SCALE;
	}

	public static void setHUDMob(Mob mob){
		BHUD_TARGET = mob;
	}
	
	public static void setGameState(GameState state){
		gameState = state;
	}
	
}