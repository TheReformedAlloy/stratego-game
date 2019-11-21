package com.stratego;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameView extends JFrame {
	boolean isFullScreen;
	KeyListener keyboard;
	
	JPanel innerPanel;
	CardLayout cards;
	
	GameInitPanel initPanel;
	GameSetupPanel setupPanel;
	GamePlayPanel gamePanel;
	
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
	
	private class PlayerSetupListener implements ActionListener {

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