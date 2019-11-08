 package com.stratego;

import java.awt.*;
import java.awt.image.*;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

import javax.imageio.ImageIO;

public class Player {
	Color pieceColor;
	String playerName;
	BufferedImage basePieceImage;
	Map<String, BufferedImage> pieceImages = new HashMap<String, BufferedImage>();
	HashMap<String, Integer> unplacedPieces = new HashMap<String, Integer>();
	
	Player(Color pieceColor, String playerName, BufferedImage basePieceImage) {
		this.pieceColor = pieceColor;
		this.playerName = playerName;
		this.basePieceImage = basePieceImage;
		
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
		
		for(String rank : Piece.ranks.keySet()) {
			pieceImages.put(rank, combineImage(rank));
		}
		
	}
	
	private BufferedImage combineImage(String rank) {
		BufferedImage combinedIMG = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		Graphics g = combinedIMG.getGraphics();
		g.drawImage(basePieceImage, 0, 0, null);
		
		try {
			BufferedImage charIMG = ImageIO.read(new File("./src/com/stratego/assets/piece icons/" + rank + ".png"));
			g.drawImage(charIMG, 33, 35, 62, 75, null);
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return combinedIMG;
	}
	
	public BufferedImage getImage(String rank) {
		if(rank == "blank") {
			return basePieceImage;
		}else {
			return pieceImages.get(rank);
		}
	}
	
	public int getNumberOfRank(String rank) {
		return unplacedPieces.get(rank);
	}
	
	public void addUnplacedPiece(String rank) {
		int temp = unplacedPieces.get(rank);
		temp++;
		unplacedPieces.put(rank, temp);
	}
	
	public void subUnplacedPiece(String rank) {
		int temp = unplacedPieces.get(rank);
		temp--;
		unplacedPieces.put(rank, temp);
	}

	public String getPlayerName() {
		return playerName;
	}
}
