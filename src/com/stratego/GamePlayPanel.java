package com.stratego;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class GamePlayPanel extends JPanel {
	private static final long serialVersionUID = -7309809738430689934L;
	boolean canMove = true;
	boolean changingTurns = false;
	ActionListener butListener;
	
	Game gameModel;
	Piece pieceSelected;
	
	int sourceLocX = 0;
	int sourceLocY = 0;
	int[][] possibleMoves;
	
	UserDisplayPanel userDisplayPanel;
	BoardPanel boardPanel;
	OptionPanel optionPanel;
		
	GamePlayPanel(ActionListener butListener, Game gameModel){
		this.butListener = butListener;
		this.gameModel = gameModel;
		
		optionPanel = new OptionPanel();
		userDisplayPanel = new UserDisplayPanel(gameModel, optionPanel);
		
		setBackground(TextureManager.getInstance().getColor("bg base"));
		
		setLayout(new BorderLayout());
		
		add(new MainPanel());
		
	}
	
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -5766267547077339193L;

		MainPanel(){
			setOpaque(false);
			
			setLayout(new GridBagLayout());
			
			GridBagConstraints leftPanelConstraints = new GridBagConstraints();
				leftPanelConstraints.gridx = 0;
				leftPanelConstraints.gridy = 0;
				leftPanelConstraints.gridheight = 1;
				leftPanelConstraints.gridwidth = 1;
				leftPanelConstraints.weighty = 2;
				leftPanelConstraints.weightx = 1;
				leftPanelConstraints.fill = GridBagConstraints.BOTH;
			add(userDisplayPanel, leftPanelConstraints);
			
			GridBagConstraints mPanelConstraints = new GridBagConstraints();
				mPanelConstraints.gridx = 1;
				mPanelConstraints.gridy = 0;
				mPanelConstraints.gridheight = 1;
				mPanelConstraints.gridwidth = 7;
				mPanelConstraints.weighty = 7;
				mPanelConstraints.weightx = 6;
				mPanelConstraints.insets = new Insets(10, 100, 10, 100);
				mPanelConstraints.fill = GridBagConstraints.BOTH;
			boardPanel = new BoardPanel();
			add(boardPanel, mPanelConstraints);		
		}
		
	}
		
	private class BoardPanel extends JPanel {
		private static final long serialVersionUID = -1075682170099848439L;
		int boardWidth;
		int gridWidth;
		int pieceWidth;
		int boardHOffset;
		int boardVOffset;
		
		BoardPanel(){
			setOpaque(false);
			
			setBorder(TextureManager.getInstance().getBorder("boarddb"));
			
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
				if(!(clickX < 0 || clickX > 9) || (clickY < 0 || clickY > 9)) {
					if(canMove) {
						if(((clickX > 1 && clickX < 4) || (clickX > 5 && clickX < 8)) && (clickY > 3 && clickY < 6)) {
							JOptionPane.showMessageDialog(boardPanel, "Please select a location away from the water.");
						} else if(pieceSelected == null) {
							if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
								if(gameModel.getBoard().getGridLocation(clickX, clickY).getOwner() == gameModel.getWhoseTurn()) {
									pieceSelected = gameModel.getBoard().getGridLocation(clickX, clickY);
									possibleMoves = gameModel.getBoard().getPossibleMoves(clickX, clickY);
									sourceLocX = clickX;
									sourceLocY = clickY;
								} else {
									JOptionPane.showMessageDialog(boardPanel, "Please select a location with your own piece.");
								}
							} else {
								JOptionPane.showMessageDialog(boardPanel, "Please select a location with a piece.");
							}
						} else if(pieceSelected != null) {
							if(gameModel.getBoard().getGridLocation(clickX, clickY) != null ? pieceSelected.getOwner() != gameModel.getBoard().getGridLocation(clickX, clickY).getOwner() : true) {
								if(possibleMoves != null) {
									for(int[] move : possibleMoves) {
										
										if(clickX == move[0] && clickY == move[1]) {
											if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
												int confirmMove = JOptionPane.showConfirmDialog(boardPanel, "Do you wish to attack this piece?");
												if(confirmMove == JOptionPane.OK_OPTION) {
													gameModel.getBoard().setLocation(clickX, clickY, gameModel.checkEncounter(pieceSelected, gameModel.getBoard().getGridLocation(clickX, clickY)));
													pieceSelected = null;
													JOptionPane.showMessageDialog(boardPanel, (gameModel.getBoard().getGridLocation(clickX, clickY) != null ? gameModel.getPlayer(gameModel.getBoard().getGridLocation(clickX, clickY).getOwner()).getPlayerName() : "No one") + " wins this round.");
												}
											} else {
												gameModel.getBoard().setLocation(clickX, clickY, pieceSelected);
												pieceSelected = null;
											}
											gameModel.getBoard().setLocation(sourceLocX, sourceLocY, null);
											possibleMoves = null;
											canMove = false;
											break;
										}
									}
								}
							} else {
								if(pieceSelected.getId() != gameModel.getBoard().getGridLocation(clickX, clickY).getId()) {
									pieceSelected = gameModel.getBoard().getGridLocation(clickX, clickY);
									possibleMoves = gameModel.getBoard().getPossibleMoves(clickX, clickY);
									sourceLocX = clickX;
									sourceLocY = clickY;
								}
							}
						}
					} else {
						if(!gameModel.getWinStatus()) {
							JOptionPane.showMessageDialog(boardPanel, "You have already made a move for this turn. Please switch turns.");
						}
					}
				}
				
				userDisplayPanel.displayPiece(pieceSelected);
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
			
			
			
			g.setColor(TextureManager.getInstance().getColor("border base"));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(TextureManager.getInstance().getImage("board"), boardHOffset, boardVOffset, boardWidth, boardWidth, null);
			
			Graphics2D g2d = (Graphics2D) g;
			g2d.setColor(Color.yellow);
			g2d.setStroke(new BasicStroke(4));
			if(!changingTurns) {
				if(gameModel.getWinStatus()) {
					for(int y = 0; y < 10; y++) {
						for(int x = 0; x < 10; x++) {
							Piece pieceAtLoc = gameModel.getBoard().getGridLocation(x, y);
							if(pieceAtLoc != null) {
								g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage(pieceAtLoc.getRank()), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
							}
						}
					}
				} else {
					for(int y = 0; y < 10; y++) {
						for(int x = 0; x < 10; x++) {
							Piece pieceAtLoc = gameModel.getBoard().getGridLocation(x, y);
							if(pieceAtLoc != null) {
								if(pieceAtLoc.getOwner() == gameModel.getWhoseTurn()) {
									g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage(pieceAtLoc.getRank()), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
									if(pieceSelected != null ? pieceSelected.getId() == pieceAtLoc.getId() : false) {
										g2d.drawRect(boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth);
									}
								} else {
									g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage("blank"), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
								}
							}
						}
					}
				}
				
				if(possibleMoves != null) {
					for(int[] loc : possibleMoves) {
						if(loc[0] != -1 && loc[1] != -1) {
							g2d.drawRect(boardHOffset + gridWidth * loc[0], boardVOffset + gridWidth * loc[1], pieceWidth, pieceWidth);
						}
					}
				}
			} else {
				for(int y = 0; y < 10; y++) {
					for(int x = 0; x < 10; x++) {
						Piece pieceAtLoc = gameModel.getBoard().getGridLocation(x, y);
						if(pieceAtLoc != null) {
							g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage("blank"), boardHOffset + gridWidth * x, boardVOffset + gridWidth * y, pieceWidth, pieceWidth,  null);
						}
					}
				}
			}
		}
	}
	
	private class OptionPanel extends JPanel {
		private static final long serialVersionUID = -1495946805893261147L;
		
		OptionPanel(){
			setOpaque(false);
			
			setLayout(new BorderLayout());

			add(new ButtonPanel());
		}
		
		private class ButtonPanel extends JPanel {
			private static final long serialVersionUID = -6012854594396096008L;

			ButtonPanel() {
				setOpaque(false);
				
				setLayout(new GridLayout(3,1));
				
				GraphicButton switchButton = new GraphicButton("Switch Turns");
				switchButton.addActionListener(new TurnListener());
				switchButton.setActionCommand("end_turn");
				add(switchButton);
				
				GraphicButton fullButton = new GraphicButton("Fullscreen Mode");
				fullButton.setActionCommand("fullscreen");
				fullButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(fullButton);
				
				GraphicButton exitButton = new GraphicButton("Quit Game");
				exitButton.setActionCommand("main_menu");
				exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
				add(exitButton);
			}
		}
	}
	
	private class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "end_turn") {
				if(gameModel.getWinStatus()) {
					boardPanel.repaint();
					JOptionPane.showMessageDialog(boardPanel, "Congratulations, " + gameModel.getCurrentPlayer().getPlayerName() + "! You won!\r\nPress \"Quit Game\" when you are ready to return to the menu.");
				} else if(!canMove) {
					changingTurns = true;
					boardPanel.repaint();
					JOptionPane.showMessageDialog(boardPanel, "Please switch turns with the other player.");
					gameModel.switchTurn();
					userDisplayPanel.displayPlayer();
					changingTurns = false;
					boardPanel.repaint();
					canMove = true;
				} else {
					JOptionPane.showMessageDialog(boardPanel, "You must move a piece before changing turns!");
				}
			}
		}
	}
}
