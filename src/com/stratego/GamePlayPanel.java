package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePlayPanel extends BackgroundPanel {
	
	int whoseTurnIsIt = 1;
	ActionListener butListener;
	
	Game gameModel;
	Piece pieceSelected;
	
	UserDisplayPanel userDisplayPanel;
	BoardPanel boardPanel;
	BottomPanel optionPanel;
		
	GamePlayPanel(ActionListener butListener, Game gameModel){
		
		this.butListener = butListener;
		this.gameModel = gameModel;
		
		userDisplayPanel = new UserDisplayPanel(whoseTurnIsIt, gameModel);
		optionPanel = new BottomPanel();
		
		setLayout(new BorderLayout());
		
		add(new MainPanel());
		
	}

	
	private class MainPanel extends EmptyPanel {
		
		MainPanel(){
			super();
			
			setLayout(new GridBagLayout());
			
			GridBagConstraints leftPanelConstraints = new GridBagConstraints();
				leftPanelConstraints.gridx = 0;
				leftPanelConstraints.gridy = 0;
				leftPanelConstraints.gridheight = 2;
				leftPanelConstraints.gridwidth = 1;
				leftPanelConstraints.weighty = 2;
				leftPanelConstraints.weightx = 1;
				leftPanelConstraints.fill = GridBagConstraints.BOTH;
			add(userDisplayPanel, leftPanelConstraints);
			
			GridBagConstraints mPanelConstraints = new GridBagConstraints();
				mPanelConstraints.gridx = 1;
				mPanelConstraints.gridy = 0;
				mPanelConstraints.weighty = 7;
				mPanelConstraints.weightx = 6;
				mPanelConstraints.fill = GridBagConstraints.BOTH;
			boardPanel = new BoardPanel();
			add(boardPanel, mPanelConstraints);
			
			GridBagConstraints bPanelConstraints = new GridBagConstraints();
				bPanelConstraints.gridx = 0;
				bPanelConstraints.gridy = 7;
				bPanelConstraints.gridwidth = 8;
				bPanelConstraints.weighty = 1;
				bPanelConstraints.weightx = 8;
				bPanelConstraints.fill = GridBagConstraints.BOTH;
			add(optionPanel, bPanelConstraints);			
		}
		
	}
		
	private class BoardPanel extends JPanel {
		
		int boardWidth;
		int gridWidth;
		int pieceWidth;
		int boardHOffset;
		int boardVOffset;
		
		BoardPanel(){
			setOpaque(false);
			
			addMouseListener(new PieceMovementListener());
			
		}
		
		private class PieceMovementListener implements MouseListener {

			@Override
			public void mouseClicked(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
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
	
	private class BottomPanel extends JPanel {
		
		CardLayout cards;
		
		BottomPanel(){
			setOpaque(false);
			
			cards = new CardLayout();
			setLayout(cards);

			add(new Player1Panel(), "player1");
			add(new Player2Panel(), "player2");
			
			cards.show(this, "player1");
		}
		
		private class Player1Panel extends JPanel {
			Player1Panel() {
				setOpaque(false);
				
				setLayout(new GridLayout(1,0));
				
				GraphicButton exitButton = new GraphicButton("Quit Game");
				exitButton.setActionCommand("main_menu");
				exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(exitButton);
				
				GraphicButton shuffleButton = new GraphicButton("Shuffle Pieces");
				shuffleButton.setActionCommand("shuffle");
				shuffleButton.addActionListener(new TurnListener());
				add(shuffleButton);
				
				GraphicButton fullButton = new GraphicButton("Fullscreen Mode");
				fullButton.setActionCommand("fullscreen");
				fullButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(fullButton);
				
				GraphicButton switchButton = new GraphicButton("Switch Turns");
				switchButton.addActionListener(new TurnListener());
				switchButton.setActionCommand("end_turn");
				add(switchButton);
			}
		}
		
		private class Player2Panel extends JPanel {
			Player2Panel() {
				setOpaque(false);
				
				setLayout(new GridLayout(1,0));
				
				GraphicButton exitButton = new GraphicButton("Quit Game");
				exitButton.setActionCommand("main_menu");
				exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(exitButton);
				
				GraphicButton shuffleButton = new GraphicButton("Shuffle Pieces");
				shuffleButton.setActionCommand("shuffle");
				shuffleButton.addActionListener(new TurnListener());
				add(shuffleButton);
				
				GraphicButton fullButton = new GraphicButton("Fullscreen Mode");
				fullButton.setActionCommand("fullscreen");
				fullButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(fullButton);
				
				GraphicButton switchButton = new GraphicButton("Begin Game");
				switchButton.addActionListener(butListener);
				switchButton.setActionCommand("start_game");
				add(switchButton);
			}
		}
	}
	
	private class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "end_turn") {
				if(gameModel.getBoard().checkNumberOfPieces(whoseTurnIsIt) == 40) {
					whoseTurnIsIt = 2;
					userDisplayPanel.displayPlayer(whoseTurnIsIt, gameModel);
					boardPanel.repaint();
					optionPanel.cards.show(optionPanel, "player2");
				}else {
					JOptionPane.showMessageDialog(boardPanel, "You must place down all pieces on your side of the board before continuing.");
				}
			} else if(e.getActionCommand() == "shuffle") {
				gameModel.shuffle(whoseTurnIsIt);
				boardPanel.repaint();
			}
		}
	}
}
