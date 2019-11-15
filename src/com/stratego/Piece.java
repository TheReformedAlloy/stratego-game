package com.stratego;

import java.util.Map;
import static java.util.Map.entry;

public class Piece {
	public static Map<String, Integer> ranks = Map.ofEntries(
			entry("flag", 0),
			entry("spy", 1),
			entry("scout", 2),
			entry("miner", 3),
			entry("sergeant", 4),
			entry("lieutenant", 5),
			entry("captain", 6),
			entry("major", 7),
			entry("colonel", 8),
			entry("general", 9),
			entry("marshal", 10),
			entry("bomb", 11));
	
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
		return ranks.get(rank);
	}
	
	public int getOwner() {
		return owner;
	}

	public int getId() {
		return id;
	}
}
