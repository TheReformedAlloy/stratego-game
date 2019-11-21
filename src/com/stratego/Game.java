package com.stratego;

public class Game {
	
	Player player1;
	Player player2;
	Board board;
	
	int whoseTurn = 1;

	boolean win = false;
	
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
	
	public boolean getWinStatus() {
		return win;
	}
	
	public void shuffle() {
		Player playerRef = getCurrentPlayer();
		playerRef.setUnplacedToInit();
		if(whoseTurn == 1) {
			for(int i = 9; i > 5; i--) {
				for(int j = 0; j < 10; j++) {
					Piece randPiece = playerRef.getRandomNewPiece();
					playerRef.subUnplacedPiece(randPiece.getRank());
					board.setLocation(j, i, randPiece);
				}
			}
		} else {
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 10; j++) {
					Piece randPiece = playerRef.getRandomNewPiece();
					playerRef.subUnplacedPiece(randPiece.getRank());
					board.setLocation(j, i, randPiece);
				}
			}
		}
	}
	
	public Piece checkEncounter(Piece atkPiece, Piece defPiece)
	{
		if(atkPiece.rank.equals("spy") && defPiece.getRankValue() == 10)
		{
			return atkPiece;
		}
		else if(atkPiece.rank.equals("miner") && defPiece.getRankValue() == 11) 
		{
			return atkPiece;
		}
		else if(defPiece.getRankValue() == 0) 
		{
			win = true;
			return atkPiece;
		}
		else if(atkPiece.getRankValue() > defPiece.getRankValue())
		{
			return atkPiece;
		}
		else if(atkPiece.getRankValue() == defPiece.getRankValue()) {
			return null;
		}
		else 
		{
			return defPiece;
		}
	}
	
	public void switchTurn() {
		whoseTurn = whoseTurn == 2 ? 1 : 2;
	}

	public int getWhoseTurn() {
		return whoseTurn;
	}

	public Player getCurrentPlayer() {
		return getPlayer(whoseTurn);
	}
}
