package com.stratego;

/**
 * An model used to hold two <code>Player</code>s and a <code>Board</code>, representing and managing
 * all relevant data used in the game.
 * 
 * @author Clint Mooney
 *
 */
public class Game {
	
	/** Represents the player who goes first in the game.*/
	Player player1;
	/** Represents the player who goes second in the game.*/
	Player player2;
	/** Represents the game board which contains all pieces placed during the game.*/
	Board board;

	/** Represents the current player who is able to make a move or set up pieces.*/
	int whoseTurn = 1;
	
	/** Indicates whether a person has won the game.*/
	boolean win = false;
	
	/**
	 * Creates a game with the two provided <code>Player</code>s and generates an empty <code>Board</code>.
	 * 
	 * @param player1 {@link Game#player1}
	 * @param player2 {@link Game#player2}
	 */
	Game(Player player1, Player player2){
		this.player1 = player1;
		this.player2 = player2;
		this.board = new Board();
	}

	/**
	 * Returns <code>board</code>
	 * 
	 * @return {@link Game#board}
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * Returns the <code>Player</code> specified. A value of <code>1</code> returns <code>player1</code>. A value of <code>2</code>
	 * returns <code>player2</code>. Otherwise, this returns <code>null</code>.
	 * 
	 * @param playerNumber specifies which <code>Player</code> to return.
	 * @return the <code>Player</code> specified by <code>playerNumber</code>.
	 */
	public Player getPlayer(int playerNumber) {
		if(playerNumber == 1) {
			return player1;
		} else if(playerNumber == 2) {
			return player2;
		} else {
			return null;
		}
	}
	
	/**
	 * Indicates whether a person has won the game.
	 * 
	 * @return {@link Game#win}.
	 */
	public boolean getWinStatus() {
		return win;
	}
	
	/**
	 * Places pieces on the <code>board</code> in a random manner for the <code>Player</code> specified
	 * by <code>whoseTurn</code>.
	 */
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
	
	/**
	 * Compares two pieces and returns the winning piece. For the majority of pieces, this is a simple comparison of 
	 * rank value. However, there are some exceptions. When the two are equal in rank value, a value of <code>null</code>
	 * is returned. In the case that the defending piece is a bomb, it wins unless the opposing piece is a miner. In the
	 * case the defending piece is a flag, it returns the attacking piece and sets <code>win</code> to <code>true</code>.
	 * 
	 * @param atkPiece refers to the <code>Piece</code> of the <code>Player</code> that initiated the encounter.
	 * @param defPiece refers to the <code>Piece</code> of the <code>Player</code> that did not initiate the encounter.
	 * @return the winning <code>Piece</code> when two <code>Piece</code>s are compared.
	 */
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
	
	/**
	 * Toggles <code>whoseTurn</code> to either 1 or 2 to denote whose turn it currently is.
	 */
	public void switchTurn() {
		whoseTurn = whoseTurn == 2 ? 1 : 2;
	}

	/**
	 * Represents the current player who is able to make a move or set up pieces.
	 * 
	 * @return {@link Game#whoseTurn}
	 */
	public int getWhoseTurn() {
		return whoseTurn;
	}

	/**
	 * Gets the player whose turn it currently is.
	 * 
	 * @return the player whose turn corresponds to <code>whoseTurn</code>.
	 */
	public Player getCurrentPlayer() {
		return getPlayer(whoseTurn);
	}
}
