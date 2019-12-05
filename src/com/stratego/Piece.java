package com.stratego;

/**
 * Piece classes contain information concerning a game piece's rank, unique ID, and owner Player.
 * 
 * @author Rebecca Whisnant
 * @author Clint Mooney
 */

public class Piece {
	/**
	 * Contains the ranks of different pieces sorted by value.
	 */
	public static String[] ranks = 	{
			"flag", "spy",
			"scout", "miner",
			"sergeant", "lieutenant",
			"captain", "major",
			"colonel", "general",
			"marshal", "bomb"};
	
	/** Contains the next number to be assigned when <code>getNewId</code> is called.*/
	private static int currentId = 0;
	
	/**
	 * Returns a unique identifying number to be assigned to a <code>Piece</code> instance.
	 * 
	 * @return <code>currentId</code> to be assigned.
	 */
	private static int getNewId() {
		return currentId++;
	}
	
	/** Represents which player owns the piece.*/
	int owner;
	/** Provides a unique identifier for a piece.*/
	int id;
	/** Identifies which type of piece the <code>Piece</code> object represents.*/
	String rank;
	
	/**
	 * Creates a Piece object and assigns <code>owner</code> and <code>rank</code> to their corresponding attributes.
	 * 
	 * @param owner a number representing which player owns the piece.
	 * @param rank a <code>String</code> identifying the type of piece.
	 */
	Piece(int owner, String rank) {
		this.owner = owner;
		this.rank = rank;
		this.id = getNewId();
	}
	
	/**
	 * Returns <code>rank</code>.
	 * 
	 * @return <code>rank</code>.
	 */
	public String getRank() {
		return rank;
	}
	
	/**
	 * Returns the index of <code>rank</code> in <code>ranks</code>.
	 * 
	 * @return index of <code>rank</code> in <code>ranks</code>.
	 */
	public int getRankValue()
	{
		return java.util.Arrays.asList(ranks).indexOf(rank);
	}
	
	/**
	 * Returns <code>owner</code>.
	 * 
	 * @return <code>owner</code>.
	 */
	public int getOwner() {
		return owner;
	}

	/**
	 * Returns <code>id</code>.
	 * 
	 * @return <code>id</code>.
	 */
	public int getId() {
		return id;
	}
}
