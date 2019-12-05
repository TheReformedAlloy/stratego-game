package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * OptionMenuView is the JFrame that displays when the player selects 'Options' from the main menu and allows the
 * player to change options that affect gameplay from the main menu.
 * 
 * @author Clint Mooney
 *
 */
public class OptionMenuView extends JFrame{
	private static final long serialVersionUID = -2727297496436304263L;
		
		/** Stores whether the view should be displayed in fullscreen.*/
		boolean isFullScreen;
		
		/** When pressed, changes the game state to begin the game (Unused).*/
		GraphicButton gameButton;
		/** When pressed, changes the game state to toggle fullscreen.*/
		GraphicButton optButton;
		/** When pressed, changes the game state to return to the main menu.*/
		GraphicButton exitButton;

		/** Stores a reference to an instance of the game's StandardKeyListener (Unused).*/
		KeyListener keyboard;
	
	/**
	 * Creates an <code>OptionMenuView</code> with a <code>MenuPanel</code> as its body.
	 * 
	 * @param isFullScreen indicates whether the JFrame should be displayed in fullscreen mode.
	 */
	public OptionMenuView(boolean isFullScreen) {
		setTitle("Options | Stratego");
		
		this.isFullScreen = isFullScreen;
		
		if(isFullScreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}else {
			setSize(1280, 720);
		}
		
		addKeyListener(keyboard);
		
		add(new MenuPanel());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Displays a GraphicPanel containing the title of the game and a <code>OptionPanel</code>.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class MenuPanel extends GraphicPanel {
		private static final long serialVersionUID = -4727101758181622522L;
		
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
			
			add(new OptionPanel(), BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		}
	}
	
	/**
	 * Displays the buttons which change the game state of the game.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class OptionPanel extends JPanel {
		private static final long serialVersionUID = 6653760825360020913L;

		/**
		 * Creates a OptionPanel which displays <code>optButton</code>, and <code>exitButton</code>.
		 */
		OptionPanel(){
			setOpaque(false);
			setLayout(new GridLayout(0,1));
			
			optButton = new GraphicButton("Toggle Fullscreen: " + (isFullScreen ? "On" : "Off"));
			optButton.setActionCommand("fullscreen");
			optButton.setFocusable(false);
			optButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(optButton);
			
			exitButton = new GraphicButton("Return to Main Menu");
			exitButton.setActionCommand("main_menu");
			exitButton.setFocusable(false);
			exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(exitButton);
			
			setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
		}
	}
}
