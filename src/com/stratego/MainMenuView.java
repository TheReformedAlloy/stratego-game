package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Displays the main menu of the game to the user. From this <code>JFrame</code>, the user's
 * input will change the game state or end the game.
 * 
 * @author Clint Mooney
 *
 */
public class MainMenuView extends JFrame {
	private static final long serialVersionUID = 3146868548367039221L;

	/** Stores whether the game should be displayed in fullscreen.*/
	boolean isFullScreen;
	
	/** Stores a reference to an instance of the game's StandardKeyListener (Unused).*/
	KeyListener keyboard;
	
	/**
	 * Creates a JFrame that displays in <code>BorderLayout</code> if <code>isFullScreen</code>
	 * is true and adds an anonymous instance of the <code>MenuPanel</code> internal class.
	 * 
	 * @param isFullScreen indicates whether the JFrame should be displayed in fullscreen mode.
	 */
	MainMenuView(boolean isFullScreen){
		setTitle("Main Menu | Stratego");
		
		this.isFullScreen = isFullScreen;
		if(isFullScreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}else {
			setSize(1280, 720);
		}
		
		setLayout(new BorderLayout());
		
		addKeyListener(keyboard);
		
		add(new MenuPanel());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Displays a GraphicPanel containing the title of the game and a <code>MainPanel</code>.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class MenuPanel extends GraphicPanel {
		private static final long serialVersionUID = 1230171740611570657L;
		
		/** Used to display the title of the game at the top of the panel.*/
		JLabel gameTitle;
		
		/** Creates a <code>MenuPanel</code> in <code>BorderLayout</code> with <code>assets/gui/Background.png</code> as the background.*/
		MenuPanel() {
			super(TextureManager.getInstance().getImage("background"));
			setLayout(new BorderLayout(100, 15));
			
			JPanel titlePanel = new JPanel();
			titlePanel.setBackground(TextureManager.getInstance().getColor("text base"));
			titlePanel.setBorder(TextureManager.getInstance().getBorder("boarddb"));
				gameTitle = new JLabel("Welcome to Stratego", JLabel.CENTER);
				gameTitle.setFont(new Font("Verdana", Font.PLAIN, 72));
				titlePanel.add(gameTitle);
			add(titlePanel, BorderLayout.NORTH);
			
			add(new MainPanel(), BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		}
	}
	
	/**
	 * Displays the buttons which change the game state of the game.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -2916829245822115605L;

		/** When pressed, changes the game state to open the game view.*/
		GraphicButton gameButton;
		/** When pressed, changes the game state to open the option view.*/
		GraphicButton optButton;
		/** When pressed, closes the game with a code of 0.*/
		GraphicButton exitButton;
		
		/**
		 * Creates a MainPanel which displays <code>gameButton</code>, <code>optButton</code>, and <code>exitButton</code>.
		 */
		MainPanel(){
			setLayout(new GridLayout(0, 1, 30, 10));
			
			setOpaque(false);
			
			gameButton = new GraphicButton("Start New Game");
			gameButton.setActionCommand("game");
			gameButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			gameButton.setFocusable(false);
			add(gameButton);
			optButton = new GraphicButton("Options");
			optButton.setActionCommand("options");
			optButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			gameButton.setFocusable(false);
			add(optButton);
			exitButton = new GraphicButton("Exit Game");
			exitButton.setActionCommand("exit_game");
			exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			gameButton.setFocusable(false);
			add(exitButton);
			
			setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}
	}
}
