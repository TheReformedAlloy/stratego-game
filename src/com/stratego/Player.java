 package com.stratego;

import java.awt.*;
import java.awt.image.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

/**
 * Contains a player's identifying information and information relevant to the number of remaining pieces of each type that a player
 * can still place down.
 * 
 * @author Jeremiah Allsford
 * @author Clint Mooney
 * @author James Ford
 */

public class Player {
	/** Stores the color chosen by the player to paint the corresponding pieces with (Unused).*/
	Color pieceColor;
	/** Records the player's name to be displayed when it is their turn.*/
	String playerName;
	/** Indicates when in the turn sequence that the player should play.*/
	int turnOrder;
	/** Stores the image created by the <code>GamepieceColorChooser</code> which is a hue-shifted version of the '<code>assets/gui/grayPiece.png</code>'.*/
	BufferedImage basePieceImage;
	/** Stores the image created which contains '<code>assets/piece icons/crown.png</code>' in the icon field representing a player.*/
	BufferedImage playerPieceImage;
	/** Stores an image representing each rank of piece, associated with the rank <code>String</code>.*/
	HashMap<String, BufferedImage> pieceImages = new HashMap<String, BufferedImage>();
	/** Stores the number of pieces remaining to be placed in setup by their rank, associated with the rank <code>String</code>*/
	HashMap<String, Integer> unplacedPieces = new HashMap<String, Integer>();
	
	/**
	 * Creates a Player.
	 * 
	 * @param pieceColor Stores the color chosen by the player to paint the corresponding pieces with (Unused). 
	 * @param playerName Records the player's name to be displayed when it is their turn.
	 * @param turnOrder Indicates when in the turn sequence that the player should play.
	 * @param basePieceImage Stores the image created by the <code>GamepieceColorChooser</code> which is a hue-shifted version of the '<code>assets/gui/grayPiece.png</code>'.
	 */
	Player(Color pieceColor, String playerName, int turnOrder, BufferedImage basePieceImage) {
		this.pieceColor = pieceColor;
		this.playerName = playerName;
		this.turnOrder = turnOrder;
		this.basePieceImage = basePieceImage;
		this.playerPieceImage = combineImage("crown");
		
		setUnplacedToInit();
		
		for(String rank : Piece.ranks) {
			pieceImages.put(rank, combineImage(rank));
		}
		
	}
	
	/**
	 * Draws a second image over the white field of <code>basePieceImage</code>.
	 * 
	 * @param imgName the name of the second image to be drawn within the '<code>piece icons</code> folder.
	 * @return a new <code>BufferedImage</code> consisting of <code>basePieceImage</code> with a second image placed in its center.
	 */
	private BufferedImage combineImage(String imgName) {
		BufferedImage combinedIMG = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		Graphics g = combinedIMG.getGraphics();
		g.drawImage(basePieceImage, 0, 0, null);
		
		try {
			BufferedImage charIMG = ImageIO.read(Player.class.getResource("assets/piece icons/" + imgName + ".png"));
			g.drawImage(charIMG, 33, 35, 62, 75, null);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return combinedIMG;
	}
	
	/**
	 * Returns the <code>BufferedImage</code> associated with the inputted <code>String</code> in <code>pieceImages</code>.
	 * 
	 * @param rank the rank <code>String</code> that the image should be associated with.
	 * @return the rank <code>BufferedImage</code> that the <code>String</code> should be associated with.
	 */
	public BufferedImage getImage(String rank) {
		if(rank.equals("blank")) {
			return basePieceImage;
		} else if(rank.equals("player")) {
			return playerPieceImage;
		} else {
			return pieceImages.get(rank);
		}
	}
	
	/**
	 * Gets the remaining number of pieces that can be placed for a certain rank.
	 * 
	 * @param rank the <code>String</code> that is a key in <code>unplacedPieces</code>.
	 * @return the remaining number of pieces that can be placed for a certain rank.
	 */
	public int getNumberOfRank(String rank) {
		return unplacedPieces.get(rank);
	}
	
	/**
	 * Sets each rank's unplaced pieces to its initial value according to the rules.
	 */
	public void setUnplacedToInit() {
		unplacedPieces.put("bomb", 6);
		unplacedPieces.put("flag", 1);
		unplacedPieces.put("spy", 1);
		unplacedPieces.put("scout", 8);
		unplacedPieces.put("miner", 5);
		unplacedPieces.put("sergeant", 4);
		unplacedPieces.put("lieutenant", 4);
		unplacedPieces.put("captain", 4);
		unplacedPieces.put("major", 3);
		unplacedPieces.put("colonel", 2);
		unplacedPieces.put("general", 1);
		unplacedPieces.put("marshal", 1);
	}
	
	/**
	 * Increments the number of unplaced pieces for a certain rank.
	 * 
	 * @param rank name of the rank to use as a key for <code>unplacedPieces</code>.
	 */
	public void addUnplacedPiece(String rank) {
		int temp = unplacedPieces.get(rank);
		temp++;
		unplacedPieces.put(rank, temp);
	}
	
	/**
	 * Decrements the number of unplaced pieces for a certain rank.
	 * 
	 * @param rank name of the rank to use as a key for <code>unplacedPieces</code>.
	 */
	public void subUnplacedPiece(String rank) {
		int temp = unplacedPieces.get(rank);
		temp--;
		unplacedPieces.put(rank, temp);
	}
	
	/**
	 * Gets a random unplaced piece to set for a shuffling algorithm.
	 * 
	 * @return a random piece that can still be placed.
	 */
	public Piece getRandomNewPiece() {
		Random rand = new Random();
		Object[] unplaced = unplacedPieces.keySet().toArray();
		int randNo;
		String generatedRank;
		
		do {
			randNo = rand.nextInt(unplaced.length);
			generatedRank = (String) unplaced[randNo];
		}
		while(unplacedPieces.get(generatedRank) == 0);
		
		return new Piece(turnOrder, generatedRank);
	}
	
	/**
	 * Gets the player's name.
	 * 
	 * @return Records the player's name to be displayed when it is their turn.
	 */
	public String getPlayerName() {
		return playerName;
	}
}
