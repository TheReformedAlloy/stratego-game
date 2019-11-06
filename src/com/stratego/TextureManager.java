package com.stratego;

import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;

public class TextureManager {
	public static final BufferedImage BLUEPIECE = processImage("assets/Blue Piece.png");
	public static final BufferedImage REDPIECE = processImage("assets/Red Piece.png");
	public static final BufferedImage GRAYPIECE = processImage("assets/Gray Piece.png");
	public static final BufferedImage BUTTON = processImage("assets/Button.png");
	public static final BufferedImage BUTTON_HOVER = processImage("assets/ButtonHover.png");
	public static final BufferedImage BUTTON_PRESSED = processImage("assets/ButtonPressed.png");
	public static final BufferedImage BACKGROUND = processImage("assets/Background.png");
	public static final BufferedImage BOARD = processImage("assets/Game Board.png");
	
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
			return ImageIO.read(TextureManager.class.getResource("assets/Gray Piece.png"));
		}catch(IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
