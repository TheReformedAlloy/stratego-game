package com.stratego;

import java.awt.event.*;

import javax.swing.*;

/**
 * A singleton that contains the shared information used between the different JFrames
 * that make up the user interface and provides a way to handle state changes throughout
 * the game.
 * 
 * @author Clint Mooney
 *
 */
public class GameDriver {
	
	/**
	 * Provides a list of the possible states the game can be in.
	 * 
	 * @author Clint Mooney
	 *
	 */
	public enum GameState {
		MAINMENU, OPTIONMENU, GAMEDISPLAY
	}
	
	/** Denotes whether the game should be displayed in fullscreen.*/
	private static boolean isFullScreen = false;
	/** Indicates whether the game is able to play sound (Unused).*/
	private boolean sound = true;
	/** Records the current <code>GameState</code> that the game is in.*/
	private GameState viewState = GameState.MAINMENU;
	/** Provides a universal implementation of the <code>StateChangeListener</code> class.*/
	private static ActionListener stateChangeListener;
	/** Stores the current JFrame that is being displayed.*/
	private static JFrame view;
	
	/**
	 * Creates an instance of the GameDriver class; utilizes the <code>private</code> keyword
	 * to ensure that it can be initialized outside of the use of the <code>getInstance</code>
	 * method.
	 */
	private GameDriver(){
		stateChangeListener = new StateChangeListener();
	}
	
	/** Stores the current instance of the <code>GameDriver</code> class*/
	private static GameDriver instance = null;
	
	/** Ensures that the GameDriver has only one instance by initializing only if <code>instance</code>
	 * currently is <code>null</code>.
	 * 
	 * @return <code>instance</code>.
	 */
	public static GameDriver getInstance() {
		if(instance == null) {
			instance = new GameDriver();
		}
		
		return instance;
	}
	
	/** 
	 * Assigns <code>view</code> to a new <code>MainMenuView</code> to open the GUI.
	 */
	public static void openView() {
		view = new MainMenuView(isFullScreen);
	}
	
	/** 
	 * Returns whether the <code>view</code> is being displayed in fullscreen.
	 * 
	 * @return <code>isFullScreen</code>.
	 */ 
	public boolean isFullScreen() {
		return isFullScreen;
	}
	
	/**
	 * Toggles whether the GUI displayed currently is in fullscreen mode and displays the
	 * GUI with the new <code>JFrame</code>.
	 */
	public void toggleFullScreen() {
		isFullScreen = !isFullScreen;

		if(isFullScreen) {
			view.dispose();
			view.setExtendedState(JFrame.MAXIMIZED_BOTH);
			view.setResizable(false);
			view.setUndecorated(true);
			view.setVisible(true);
		}else {
			view.dispose();
			view.setResizable(false);
			view.setUndecorated(false);
			view.setVisible(true);
			view.setSize(1280, 720);
		}
	}
	
	/**
	 * Returns the boolean which indicates whether the game should play sound.
	 * 
	 * @return <code>sound</code> (Unused).
	 */
	public boolean isSoundOn() {
		return sound;
	}
	
	/**
	 * Toggles <code>sound</code> to the opposite of its current state.
	 */
	public void toggleSound() {
		sound = !sound;
	}
	
	/**
	 * Changes the state of the game based on the specified <code>newState</code> and updates
	 * the GUI accordingly.
	 * 
	 * @param newState the destination GameState which the game should transition to.
	 */
	private void changeViewState(GameState newState) {
		view.setVisible(false);
		view.dispose();
		
		viewState = newState;
		
		switch(viewState) {
		case MAINMENU:
			view = new MainMenuView(isFullScreen);
			break;
		case OPTIONMENU:
			view = new OptionMenuView(isFullScreen);
			break;
		case GAMEDISPLAY:
			view = new GameView(isFullScreen);
			break;
		}
	}
	
	
	/**
	 * Allows for the program to access the instance's stateChangeListener.
	 * 
	 * @return <code>stateChangeListener</code>
	 */
	public ActionListener getStateChangeListener() {
		return stateChangeListener;
	}
	
	/**
	 * Defines the ActionListener to be passed through the singleton that will listen for
	 * interactions with the GUI that demand a change in either the state of the game or
	 * the display.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class StateChangeListener implements ActionListener {
		@Override
		public void actionPerformed (ActionEvent e) {
			switch(e.getActionCommand()) {
			//Check for GUI-switching commands:
			case "main_menu":
				if(viewState == GameState.GAMEDISPLAY) {
					int quitting = JOptionPane.showConfirmDialog(view, "Are you sure you want to end the game early?");
					if(quitting == JOptionPane.OK_OPTION) {
						changeViewState(GameState.MAINMENU);
					}
				} else {
					changeViewState(GameState.MAINMENU);
				}
				break;
			case "options":
				changeViewState(GameState.OPTIONMENU);
				break;
			case "game":
				changeViewState(GameState.GAMEDISPLAY);
				break;
			case "exit_game":
				System.exit(0);
				break;
				
			//Check for property-switching commands:
			case "fullscreen":
				toggleFullScreen();
				break;
			case "sound":
				toggleSound();
				break;
			}
		}
	}

}
