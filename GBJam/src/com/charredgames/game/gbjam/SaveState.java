package com.charredgames.game.gbjam;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * @author Joe Boyle <joe@charredgames.com>
 * @since Nov 7, 2013
 */
public class SaveState {

	private int id, size;
	private File file;
	
	public static SaveState STATE_1 = new SaveState(1);
	
	public SaveState(int id){
		this.id = id;
		File dir = new File(System.getProperty("user.dir") + "/resources/saves/");
		this.file = new File(dir, "save_" + id + ".cgs");
		System.out.println(System.getProperty("user.dir") + "/resources/saves/" + id + ".cgs");
		if(!file.isFile() || !file.exists()){
			try {
				boolean b = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void save(){
		PrintWriter writer;
		try {
			writer = new PrintWriter(file, "UTF-8");
			writer.println("The first line");
			writer.println("The second line");
			writer.close();
			System.out.println("SAVED");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
		}
	}
	
	public void load(){
		
	}
	
	public int getId(){
		return id;
	}
	
	public int getSize(){
		return size;
	}
	
}
