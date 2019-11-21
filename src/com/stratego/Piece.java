package com.stratego;

public class Piece {
	public static String[] ranks = 	{
			"flag", "spy",
			"scout", "miner",
			"sergeant", "lieutenant",
			"captain", "major",
			"colonel", "general",
			"marshal", "bomb"};
	
	private static int currentId = 0;
	
	private static int getNewId() {
		return currentId++;
	}
	
	int owner;
	int id;
	String rank;
	
	Piece(int owner, String rank) {
		this.owner = owner;
		this.rank = rank;
		this.id = getNewId();
	}
	
	public String getRank() {
		return rank;
	}
	
	public int getRankValue()
	{
		return java.util.Arrays.asList(ranks).indexOf(rank);
	}
	
	public int getOwner() {
		return owner;
	}

	public int getId() {
		return id;
	}
}
