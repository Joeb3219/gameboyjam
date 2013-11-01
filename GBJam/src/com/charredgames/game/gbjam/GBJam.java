package com.charredgames.game.gbjam;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

public class GBJam extends Canvas implements Runnable{

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
	
	private void tick(){
		
	}
	
	private void render(){
		
		g.drawImage(img, 0, 0, window.getWidth(), window.getHeight(), null);
		
		buffer.show();
	}
	
	private void init(){
		createBufferStrategy(3);
		buffer = getBufferStrategy();
		g = buffer.getDrawGraphics();
		
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFF222222;
	}
	
	public void run(){
		while(isRunning) render();
		stop();
	}
	
	public GBJam(){
		Dimension wSize = new Dimension(_WIDTH * _SCALE, _HEIGHT * _SCALE);
		setPreferredSize(wSize);
		window = new JFrame();
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
	
}
