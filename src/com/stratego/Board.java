package com.stratego;

public class Board {
	
	private Piece[][] board;   //this array needs to be a 10x10
	private int w;    //used for width of the display
	private int h;    //used for height of the display
	
	public Board(int width, int height)
	{
		w = width;
		h = height;
	}

	//used to the piece found at a given grid location
	public Piece getGridLocation(int x, int y)
	{
		return board[y][x];   //the order of x and y is flipped here. If we stick to the traditional orientation of an x and y axis, 
							  //this is needed to ensure that the board reads the correct space. 	
	}
	
	//used to change the contents of a location on the board
	public void setLocation(int x, int y, Piece p)
	{
		board[y][x] = p;
	}
}
