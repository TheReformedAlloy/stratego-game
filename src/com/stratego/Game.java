package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class Game {
	
	Player player1;
	Player player2;
	Board board;
	
	Game(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		this.board = new Board();
	}

	public Board getBoard() {
		return board;
	}

	public Player getPlayer(int playerNumber) {
		if(playerNumber == 1) {
			return player1;
		} else if(playerNumber == 2) {
			return player2;
		} else {
			return null;
		}
	}
}
