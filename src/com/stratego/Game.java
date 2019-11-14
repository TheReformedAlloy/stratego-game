package com.stratego;

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
	
	public void shuffle(int whoseTurnIsIt) {
		Player playerRef = getPlayer(whoseTurnIsIt);
		playerRef.setUnplacedToInit();
		int yStart = whoseTurnIsIt == 1 ? 6 : 0;
		
		for(int i = yStart; i < yStart + 4; i++) {
			for(int j = 0; j < 10; j++) {
				Piece randPiece = playerRef.getRandomNewPiece();
				playerRef.subUnplacedPiece(randPiece.getRank());
				board.setLocation(j, i, randPiece);
			}
		}
	}
}
