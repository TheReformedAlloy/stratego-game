package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class OptionMenuView extends JFrame{
		boolean isFullScreen;
		
		GraphicButton gameButton;
		GraphicButton optButton;
		GraphicButton exitButton;
		KeyListener keyboard;
	
	public OptionMenuView(boolean isFullScreen) {
		setTitle("Options | Stratego");
		
		this.isFullScreen = isFullScreen;
		
		if(isFullScreen) {
			setExtendedState(JFrame.MAXIMIZED_BOTH);
			setUndecorated(true);
		}else {
			setSize(1280, 720);
		}
		
		keyboard = new StandardKeyboardListener();
		addKeyListener(keyboard);
		
		add(new MenuPanel());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class MenuPanel extends BackgroundPanel {
		JLabel gameTitle;
		
		
		MenuPanel() {
			setLayout(new BorderLayout(100, 15));
			
			gameTitle = new JLabel("Welcome to Stratego", JLabel.CENTER);
			gameTitle.setFont(new Font("Verdana", Font.ITALIC, 72));
			gameTitle.setForeground(Color.LIGHT_GRAY);
			add(gameTitle, BorderLayout.NORTH);
			
			add(new OptionPanel(), BorderLayout.CENTER);
			add(Box.createGlue(), BorderLayout.EAST);
			add(Box.createGlue(), BorderLayout.WEST);
			add(Box.createGlue(),BorderLayout.SOUTH);
		}
	}
	
	private class OptionPanel extends JPanel {
		
		OptionPanel(){
			setOpaque(false);
			setLayout(new GridLayout(0,1));
			
			gameButton = new GraphicButton("Toggle Sound");
			gameButton.setActionCommand("sound");
			gameButton.setFocusable(false);
			gameButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(gameButton);
			
			optButton = new GraphicButton("Toggle Fullscreen: " + (isFullScreen ? "On" : "Off"));
			optButton.setActionCommand("fullscreen");
			optButton.setFocusable(false);
			optButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(optButton);
			
			exitButton = new GraphicButton("Back to Main Menu pls?");
			exitButton.setActionCommand("main_menu");
			exitButton.setFocusable(false);
			exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(exitButton);
		}
	}
}
