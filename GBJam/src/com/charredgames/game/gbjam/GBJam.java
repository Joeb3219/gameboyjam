package com.charredgames.game.gbjam;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JFrame;

import com.charredgames.game.gbjam.entity.Chest;
import com.charredgames.game.gbjam.entity.Mob;
import com.charredgames.game.gbjam.entity.Player;
import com.charredgames.game.gbjam.graphics.GameImage;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.inventory.Inventory;
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
	public static final int _DESIREDTPS = 60;
	public static String title = "GBJam";
	
	private static JFrame window;
	private Graphics g;
	private BufferStrategy buffer;
	private Thread mainThread;
	private boolean isRunning = false;
	private BufferedImage img = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	@SuppressWarnings("unused")
	private Random rand = new Random();
	
	private Screen screen;
	private Keyboard keyboard;
	private Player player;
	@SuppressWarnings("unused")
	private Mob mob = Mob.testing;
	private Level level = Level.spawnLevel;
	private int HUDHeight = 40;
	private GameState gameState = GameState.GAME;
	
	private void tick(){
		Controller.update();
		keyboard.update();
		if(gameState == GameState.GAME){
			player.update();
			Controller.updateMobs();
		}
		if(keyboard.start && (Controller.tickCount%8 == 0)){
			if(gameState == GameState.INVENTORY) gameState = GameState.GAME;
			else gameState = GameState.INVENTORY;
		}
	}
	
	private void render(){
		
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
		for(Chest chest : level.getChests()) chest.render(screen);
		Controller.renderMobs(screen);
		player.render(screen);
		
		for(int i = 0; i < pixels.length; i ++) pixels[i] = screen.pixels[i];
		
		g.drawImage(img, 0, 0, getWindowWidth(), getWindowHeight(), null);
		
		loadHUD();
		
		if(gameState == GameState.INVENTORY) loadInventory();
		
		buffer.show();
	}
	
	private void loadInventory(){
		if(keyboard.down || keyboard.up) player.getInventory().getNextItem();
		g.setColor(Color.ORANGE);
		int xPos = (getWindowWidth()/2)-150;
		int width = (getWindowWidth()) - (xPos*2);
		int yPos = 100;
		g.fillRect(xPos, yPos, width, getWindowHeight()-100);
		for(Entry<Integer, InventorySlot> entry : player.getInventory().getSlots().entrySet()){
			if(entry.getValue() == null) continue;
			Item item = entry.getValue().getItem();
			int amount = entry.getValue().getQuantity();
			yPos += 20;
			g.setColor(Color.WHITE);
			g.drawImage(item.getImage().getImage(), xPos + 10, yPos, item.getImage().getImage().getWidth(), item.getImage().getImage().getHeight(), null);
			if(amount > 1) g.drawString(item.getName() + ": " + amount, xPos + 30, yPos + 12);
			else g.drawString(item.getName(), xPos + 30, yPos + 12);
			g.setColor(Color.BLACK);
			if(item == player.getInventory().getSelectedItem().getItem()) g.fillRect((xPos + width) - 180, yPos, 16, 16);
		}
		if(keyboard.a){
			if(player.getInventory().getSelectedItem().getItem() == Item.APPLE){
				player.heal(Item.APPLE.getValue());
				player.getInventory().removeItem(Item.APPLE, 1);
			}
			if(player.getInventory().getSelectedItem().getItem() == Item.SWORD){
				
			}
		}
	}

	private void loadHUD(){
		g.setColor(new Color(44, 44, 44, 100));
		g.fillRect(0, 0, getWindowWidth(), HUDHeight);
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
		player = new Player(keyboard);
		player.reset();
		player.setLevel(level);
		Inventory chest = new Inventory();
		chest.addItem(Item.APPLE, 10);
		level.addChest(new Chest(level, 20, 20, 5, chest));
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

	
}