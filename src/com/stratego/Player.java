package com.stratego;

import java.awt.*;
import java.awt.image.*;

public class Player {
	Color pieceColor;
	String playerName;
	BufferedImage basePieceImage;
	
	Player(Color pieceColor, String playerName, BufferedImage basePieceImage) {
		this.pieceColor = pieceColor;
		this.playerName = playerName;
		this.basePieceImage = basePieceImage;
	}
}
