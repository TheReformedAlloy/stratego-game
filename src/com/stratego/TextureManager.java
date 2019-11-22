package com.stratego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.io.IOException;
import java.util.HashMap;

public class TextureManager {
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	private HashMap<String, Color> colors = new HashMap<String, Color>();
	private HashMap<String, Border> borders = new HashMap<String, Border>();
	
	//instance will be used to create an TextureManager.
	private static TextureManager instance = null;
	
	//getInstance() can be accessed outside of this class to get the current instance of the game.
	public static TextureManager getInstance() {
		if(instance == null) {
			instance = new TextureManager();
		}
		
		return instance;
	}
	
	private TextureManager() {
		images.put("gray piece", processImage("assets/tiles/grayPiece.png"));
		images.put("button", processImage("assets/gui/Button.png"));
		images.put("button hover", processImage("assets/gui/ButtonHover.png"));
		images.put("button press", processImage("assets/gui/ButtonPressed.png"));
		images.put("background", processImage("assets/gui/Background.png"));
		images.put("board", processImage("assets/gui/Game Board.png"));

		colors.put("bg base", new Color(147, 98, 86));
		colors.put("font base", new Color(70, 51, 51));
		colors.put("font high", new Color(95, 76, 76));
		colors.put("font low", new Color(45, 26, 26));
		colors.put("text base", new Color(246, 214, 164));
		colors.put("text high", new Color(246, 230, 205));
		colors.put("text low", new Color(182, 150, 100));
		colors.put("text select", new Color(246, 230, 226));
		colors.put("border base", new Color(125, 86, 61));
		colors.put("border high", new Color(157, 118, 93));
		colors.put("border low", new Color(93, 54, 29));
		colors.put("border vhigh", new Color(157, 118, 93));
		
		//borders.put
		Border textBevel = BorderFactory.createBevelBorder(BevelBorder.RAISED, getColor("border vhigh"), getColor("border high"));
		Border textBorder = BorderFactory.createLineBorder(getColor("border high"), 10);
		Border textBevelBorder = BorderFactory.createCompoundBorder(textBevel, textBorder);
		borders.put("text", textBevelBorder);
		Border textDubBevelBorder = BorderFactory.createCompoundBorder(textBevelBorder, textBevel);
		borders.put("textdb", textDubBevelBorder);
		
		Border boardBevel = BorderFactory.createBevelBorder(BevelBorder.RAISED, getColor("border high"), getColor("border base"));
		Border boardBorder = BorderFactory.createLineBorder(getColor("border base"), 10);
		Border boardBevelBorder = BorderFactory.createCompoundBorder(boardBevel, boardBorder);
		borders.put("board", boardBevelBorder);
		Border boardDubBevelBorder = BorderFactory.createCompoundBorder(boardBevelBorder, boardBevel);
		borders.put("boarddb", boardDubBevelBorder);
	}
	
	public BufferedImage getImage(String key) {
		return images.get(key);
	}
	
	public Color getColor(String key) {
		return colors.get(key);
	}
	
	public Border getBorder(String key) {
		return borders.get(key);
	}
	
	private static BufferedImage processImage(String path) {
		try {
			return ImageIO.read(TextureManager.class.getResource(path));
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage returnGrayPiece() {
		try {
			return ImageIO.read(TextureManager.class.getResource("assets/tiles/grayPiece.png"));
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
