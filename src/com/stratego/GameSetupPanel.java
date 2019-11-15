package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.HashMap;

import javax.swing.*;

public class GameSetupPanel extends BackgroundPanel {
	
	int whoseTurnIsIt = 1;
	ActionListener butListener;
	
	Game gameModel;
	Piece pieceSelected;
	
	UserDisplayPanel userDisplayPanel;
	BoardPanel boardPanel;
	PieceSelectorPanel pieceSelector;
	BottomPanel optionPanel;
		
	GameSetupPanel(ActionListener butListener, Game gameModel){
		
		this.butListener = butListener;
		this.gameModel = gameModel;
		
		userDisplayPanel = new UserDisplayPanel(whoseTurnIsIt, gameModel);
		pieceSelector = new PieceSelectorPanel();
		optionPanel = new BottomPanel();
		
		setLayout(new BorderLayout());
		
		add(new MainPanel());
		
	}
	
	private void placePiece (int x, int y) {
		gameModel.getBoard().setLocation(x, y, pieceSelected);
		gameModel.getPlayer(whoseTurnIsIt).subUnplacedPiece(pieceSelected.getRank());
		pieceSelector.panels.get(pieceSelected.getRank()).setBackground(Color.LIGHT_GRAY);
		pieceSelected = null;
		pieceSelector.updatePieceCount();
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
			
			GridBagConstraints rightPanelConstraints = new GridBagConstraints();
				rightPanelConstraints.gridx = 6;
				rightPanelConstraints.gridy = 0;
				rightPanelConstraints.weighty = 7;
				rightPanelConstraints.weightx = 2;
				rightPanelConstraints.fill = GridBagConstraints.BOTH;
			JScrollPane pieceViewer = new JScrollPane(pieceSelector);
			pieceViewer.setOpaque(false);
			add(pieceViewer, rightPanelConstraints);
			
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
			
			addMouseListener(new PiecePlacementListener());
			
		}
		
		private class PiecePlacementListener implements MouseListener {

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
				
				if((whoseTurnIsIt == 1 && clickY < 6) || (whoseTurnIsIt == 2 && clickY > 3)) {
					JOptionPane.showMessageDialog(boardPanel, "Player " + whoseTurnIsIt + " cannot place a piece there on setup.");
				} else if(pieceSelected != null) {
					if(((clickX > 1 && clickX < 4) || (clickX > 5 && clickX < 8)) && (clickY > 3 && clickY < 6)) {
						JOptionPane.showMessageDialog(boardPanel, "Please select a location away from the water.");
					} else if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
						int allow = JOptionPane.showConfirmDialog(boardPanel, "Are you sure you want to replace this piece?", "Stratego | Replace Piece?", JOptionPane.YES_NO_OPTION);
						if(allow == JOptionPane.YES_OPTION) {
							gameModel.getPlayer(whoseTurnIsIt).addUnplacedPiece(gameModel.getBoard().getGridLocation(clickX, clickY).getRank());
							placePiece(clickX, clickY);
						}
					} else {
						placePiece(clickX, clickY);
					}
				} else {
					if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
						int remove = JOptionPane.showConfirmDialog(boardPanel, "Are you sure you want to remove this piece?", "Stratego | Remove Piece?", JOptionPane.YES_NO_OPTION);
						if(remove == JOptionPane.YES_OPTION) {
							gameModel.getPlayer(whoseTurnIsIt).addUnplacedPiece(gameModel.getBoard().getGridLocation(clickX, clickY).getRank());
							gameModel.getBoard().setLocation(clickX, clickY, null);
						}
					} else {
						JOptionPane.showMessageDialog(boardPanel, "Please select a piece to place!");
					}
				}
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
		}
	}	
	
	private class PieceSelectorPanel extends JPanel {
		
		HashMap<String, PiecePanel> panels = new HashMap<String, PiecePanel>();
		
		PieceSelectorPanel() {
			setOpaque(false);
			
			setLayout(new GridLayout(0, 1));
			
			for(String rank : Piece.ranks.keySet()) {
				panels.put(rank, new PiecePanel(rank));
				add(panels.get(rank));
			}
		}
		
		public void updatePieceCount() {
			for(String rank : Piece.ranks.keySet()) {
				int numLeft = gameModel.getPlayer(whoseTurnIsIt).getNumberOfRank(rank);
				panels.get(rank).setNumLabel(numLeft);
			}
		};
		
		public void redrawPieces() {
			for(String rank : Piece.ranks.keySet()) {
				panels.get(rank).setPieceImage(gameModel.getPlayer(whoseTurnIsIt).getImage(rank));
			}
		}
		
		private class PiecePanel extends JPanel {
			
			JLabel icon;
			
			String rank;
			
			PiecePanel(String rank) {
				this.rank = rank;
				
				addMouseListener(new PieceSelectionListener());
				
				setBackground(Color.LIGHT_GRAY);
				
				icon = new JLabel(new ImageIcon(gameModel.getPlayer(whoseTurnIsIt).getImage(rank)));
				icon.setText("x" + Integer.toString(gameModel.getPlayer(whoseTurnIsIt).getNumberOfRank(rank)));
				add(icon);
			}
			
			public void setNumLabel(int numLeft) {
				if(numLeft == 0) {
					setBackground(Color.DARK_GRAY);
				}
				icon.setText("x" + Integer.toString(numLeft));
			}
			
			public void setPieceImage(BufferedImage pieceIMG) {
				icon.setIcon(new ImageIcon(pieceIMG));
			}
			
			private class PieceSelectionListener implements MouseListener {

				@Override
				public void mouseClicked(MouseEvent e) {
					if(!icon.getText().equals("x0")) {
						setBackground(Color.WHITE);
					}
				}

				@Override
				public void mousePressed(MouseEvent e) {
					if(gameModel.getPlayer(whoseTurnIsIt).getNumberOfRank(rank) > 0) {
						if(pieceSelected != null) {
							gameModel.getPlayer(whoseTurnIsIt).addUnplacedPiece(pieceSelected.rank);
							panels.get(pieceSelected.getRank()).setBackground(Color.LIGHT_GRAY);
						}
						pieceSelected = new Piece(whoseTurnIsIt, rank);
					} else {
						JOptionPane.showMessageDialog(boardPanel, "You cannot select any more of these pieces.");
					}
				}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
					if(!icon.getText().equals("x0") && pieceSelected != null ? pieceSelected.getRank() !=  rank : false) {
						setBackground(Color.LIGHT_GRAY);
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(!icon.getText().equals("x0") && (pieceSelected != null ? pieceSelected.getRank() != rank : false)) {
						setBackground(Color.LIGHT_GRAY);
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
					pieceSelector.redrawPieces();
					boardPanel.repaint();
					optionPanel.cards.show(optionPanel, "player2");
				}else {
					JOptionPane.showMessageDialog(boardPanel, "You must place down all pieces on your side of the board before continuing.");
				}
			} else if(e.getActionCommand() == "shuffle") {
				gameModel.shuffle(whoseTurnIsIt);
				pieceSelector.redrawPieces();
				boardPanel.repaint();
			}
		}
	}
}
