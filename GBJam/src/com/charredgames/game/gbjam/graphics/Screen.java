package com.charredgames.game.gbjam.graphics;

/**
 * @author joeb3219 <joe@charredgames.com>
 * @since Nov 3, 2013
 */
public class Screen {

	private static int height, width;
	public int xOffset, yOffset;
	public int tileSize = 16;
	public int[] pixels;
	private int[] tiles;
	
	public Screen(int w, int h){
		height = h;
		width = w;
		pixels = new int[height * width];
	}
	
	public void clear(){
		for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFF222222;
	}
	
	public void setOffset(int xOffset, int yOffset){
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
	
	public void renderTile(int xPos, int yPos, Sprite sprite){
		xPos -= xOffset;
		yPos -= yOffset;
		for(int y = 0; y < sprite.size; y++){
			int yAbsolute = y + yPos;
			for(int x = 0; x < sprite.size; x++){
				int xAbsolute = x + xPos;
				if(xAbsolute < -sprite.size || xAbsolute >= width || yAbsolute < 0 || yAbsolute >= height) break;
				if(xAbsolute < 0) xAbsolute = 0;
				int grabColour = sprite.pixels[x + y * sprite.size];
				if(grabColour != 0xFFFF5978) pixels[xAbsolute + yAbsolute * width] = grabColour;
			}
		}
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
}
