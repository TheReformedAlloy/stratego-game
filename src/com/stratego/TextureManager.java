package com.stratego;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

public class TextureManager {
	public static final BufferedImage GRAYPIECE = processImage("assets/tiles/grayPiece.png");
	public static final BufferedImage BUTTON = processImage("assets/gui/Button.png");
	public static final BufferedImage BUTTON_HOVER = processImage("assets/gui/ButtonHover.png");
	public static final BufferedImage BUTTON_PRESSED = processImage("assets/gui/ButtonPressed.png");
	public static final BufferedImage BACKGROUND = processImage("assets/gui/Background.png");
	public static final BufferedImage BOARD = processImage("assets/gui/Game Board.png");
	
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
