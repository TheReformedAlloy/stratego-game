package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenuView extends JFrame {
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
		
		keyboard = new StandardKeyboardListener();
		addKeyListener(keyboard);
		
		add(new MenuPanel());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void changeFullScreen() {
		setVisible(false);
		MainMenuView newMenu = new MainMenuView(!isFullScreen);
		dispose();
	}
	
	private class MenuPanel extends BackgroundPanel {
		MainPanel mainPanel;
		JLabel gameTitle;
		
		MenuPanel() {
			super();
			setLayout(new BorderLayout(100, 15));
			
			gameTitle = new JLabel("Welcome to Stratego", JLabel.CENTER);
			gameTitle.setFont(new Font("Verdana", Font.ITALIC, 72));
			gameTitle.setForeground(Color.LIGHT_GRAY);
			add(gameTitle, BorderLayout.NORTH);
			
			add(new MainPanel(), BorderLayout.CENTER);
			add(new EmptyPanel(), BorderLayout.EAST);
			add(new EmptyPanel(), BorderLayout.WEST);
			add(new EmptyPanel(),BorderLayout.SOUTH);
		}
	}
	
	private class MainPanel extends JPanel {
		
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
		}
	}
}
