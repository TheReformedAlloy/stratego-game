package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenuView extends JFrame {
	private static final long serialVersionUID = 3146868548367039221L;

	boolean isFullScreen;
	
	KeyListener keyboard;
	
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
	
	private class MenuPanel extends GraphicPanel {
		private static final long serialVersionUID = 1230171740611570657L;
		
		JLabel gameTitle;
		
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
	
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -2916829245822115605L;
		GraphicButton gameButton;
		GraphicButton optButton;
		GraphicButton exitButton;
		
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
