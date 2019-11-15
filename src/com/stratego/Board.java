package com.stratego;

import java.util.Arrays;

public class Board {
	
	private Piece[][] board;   //this array needs to be a 10x10
	/*
	 * private int w; //used for width of the display private int h; //used for
	 * height of the display
	 */	
	public Board()
	{
		board = new Piece[10][10];
		/*
		 * w = width; h = height;
		 */
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
	
	public int checkNumberOfPieces(int playerNo) {
		int count = 0;
		
		for(int y = 0; y < 10; y++) {
			for(int x = 0; x < 10; x++) {
				if(board[y][x] != null ? board[y][x].getOwner() == playerNo : false) {
					count++;
				}
			}
		}
		
		return count;
	}
	
	//returns an array of the possible locations a chosen piece can move to
	public int[][] getPossibleMoves(int x, int y) 
	{
		int[][] moves;      					//stores the possible moves
		int owner;								//stores the owner of the selected piece
		
		if (getGridLocation(x, y) == null)	//makes sure there is a piece present in the selected space
		{
			return null;
		} else {
			owner = getGridLocation(x, y).getOwner();
			
			if(getGridLocation(x, y).getRankValue() == 2)    //handles scouts
			{
				int location = 0;
				moves = new int[18][2];
				for (int[] move : moves) {
					move[0] = -1;
					move[1] = -1;
				}
				//the increment might need to be raised/lowered (based on case), decide in testing
				for(int i = x - 1; i >= 0; i--)   //going left
				{
					if(getGridLocation(i, y) != null ? getGridLocation(x, y).getOwner() != getGridLocation(i, y).getOwner() : true)
					{
						moves[location][0] = i;
						moves[location][1] = y;
						location++;
					}
					else
					{
						break;
					}
				}
				for(int i = x + 1; i <= 9; i++)   //going right
				{
					if(getGridLocation(i, y) != null ? getGridLocation(x, y).getOwner() != getGridLocation(i, y).getOwner() : true)
					{
						moves[location][0] = i;
						moves[location][1] = y;
						location++;
					}
					else
					{
						break;
					}
				}
				for(int i = y - 1; i >= 0; i--)   //going up
				{
					if(getGridLocation(x, i) != null ? getGridLocation(x, y).getOwner() != getGridLocation(x, i).getOwner() : true)
					{
						moves[location][0] = x;
						moves[location][1] = i;
						location++;
						if(getGridLocation(x, i) != null)
						{
							break;
						}
					}
					else
					{
						break;
					}
				}
				for(int i = y + 1; i <= 9; i++)   //going down 
				{
					if(getGridLocation(x, i) != null ? getGridLocation(x, y).getOwner() != getGridLocation(x, i).getOwner() : true)
					{
						moves[location][0] = x;
						moves[location][1] = i;
						location++;
						if(getGridLocation(x, i) != null)
						{
							break;
						}
					}
					else
					{
						break;
					}
				}
			}
			
			else if(getGridLocation(x, y).getRankValue() == 0 || getGridLocation(x, y).getRankValue() == 11)  //handles bombs and flags
			{
				return null;
			}
			
			else   //handles all pieces that move "normally"
			{
				moves = new int[4][2];
				for (int[] move : moves) {
					move[0] = -1;
					move[1] = -1;
				}
				System.out.println(y-1 >= 0 && getGridLocation(x, y-1) != null ? getGridLocation(x, y-1).getOwner() != owner : true);
				if(y+1 <= 9 && getGridLocation(x, y+1) != null ? getGridLocation(x, y+1).getOwner() != owner : true)		//space above the chosen one
				{
					moves[0][0] = x;    //stores the values in an x, y configuration. 
					moves[0][1] = y+1;
				}
				if(y-1 >= 0 && getGridLocation(x, y-1) != null ? getGridLocation(x, y-1).getOwner() != owner : true)	//space below the chosen one
				{
					moves[1][0] = x;    //stores the values in an x, y configuration. 
					moves[1][1] = y-1;
				}
				if(x-1 >= 0 && getGridLocation(x-1, y) != null ? getGridLocation(x-1, y).getOwner() != owner : true)	//space to the left of the chosen one
				{
					moves[2][0] = x-1;    //stores the values in an x, y configuration. 
					moves[2][1] = y;
				}
				if(x+1 <= 9 && getGridLocation(x+1, y) != null ? getGridLocation(x+1, y).getOwner() != owner : true)	//space to the right of the chosen one
				{
					moves[3][0] = x+1;    //stores the values in an x, y configuration.
					System.out.println(moves[3][0]);
					moves[3][1] = y;
				}
			}
			return moves;
		}
	}
}
