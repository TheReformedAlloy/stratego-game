package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class OptionMenuView extends JFrame{
	private static final long serialVersionUID = -2727297496436304263L;

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
		
		addKeyListener(keyboard);
		
		add(new MenuPanel());
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class MenuPanel extends GraphicPanel {
		private static final long serialVersionUID = -4727101758181622522L;
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
			
			add(new OptionPanel(), BorderLayout.CENTER);
			setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
		}
	}
	
	private class OptionPanel extends JPanel {
		private static final long serialVersionUID = 6653760825360020913L;

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
