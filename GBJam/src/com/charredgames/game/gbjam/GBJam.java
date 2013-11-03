package com.charredgames.game.gbjam;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;

import javax.swing.JFrame;

import com.charredgames.game.gbjam.graphics.GameImage;
import com.charredgames.game.gbjam.graphics.Screen;
import com.charredgames.game.gbjam.level.Level;
import com.charredgames.game.gbjam.mob.Mob;
import com.charredgames.game.gbjam.mob.Player;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @version 1.0.0
 * @since Nov 3, 2013
 */
/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 3, 2013
 */
/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class GBJam extends Canvas implements Runnable{

	public static final int _WIDTH = 160;
	public static final int _HEIGHT = 144;
	public static final int _SCALE = 2;
	public static final int _DESIREDTPS = 60;
	public static String title = "GBJam";
	
	private static JFrame window;
	private Graphics g;
	private BufferStrategy buffer;
	private Thread mainThread;
	private boolean isRunning = false;
	private BufferedImage img = new BufferedImage(_WIDTH, _HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	private Random rand = new Random();
	
	private Screen screen;
	private Keyboard keyboard;
	private Player player;
	private Mob mob = Mob.testing;
	private Level level = Level.spawnLevel;
	private int HUDHeight = 60;
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Stuff that shouldn't depend on FPS -> updates.
	 */
	private void tick(){
		keyboard.update();
		player.update();
		Controller.updateMobs();
		
		//new Mob(rand.nextInt(_WIDTH), rand.nextInt(_HEIGHT), 0, Sprite.mob);
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Called every frame. Draws everything to the screen.
	 */
	private void render(){
		
		screen.clear();
		
		int xOffset = (player.getX()) - (_WIDTH/2);
		int yOffset = (player.getY()) - (_HEIGHT/2);
		
		level.render(xOffset, yOffset, screen);
		
		Controller.renderMobs(screen);
		player.render(screen);
		
		for(int i = 0; i < pixels.length; i ++) pixels[i] = screen.pixels[i];
		
		g.drawImage(img, 0, HUDHeight, getWindowWidth(), getWindowHeight() - HUDHeight, null);

		loadHUD();
		
		buffer.show();
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Method to clean up the render() method. Draws all of the HUD aspects.
	 */
	private void loadHUD(){
		g.setColor(Color.GRAY);
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
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Creates the buffer strategy && inits different variables.
	 */
	private void init(){
		createBufferStrategy(3);
		buffer = getBufferStrategy();
		g = buffer.getDrawGraphics();
		
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFF222222;
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Implements Runnable.
	 */
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
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Creates the window, keyboard, etc. Sets up the game for launch.
	 */
	public GBJam(){
		Dimension wSize = new Dimension(_WIDTH * _SCALE, _HEIGHT * _SCALE);
		setPreferredSize(wSize);
		window = new JFrame();
		screen = new Screen(_WIDTH, _HEIGHT);
		keyboard = new Keyboard();
		addKeyListener(keyboard);
		player = new Player(keyboard);
		player.reset();
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

	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Called when the game starts; Creates the thread.
	 */
	private void start(){
		isRunning = true;
		init();
		mainThread = new Thread(this, "MainThread");
		mainThread.start();
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @description Called when the game stops.
	 */
	private void stop(){
		try {mainThread.join();} catch (InterruptedException e) {e.printStackTrace();}
	}

	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @return Returns the correct height of the window.
	 */
	public static int getWindowHeight(){
		return _HEIGHT * _SCALE;
	}
	
	/**
	 * @author Joe Boyle <joe@charredgames.com>
	 * @since Nov 3, 2013
	 * @return Returns the correct Width of the window.
	 */
	public static int getWindowWidth(){
		return _WIDTH * _SCALE;
	}

	
}