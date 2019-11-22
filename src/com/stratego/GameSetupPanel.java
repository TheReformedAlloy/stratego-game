package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.LinkedHashMap;

import javax.swing.*;

public class GameSetupPanel extends JPanel {
	private static final long serialVersionUID = 1999846897104495339L;

	ActionListener butListener;
	
	Game gameModel;
	Piece pieceSelected;
	
	UserDisplayPanel userDisplayPanel;
	BoardPanel boardPanel;
	PieceSelectorPanel pieceSelector;
	OptionPanel optionPanel;
		
	GameSetupPanel(ActionListener butListener, Game gameModel){
		
		this.butListener = butListener;
		this.gameModel = gameModel;
		
		setBackground(TextureManager.getInstance().getColor("bg base"));
		
		pieceSelector = new PieceSelectorPanel();
		optionPanel = new OptionPanel();
		userDisplayPanel = new UserDisplayPanel(gameModel, optionPanel);
		
		setLayout(new BorderLayout());
		
		add(new MainPanel());
		
	}
	
	private void placePiece (int x, int y) {
		gameModel.getBoard().setLocation(x, y, pieceSelected);
		gameModel.getCurrentPlayer().subUnplacedPiece(pieceSelected.getRank());
		pieceSelector.panels.get(pieceSelected.getRank()).setBackground(TextureManager.getInstance().getColor("text high"));
		pieceSelected = null;
		pieceSelector.updatePieceCount();
	}
	
	private void removePiece (int x, int y) {
		gameModel.getCurrentPlayer().addUnplacedPiece(gameModel.getBoard().getGridLocation(x, y).getRank());
		gameModel.getBoard().setLocation(x, y, null);
		pieceSelector.updatePieceCount();
	}
	
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -4490157497028504961L;

		MainPanel(){
			setOpaque(false);
			setLayout(new GridBagLayout());
			
			GridBagConstraints leftPanelConstraints = new GridBagConstraints();
				leftPanelConstraints.gridx = 0;
				leftPanelConstraints.gridy = 0;
				leftPanelConstraints.gridwidth = 1;
				leftPanelConstraints.weightx = .125;
				leftPanelConstraints.weighty = 1;
				leftPanelConstraints.fill = GridBagConstraints.BOTH;
			add(userDisplayPanel, leftPanelConstraints);
			
			GridBagConstraints mPanelConstraints = new GridBagConstraints();
				mPanelConstraints.gridx = 1;
				mPanelConstraints.gridy = 0;
				mPanelConstraints.gridwidth = 6;
				mPanelConstraints.weightx = .875;
				mPanelConstraints.weighty = 1;
				mPanelConstraints.insets = new Insets(10, 10, 10, 10);
				mPanelConstraints.fill = GridBagConstraints.BOTH;
			boardPanel = new BoardPanel();
			add(boardPanel, mPanelConstraints);
			
			GridBagConstraints rightPanelConstraints = new GridBagConstraints();
				rightPanelConstraints.gridx = 7;
				rightPanelConstraints.gridy = 0;
				rightPanelConstraints.gridwidth = 2;
				rightPanelConstraints.weightx = .250;
				rightPanelConstraints.weighty = 1;
				rightPanelConstraints.insets = new Insets(10, 10, 10, 0);
				rightPanelConstraints.fill = GridBagConstraints.BOTH;
			JScrollPane pieceViewer = new JScrollPane(pieceSelector);
			pieceViewer.setOpaque(false);
			pieceViewer.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
				pieceViewer.setBorder(TextureManager.getInstance().getBorder("textdb"));
			add(pieceViewer, rightPanelConstraints);		
		}
		
	}
	
	private class BoardPanel extends JPanel {
		private static final long serialVersionUID = 2191062659801999504L;
		int boardWidth;
		int gridWidth;
		int pieceWidth;
		int boardHOffset;
		int boardVOffset;
		
		BoardPanel(){
			setOpaque(false);
			addMouseListener(new PiecePlacementListener());
			
			setBorder(TextureManager.getInstance().getBorder("boarddb"));
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
				
				if((clickX < 0 || clickX > 9) || (clickY < 0 || clickY > 9)) {
					JOptionPane.showMessageDialog(boardPanel, "Please select a location on the board.");
				} else if((gameModel.getWhoseTurn() == 1 && clickY < 6) || (gameModel.getWhoseTurn() == 2 && clickY > 3)) {
					JOptionPane.showMessageDialog(boardPanel, "Player " + gameModel.getWhoseTurn() + " cannot place a piece there on setup.");
				} else if(pieceSelected != null) {
					if(((clickX > 1 && clickX < 4) || (clickX > 5 && clickX < 8)) && (clickY > 3 && clickY < 6)) {
						JOptionPane.showMessageDialog(boardPanel, "Please select a location away from the water.");
					} else if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
						int allow = JOptionPane.showConfirmDialog(boardPanel, "Are you sure you want to replace this piece?", "Stratego | Replace Piece?", JOptionPane.YES_NO_OPTION);
						if(allow == JOptionPane.YES_OPTION) {
							gameModel.getCurrentPlayer().addUnplacedPiece(gameModel.getBoard().getGridLocation(clickX, clickY).getRank());
							placePiece(clickX, clickY);
							pieceSelector.updatePieceCount();
						}
					} else {
						placePiece(clickX, clickY);
					}
				} else {
					if(gameModel.getBoard().getGridLocation(clickX, clickY) != null) {
						int remove = JOptionPane.showConfirmDialog(boardPanel, "Are you sure you want to remove this piece?", "Stratego | Remove Piece?", JOptionPane.YES_NO_OPTION);
						if(remove == JOptionPane.YES_OPTION) {
							removePiece(clickX, clickY);
						}
					} else {
						JOptionPane.showMessageDialog(boardPanel, "Please select a piece to place!");
					}
				}
				
				userDisplayPanel.displayPiece(pieceSelected, false);
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
			super.paintComponent(g);
			
			boardWidth = (int) (getHeight() * .95);
			boardWidth -= boardWidth % 10;
			gridWidth = boardWidth / 10;
			pieceWidth = (int) (gridWidth);
			boardHOffset = (getWidth() - boardWidth) / 2;
			boardVOffset = (getHeight() - boardWidth) / 2;
			
			g.setColor(TextureManager.getInstance().getColor("border base"));
			g.fillRect(0, 0, getWidth(), getHeight());
			g.drawImage(TextureManager.getInstance().getImage("board"), boardHOffset, boardVOffset, boardWidth, boardWidth, null);
			
			for(int y = 0; y < 10; y++) {
				for(int x = 0; x < 10; x++) {
					Piece pieceAtLoc = gameModel.getBoard().getGridLocation(x, y);
					if(pieceAtLoc != null) {
						if(pieceAtLoc.getOwner() == gameModel.getWhoseTurn()) {
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
		private static final long serialVersionUID = -4903082359930665109L;
		LinkedHashMap<String, PiecePanel> panels = new LinkedHashMap<String, PiecePanel>();
		
		PieceSelectorPanel() {
			setOpaque(false);
			
			setLayout(new GridLayout(0, 1));
			
			for(String rank : Piece.ranks) {
				panels.put(rank, new PiecePanel(rank));
				add(panels.get(rank));
			}
		}
		
		public void updatePieceCount() {
			for(String rank : Piece.ranks) {
				int numLeft = gameModel.getCurrentPlayer().getNumberOfRank(rank);
				panels.get(rank).setNumLabel(numLeft);
			}
		};
		
		public void redrawPieces() {
			updatePieceCount();
			for(String rank : Piece.ranks) {
				panels.get(rank).setPieceImage(gameModel.getCurrentPlayer().getImage(rank));
			}
		}
		
		private class PiecePanel extends JPanel {
			private static final long serialVersionUID = -2618548198093147801L;
			JLabel icon;
			JLabel num;
			
			String rank;
			
			PiecePanel(String rank) {
				this.rank = rank;
				String rankCap = Character.toUpperCase(rank.charAt(0)) + rank.substring(1);
				
				setLayout(new GridLayout(1,2));
				
				addMouseListener(new PieceSelectionListener());
				
				setBackground(TextureManager.getInstance().getColor("text base"));
				
				icon = new JLabel(new ImageIcon(gameModel.getCurrentPlayer().getImage(rank)));
				add(icon);
				
				JPanel textPanel = new JPanel();
					textPanel.setOpaque(false);
					textPanel.setLayout(new GridLayout(2, 1));
					JLabel name = new JLabel(rankCap);
					textPanel.add(name);
					num = new JLabel("x" + Integer.toString(gameModel.getCurrentPlayer().getNumberOfRank(rank)));
					textPanel.add(num);
				add(textPanel);
			}
			
			public void setNumLabel(int numLeft) {
				if(numLeft == 0) {
					setBackground(TextureManager.getInstance().getColor("text low"));
				} else {
					setBackground(TextureManager.getInstance().getColor("text base"));
				}
				num.setText("x" + Integer.toString(numLeft));
			}
			
			public void setPieceImage(BufferedImage pieceIMG) {
				icon.setIcon(new ImageIcon(pieceIMG));
			}
			
			private class PieceSelectionListener implements MouseListener {

				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {
					if(gameModel.getCurrentPlayer().getNumberOfRank(rank) > 0) {
						if(pieceSelected != null) {
							gameModel.getCurrentPlayer().addUnplacedPiece(pieceSelected.rank);
							panels.get(pieceSelected.getRank()).setBackground(TextureManager.getInstance().getColor("text high"));
						}
						pieceSelected = new Piece(gameModel.whoseTurn, rank);
						setBackground(TextureManager.getInstance().getColor("text select"));
					} else {
						JOptionPane.showMessageDialog(boardPanel, "You cannot select any more of these pieces.");
					}
					
					userDisplayPanel.displayPiece(pieceSelected, false);
				}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {
					if(!num.getText().equals("x0") && (pieceSelected != null ? pieceSelected.getRank() !=  rank : true)) {
						setBackground(TextureManager.getInstance().getColor("text high"));
					}
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(!num.getText().equals("x0") && (pieceSelected != null ? pieceSelected.getRank() != rank : true)) {
						setBackground(TextureManager.getInstance().getColor("text base"));
					}
				}
			}
		}
	}
	
	private class OptionPanel extends JPanel {
		private static final long serialVersionUID = 3449413331716894116L;
		CardLayout cards;
		
		OptionPanel(){
			setOpaque(false);
			
			cards = new CardLayout();
			setLayout(cards);

			add(new Player1Panel(), "player1");
			add(new Player2Panel(), "player2");
			
			cards.show(this, "player1");
		}
		
		private class Player1Panel extends JPanel {
			private static final long serialVersionUID = -4189781202604281005L;

			Player1Panel() {
				setOpaque(false);
				
				setLayout(new GridLayout(4,1));
				
				GraphicButton shuffleButton = new GraphicButton("Shuffle Pieces");
				shuffleButton.setActionCommand("shuffle");
				shuffleButton.addActionListener(new TurnListener());
				add(shuffleButton);
				
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
		
		private class Player2Panel extends JPanel {
			private static final long serialVersionUID = 3990173560168580410L;

			Player2Panel() {
				setOpaque(false);
				
				setLayout(new GridLayout(4,1));
				
				GraphicButton shuffleButton = new GraphicButton("Shuffle Pieces");
				shuffleButton.setActionCommand("shuffle");
				shuffleButton.addActionListener(new TurnListener());
				add(shuffleButton);
				
				GraphicButton switchButton = new GraphicButton("Begin Game");
				switchButton.addActionListener(butListener);
				switchButton.setActionCommand("start_game");
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
				if(gameModel.getBoard().checkNumberOfPieces(gameModel.getWhoseTurn()) == 40) {
					gameModel.switchTurn();
					userDisplayPanel.displayPlayer();
					pieceSelector.redrawPieces();
					boardPanel.repaint();
					optionPanel.cards.show(optionPanel, "player2");
				}else {
					JOptionPane.showMessageDialog(boardPanel, "You must place down all pieces on your side of the board before continuing.");
				}
			} else if(e.getActionCommand() == "shuffle") {
				gameModel.shuffle();
				pieceSelector.updatePieceCount();
				boardPanel.repaint();
			}
		}
	}
}
