package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * GameView is the JFrame that displays when the player selects 'Start New Game' from the main menu and allows the
 * players to begin setup of the game and play Stratego.
 * 
 * @author Clint Mooney
 *
 */

public class GameView extends JFrame {
	private static final long serialVersionUID = 7297598809856376824L;
	
	/** Stores whether the game should be displayed in fullscreen.*/
	boolean isFullScreen;
	/** Stores a reference to an instance of the game's StandardKeyListener (Unused).*/
	KeyListener keyboard;

	/** A JPanel with CardLayout to switch between different child panels that represent stages of the game.*/
	JPanel innerPanel;
	/** The LayoutManager which will be implemented by innerPanel.*/
	CardLayout cards;

	/** A JPanel representing the player customization screen.*/
	GameInitPanel initPanel;
	/** A JPanel representing the board setup screen.*/
	GameSetupPanel setupPanel;
	/** A JPanel representing the game during play.*/
	GamePlayPanel gamePanel;

	/** An instance of the Game class representing the model used for display.*/
	Game gameModel;
	
	/**
	 * Creates a GameView.
	 * 
	 * @param isFullScreen Stores whether the game should be displayed in fullscreen.
	 */
	public GameView(boolean isFullScreen) {
		//Set up Initial Game View:
		setTitle("Game | Stratego");
		
		this.isFullScreen = isFullScreen;
		if(isFullScreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}else {
			setSize(1280, 720);
		}
		
		setLayout(new BorderLayout());
		
		addKeyListener(keyboard);
		
		innerPanel = new JPanel();
		innerPanel.setOpaque(false);
		cards = new CardLayout();
		innerPanel.setLayout(cards);
		
		initPanel = new GameInitPanel(new PlayerSetupListener());
		innerPanel.add(initPanel, "Setup");
		
		add(innerPanel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Listens for input from the player indicating that they are finished customizing their color/name or setting up the board.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class PlayerSetupListener implements ActionListener {
		/**
		 * Determines what to do in the case a button is pressed.
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("start_setup")) {
				gameModel = new Game(initPanel.getPlayer1(), initPanel.getPlayer2());
				setupPanel = new GameSetupPanel(this, gameModel);
				innerPanel.add(setupPanel, "Setup");
				cards.show(innerPanel, "Setup");
			} else if (e.getActionCommand().equals("start_game")) {
				if(gameModel.getBoard().checkNumberOfPieces(gameModel.getWhoseTurn()) == 40) {
					gameModel.switchTurn();
					gamePanel = new GamePlayPanel(this, gameModel);
					innerPanel.add(gamePanel, "Game");
					cards.show(innerPanel, "Game");
				}else {
					JOptionPane.showMessageDialog(setupPanel, "You must place down all pieces on your side of the board before continuing.");
				}
			}
		}
	}
}