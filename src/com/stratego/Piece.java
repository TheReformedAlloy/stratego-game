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
	
	int owner;
	String rank;
	
	Piece(int owner, String rank) {
		this.owner = owner;
		this.rank = rank;
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
}
