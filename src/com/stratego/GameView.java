package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameView extends JFrame {
	boolean isFullScreen;
	KeyListener keyboard;
	
	JPanel innerPanel;
	CardLayout cards;
	
	GameSetupPanel setupPanel;
	GamePlayingPanel gamePanel;
	
	Game gameModel;
	
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
		
		keyboard = new StandardKeyboardListener();
		addKeyListener(keyboard);
		
		innerPanel = new EmptyPanel();
		cards = new CardLayout();
		innerPanel.setLayout(cards);
		
		setupPanel = new GameSetupPanel(new PlayerSetupListener());
		innerPanel.add(setupPanel, "Setup");
		
		add(innerPanel);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class PlayerSetupListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals("submit")) {
				gameModel = new Game(setupPanel.getPlayer1(), setupPanel.getPlayer2());
				gamePanel = new GamePlayingPanel(gameModel.player1.basePieceImage, gameModel.player2.basePieceImage);
				innerPanel.add(gamePanel, "Game");
				cards.show(innerPanel, "Game");
			}
		}
	}
}