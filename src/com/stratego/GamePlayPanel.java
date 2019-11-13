package com.stratego;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GamePlayPanel extends BackgroundPanel {
	
	int whoseTurnIsIt = 1;
	
	Game gameModel;
	
	GamePlayPanel(Game gameModel){
		this.gameModel = gameModel;
		
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
	
	private class BoardPanel extends EmptyPanel {
		
		int boardWidth;
		int gridWidth;
		int pieceWidth;
		int boardHOffset;
		int boardVOffset;
		
		BoardPanel(){
			super();
		}
		
		@Override
		public void paintComponent(Graphics g) {
			
			boardWidth = (int) (getHeight() * .95);
			boardWidth -= boardWidth % 10;
			gridWidth = boardWidth / 10;
			pieceWidth = (int) (gridWidth);
			boardHOffset = (getWidth() - boardWidth) / 2;
			boardVOffset = (getHeight() - boardWidth) / 2;
			
			g.drawImage(TextureManager.BOARD, boardHOffset, boardVOffset, boardWidth, boardWidth, null);
			
			for(int y = 0; y < 10; y++) {
				for(int x = 0; x < 10; x++) {
					Piece pieceAtLoc = gameModel.getBoard().getGridLocation(x, y);
					if(pieceAtLoc != null) {
						if(pieceAtLoc.getOwner() == whoseTurnIsIt) {
							g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage(pieceAtLoc.getRank()), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
						} else {
							g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage("blank"), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
						}
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
