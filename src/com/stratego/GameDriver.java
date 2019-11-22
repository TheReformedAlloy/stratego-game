package com.stratego;

import java.awt.event.*;

import javax.swing.*;

//Singleton used to create one instance of the game and have available to the rest of the program the stateChanging ActionListener:
public class GameDriver {
	
	//Enumerator lists the possible states the game can be in:
	public enum GameState {
		MAINMENU, OPTIONMENU, GAMEDISPLAY
	}
	
	private static boolean isFullScreen = false;				//Stores a boolean which is true if the game should be fullscreen.
	private boolean sound = true;								//Stores a boolean which is true if the game able to play sound.
	private GameState viewState = GameState.MAINMENU;			//Stores a GameState value which is called whenever changeViewState is called.
	private static ActionListener stateChangeListener;			//Stores an actionlistener to be referenced throughout the rest of the program.
	private static JFrame view;//Stores an instance of the current game window.
	
	//Constructor creates a JFrame to store in view, rendering at 1280x720. Cannot be created outside of the class.
	private GameDriver(){
		stateChangeListener = new StateChangeListener();
	}
	
	//instance will be used to create an instance for the game.
	private static GameDriver instance = null;
	
	//getInstance() can be accessed outside of this class to get the current instance of the game.
	public static GameDriver getInstance() {
		if(instance == null) {
			instance = new GameDriver();
		}
		
		return instance;
	}
	
	public static void openView() {
		view = new MainMenuView(isFullScreen);
	}
	
	public boolean isFullScreen() {
		return isFullScreen();
	}
	
	//Toggles whether the GUI displayed currently is in fullscreen mode and displays the GUI with the new:
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
	
	public boolean isSoundOn() {
		return sound;
	}
	
	//Toggles the sound boolean:
	public void toggleSound() {
		sound = !sound;
	}
	
	//Changes the state of the game based on the specified newState:
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
	
	
	//Allows for the program to access the instance's stateChangeListener:
	public ActionListener getStateChangeListener() {
		return stateChangeListener;
	}
	
	//Defines the ActionListener to be passed through the singleton:
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
