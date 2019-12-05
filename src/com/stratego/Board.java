package com.stratego;

/**
 * A model to manage a two-dimensional array of <code>Pieces</code> during setup and play.
 * 
 * @author James Ford
 * @author Clint Mooney
 *
 */
public class Board {
	
	/**
	 * A two-dimensional array to store the current pieces on the board in a 10x10 grid, representing the
	 * typical Stratego board.
	 */
	private Piece[][] board;   //this array needs to be a 10x10
	/*
	 * private int w; //used for width of the display private int h; //used for
	 * height of the display
	 */	
	/**
	 * Creates a <code>Board</code> and defines <code>board</code> as a 10x10 grid.
	 */
	public Board()
	{
		board = new Piece[10][10];
		/*
		 * w = width; h = height;
		 */
	}

	/**
	 * Used to get the piece found at a given grid location. The order of x and y is flipped here. If we stick to the traditional
	 * orientation of an x and y axis, this is needed to ensure that the board reads the correct space. 	
	 * 
	 * @param x specifies the Piece's location on the x-axis of <code>board</code>.
	 * @param y specifies the Piece's location on the y-axis of <code>board</code>.
	 * @return the Piece at location (x, y).
	 */
	public Piece getGridLocation(int x, int y)
	{
		return board[y][x];   //the order of x and y is flipped here. If we stick to the traditional orientation of an x and y axis, 
							  //this is needed to ensure that the board reads the correct space. 	
	}
	
	/**
	 * Used to change the contents of a location on the board. The order of x and y is flipped here. If we stick to the traditional
	 * orientation of an x and y axis, this is needed to ensure that the board reads the correct space. 	
	 * 
	 * @param x specifies the Piece's location on the x-axis of <code>board</code>.
	 * @param y specifies the Piece's location on the y-axis of <code>board</code>.
	 * @param p the Piece to be placed at location (x, y).
	 */
	public void setLocation(int x, int y, Piece p)
	{
		board[y][x] = p;
	}
	
	/**
	 * Counts the number of pieces a player has on the <code>board</code> based on their associated <code>turnOrder</code>,
	 * which corresponds to the <code>owner</code> field of a <code>Piece</code>.
	 * 
	 * @param playerNo indicates which player the <code>Piece</code> must belong to in order to be counted.
	 * @return the number of <code>Piece</code>s that belong to a given player.
	 */
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
	
	/**
	 * Returns an array of the possible locations a chosen piece can move to.
	 * 
	 * @param x specifies the location on the x-axis of <code>board</code> that the piece to be considered is stored in.
	 * @param y specifies the location on the y-axis of <code>board</code> that the piece to be considered is stored in.
	 * @return an array of the possible locations a chosen piece can move to. 
	 */
	public int[][] getPossibleMoves(int x, int y) 
	{
		int[][] moves;      					//stores the possible moves
		
		if (getGridLocation(x, y) == null)	//makes sure there is a piece present in the selected space
		{
			return null;
		} else {
			
			boolean isScout = false;
			int maxMoves = 4;
			
			if(getGridLocation(x, y).getRankValue() == 2)    //handles scouts
			{
				isScout = true;
				maxMoves = 18;
			}
			else if(getGridLocation(x, y).getRankValue() == 0 || getGridLocation(x, y).getRankValue() == 11)  //handles bombs and flags
			{
				return null;
			}
			int location = 0;
			moves = new int[maxMoves][2];
			for (int[] move : moves) {
				move[0] = -1;
				move[1] = -1;
			}
			int leftLimit = isScout ? 0 : x == 0 ? 0 : x - 1;
			int rightLimit = isScout ? 9 : x == 9 ? 9 : x + 1;
			int upLimit = isScout ? 0 : y == 0 ? 0 : y - 1;
			int downLimit = isScout ? 9 : y == 9 ? 9 : y + 1;
			
			//the increment might need to be raised/lowered (based on case), decide in testing
			for(int i = x - 1; i >= leftLimit; i--)   //going left
			{
				if(canMoveTo(x, y, i, y)) {
					moves[location][0] = i;
					moves[location][1] = y;
					location++;
					if(getGridLocation(i, y) != null) {
						break;
					}
				} else {
					break;
				}
			}
			for(int i = x + 1; i <= rightLimit; i++)   //going right
			{
				if(canMoveTo(x, y, i, y)) {
					moves[location][0] = i;
					moves[location][1] = y;
					location++;
					if(getGridLocation(i, y) != null) {
						break;
					}
				} else {
					break;
				}
			}
			for(int i = y - 1; i >= upLimit; i--)   //going up
			{
				if(canMoveTo(x, y, x, i)) {
					moves[location][0] = x;
					moves[location][1] = i;
					location++;
					if(getGridLocation(x, i) != null) {
						break;
					}
				} else {
					break;
				}
			}
			for(int i = y + 1; i <= downLimit; i++)   //going down 
			{
				if(canMoveTo(x, y, x, i)) {
					moves[location][0] = x;
					moves[location][1] = i;
					location++;
					if(getGridLocation(x, i) != null) {
						break;
					}
				} else {
					break;
				}
			}
		}
		
		return moves;
	}

	private boolean canMoveTo(int startX, int startY, int newX, int newY) {
		if((newX == 2 || newX == 3 || newX == 6 || newX == 7) && (newY == 4 || newY == 5))	//keeps scouts out of the water
		{
			return false;
		}
		if(getGridLocation(newX, newY) != null ? getGridLocation(startX, startY).getOwner() != getGridLocation(newX, newY).getOwner() : true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
