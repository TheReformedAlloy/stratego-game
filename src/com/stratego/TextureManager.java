package com.stratego;

import java.awt.Color;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.io.IOException;
import java.util.HashMap;


/**
 * TextureManager is a singleton class which loads and stores the images, colors, and borders to be used in the
 * game. There can only be one instance of the class in order to avoid repeated loading of images, which would be
 * a memory-heavy process.
 * 
 * @author Clint Mooney
 *
 */

public class TextureManager {
	/** Contains each image to be used, associated with a key <code>String</code>.*/
	private HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	/** Contains each <code>Color</code> to be used, associated with a key <code>String</code>.*/
	private HashMap<String, Color> colors = new HashMap<String, Color>();
	/** Contains each <code>Border</code> to be used, associated with a key <code>String</code>.*/
	private HashMap<String, Border> borders = new HashMap<String, Border>();
	
	/** Stores the current instance of the <code>TextureManager</code> class*/
	private static TextureManager instance = null;
	
	/** Ensures that the <code>TextureManager</code> has only one instance by initializing only if <code>instance</code>
	 * currently is <code>null</code>.
	 * 
	 * @return <code>instance</code>.
	 */
	public static TextureManager getInstance() {
		if(instance == null) {
			instance = new TextureManager();
		}
		
		return instance;
	}
	
	/**
	 * Creates a <code>TextureManager</code> and puts each image, <code>Color</code>, and <code>Border</code> into
	 * the respective <code>HashMap</code>.
	 */
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
	
	/**
	 * Gets the image associated with the <code>key</code> provided.
	 * 
	 * @param key the <code>String</code> that is associated with a <code>BufferedImage</code> in <code>images</code>.
	 * @return the <code>BufferedImage</code> associated with <code>key</code>
	 */
	public BufferedImage getImage(String key) {
		return images.get(key);
	}
	
	/**
	 * Gets the <code>Color</code> associated with the <code>key</code> provided.
	 * 
	 * @param key the <code>String</code> that is associated with a <code>Color</code> in <code>colors</code>.
	 * @return the <code>Color</code> associated with <code>key</code>
	 */
	public Color getColor(String key) {
		return colors.get(key);
	}
	
	/**
	 * Gets the <code>Border</code> associated with the <code>key</code> provided.
	 * 
	 * @param key the <code>String</code> that is associated with a <code>Border</code> in <code>borders</code>.
	 * @return the <code>Border</code> associated with <code>key</code>
	 */
	public Border getBorder(String key) {
		return borders.get(key);
	}
	
	/**
	 * Loads an image into a <code>BufferedImage</code> object.
	 * 
	 * @param path denotes the location of the image specified.
	 * @return the object representing the image at <code>path</code>.
	 */
	private static BufferedImage processImage(String path) {
		try {
			return ImageIO.read(TextureManager.class.getResource(path));
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Returns a new instance of the Gray Piece image for use in color modification.
	 * 
	 * @return a <code>BufferedImage</code> code representing <code>'assets/tiles/grayPiece.png'</code>.
	 */
	public static BufferedImage returnGrayPiece() {
		try {
			return ImageIO.read(TextureManager.class.getResource("assets/tiles/grayPiece.png"));
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
