 package com.stratego;

import java.awt.*;
import java.awt.image.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

public class Player {
	Color pieceColor;
	String playerName;
	int turnOrder;
	BufferedImage basePieceImage;
	BufferedImage playerPieceImage;
	HashMap<String, BufferedImage> pieceImages = new HashMap<String, BufferedImage>();
	HashMap<String, Integer> unplacedPieces = new HashMap<String, Integer>();
	
	Player(Color pieceColor, String playerName, int turnOrder, BufferedImage basePieceImage) {
		this.pieceColor = pieceColor;
		this.playerName = playerName;
		this.turnOrder = turnOrder;
		this.basePieceImage = basePieceImage;
		this.playerPieceImage = combineImage("crown");
		
		setUnplacedToInit();
		
		for(String rank : Piece.ranks.keySet()) {
			pieceImages.put(rank, combineImage(rank));
		}
		
	}
	
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
	
	public BufferedImage getImage(String rank) {
		if(rank.equals("blank")) {
			return basePieceImage;
		} else if(rank.equals("player")) {
			return playerPieceImage;
		} else {
			return pieceImages.get(rank);
		}
	}
	
	public int getNumberOfRank(String rank) {
		return unplacedPieces.get(rank);
	}
	
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
	
	public String getPlayerName() {
		return playerName;
	}
}
