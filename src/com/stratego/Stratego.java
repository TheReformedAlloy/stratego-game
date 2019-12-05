package com.stratego;

/**
 * The <code>Stratego</code> class creates an instance of the GameDriver singleton and calls
 * <code>GameDriver.openView</code> to open the game's user interface.
 * 
 * @author Clint Mooney
 * @author James Ford
 * @author Rebecca Whisnant
 * @author Jeremiah Allsbrook
 *
 */
public class Stratego {
	/**
	 * Contains a static reference to the current instance of the <code>GameDriver</code>.
	 */
	static GameDriver strategoDriver;
	
	/**
	 * Assigns <code>strategoDriver</code> to the result of <code>GameDriver.getInstance</code>
	 * and opens the GUI of the game.
	 * 
	 * @param args a set of strings that is passed to the executable as parameters. Nothing
	 * is done with these.
	 */
	public static void main(String[] args) {
		strategoDriver = GameDriver.getInstance();
		GameDriver.openView();
	}
}