package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class GamePlayingPanel extends BackgroundPanel {
	
	enum Turns {PLAYERONETURN, PLAYERTWOTURN, SHOWALL};
	
	BufferedImage player1Piece;
	BufferedImage player2Piece;
	
	Board board;
		
	GamePlayingPanel(BufferedImage player1Piece, BufferedImage player2Piece){
		this.player1Piece = player1Piece;
		this.player2Piece = player2Piece;
		
		setLayout(new BorderLayout());
		
		add(new MainPanel());
		
	}
	
	private class MainPanel extends EmptyPanel {
		
		MainPanel(){
			super();
			
			setLayout(new GridBagLayout());
			
			GridBagConstraints mPanelConstraints = new GridBagConstraints();
				mPanelConstraints.gridx = 0;
				mPanelConstraints.gridy = 0;
				mPanelConstraints.gridheight = 6;
				mPanelConstraints.gridwidth = 1;
				mPanelConstraints.weighty = 7;
				mPanelConstraints.weightx = 1;
				mPanelConstraints.fill = GridBagConstraints.BOTH;
			GamePanel gamePanel = new GamePanel();
			add(gamePanel, mPanelConstraints);
			
			GridBagConstraints bPanelConstraints = new GridBagConstraints();
				bPanelConstraints.gridx = 0;
				bPanelConstraints.gridy = 6;
				bPanelConstraints.gridheight = 1;
				bPanelConstraints.gridwidth = 1;
				bPanelConstraints.weighty = 1;
				bPanelConstraints.weightx = 1;
				bPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new BottomPanel(), bPanelConstraints);
			
			GridBagConstraints spacerConstraints = new GridBagConstraints();
				spacerConstraints.gridx = 0;
				spacerConstraints.gridy = 7;
				spacerConstraints.gridheight = 1;
				spacerConstraints.gridwidth = 1;
				spacerConstraints.weightx = 1;
				spacerConstraints.weighty = 1;
				spacerConstraints.fill = GridBagConstraints.BOTH;
			add(new EmptyPanel(), spacerConstraints);
				
		}
		
	}
	
	private class GamePanel extends EmptyPanel {
		
		GamePanel() {
			setLayout(new BorderLayout());
			
			add(new EmptyPanel(), BorderLayout.NORTH);
			add(new EmptyPanel(), BorderLayout.EAST);
			add(new BoardPanel(), BorderLayout.CENTER);
			add(new EmptyPanel(), BorderLayout.WEST);
			add(new EmptyPanel(), BorderLayout.SOUTH);
		}
	}
	
	private class BoardPanel extends JScrollPane {
		
		BoardPanel(){
			setOpaque(false);
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			int boardWidth = (int) (getHeight() * .9);
			int gridWidth = boardWidth / 10;
			int pieceWidth = (int) (gridWidth * .9);
			int boardHOffset = (getWidth() - boardWidth) / 2;
			int boardVOffset = (getHeight() - boardWidth) / 2;
			
			g.drawImage(TextureManager.BOARD, (getWidth() - boardWidth) / 2, (int) (getHeight() * .05), boardWidth, boardWidth, null);
			
			for(int i = 0; i < 10; i++) {
				for(int j = 0; j < 10; j++) {
					if(i < 4) {
						g.drawImage(player2Piece, boardHOffset + gridWidth * j + gridWidth / 10, boardVOffset + gridWidth * i + gridWidth / 10, pieceWidth, pieceWidth,  null);
					}else if(i > 5) {
						g.drawImage(player1Piece, boardHOffset + gridWidth * j + gridWidth / 10, boardVOffset + gridWidth * i + gridWidth / 10, pieceWidth, pieceWidth, null);
					}
				}
			}
		}
	}
	
	private class BottomPanel extends EmptyPanel {
		BottomPanel(){
			setLayout(new BorderLayout());
			
			setOpaque(false);
			
			GraphicButton exitButton = new GraphicButton("Exit Game", this.getWidth(), this.getHeight());
			exitButton.setActionCommand("main_menu");
			exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(exitButton);
		}
	}
}
