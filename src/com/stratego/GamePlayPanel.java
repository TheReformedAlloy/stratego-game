package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePlayPanel extends BackgroundPanel {
	
	int whoseTurnIsIt = 1;
	boolean canMove = true;
	ActionListener butListener;
	
	Game gameModel;
	Piece pieceSelected;
	
	int lastClickX = 0;
	int lastClickY = 0;
	int[][] possibleMoves;
	
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
				int clickX = e.getX();
				clickX -= boardHOffset;
				clickX /= gridWidth;
				int clickY = e.getY();
				clickY -= boardVOffset;
				clickY /= gridWidth;
				
				if(canMove) {
					
					if(((clickX > 1 && clickX < 4) || (clickX > 5 && clickX < 8)) && (clickY > 3 && clickY < 6)) {
						JOptionPane.showMessageDialog(boardPanel, "Please select a location away from the water.");
					} else if(pieceSelected == null) {
						if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
							pieceSelected = gameModel.getBoard().getGridLocation(clickX, clickY);
							possibleMoves = gameModel.getBoard().getPossibleMoves(clickX, clickY);
						} else if(gameModel.getBoard().getGridLocation(clickX, clickY).getOwner() != whoseTurnIsIt) {
							JOptionPane.showMessageDialog(boardPanel, "Please select a location with your own piece.");
						} else {
							JOptionPane.showMessageDialog(boardPanel, "Please select a location with a piece.");
						}
					} else if(pieceSelected != null) {
						if(possibleMoves != null) {
							for(int[] move : possibleMoves) {
								if(clickX == move[0] && clickY == move[1]) {
									if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
										gameModel.getBoard().setLocation(clickX, clickY, gameModel.checkEncounter(pieceSelected, gameModel.getBoard().getGridLocation(clickX, clickY)));
										pieceSelected = null;
									} else {
										gameModel.getBoard().setLocation(clickX, clickY, pieceSelected);
										pieceSelected = null;
									}
									gameModel.getBoard().setLocation(lastClickX, lastClickY, null);
									possibleMoves = null;
									canMove = false;
									break;
								}
							}
						} else if(pieceSelected.getId() != gameModel.getBoard().getGridLocation(clickX, clickY).getId()) {
							int changePiece = JOptionPane.showConfirmDialog(boardPanel, "Do you want to change to a different piece?");
							if(changePiece == JOptionPane.OK_OPTION) {
								pieceSelected = gameModel.getBoard().getGridLocation(clickX, clickY);
								possibleMoves = gameModel.getBoard().getPossibleMoves(clickX, clickY);
							}
						}
					}
				} else {
					JOptionPane.showMessageDialog(boardPanel, "You have already made a move for this turn. Please switch turns.");
				}
				
				lastClickX = clickX;
				lastClickY = clickY;
				
				repaint();
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
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.yellow);
			g2d.setStroke(new BasicStroke(4));
			if(possibleMoves != null) {
				for(int[] loc : possibleMoves) {
					if(loc[0] != -1 && loc[1] != -1) {
						g2d.drawRect(boardHOffset + gridWidth * loc[0], boardVOffset + gridWidth * loc[1], pieceWidth, pieceWidth);
					}
				}
			}
		}
	}
	
	private class BottomPanel extends JPanel {
		
		CardLayout cards;
		
		BottomPanel(){
			setOpaque(false);
			
			setLayout(new BorderLayout());

			add(new ButtonPanel());
		}
		
		private class ButtonPanel extends JPanel {
			ButtonPanel() {
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
	}
	
	private class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "end_turn") {
					whoseTurnIsIt = whoseTurnIsIt == 2 ? 1 : 2;
					userDisplayPanel.displayPlayer(whoseTurnIsIt, gameModel);
					boardPanel.repaint();
					canMove = true;
			}
		}
	}
}
