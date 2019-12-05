package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.util.LinkedHashMap;

import javax.swing.*;

/**
 * Displays the board and the unplaced pieces of the current player and allows the player to place these unplaced pieces in an arrangment
 * of their choice on their side of the board.
 * 
 * @author Clint Mooney
 *
 */
public class GameSetupPanel extends JPanel {
	private static final long serialVersionUID = 1999846897104495339L;

	/** Listens to button presses and performs operations based on the buttons' <code>actionCommand</code>.*/
	ActionListener butListener;
	
	/** A reference to an instance of the Game class representing the model used for display.*/
	Game gameModel;
	/** A reference to the currently selected piece.*/
	Piece pieceSelected;
	
	/** Displays the current player's information.*/
	UserDisplayPanel userDisplayPanel;
	/** Displays the board and its pieces.*/
	BoardPanel boardPanel;
	/** Displays a player's currently unplaced pieces.*/
	PieceSelectorPanel pieceSelector;
	/** Refers to a number of buttons a player can use to change the game state.*/
	OptionPanel optionPanel;
		
	/**
	 * Creates a GameSetupPanel with a reference to the parent <code>ActionListener</code> and <code>Game</code>.
	 * 
	 * @param butListener {@link GameSetupPanel#butListener}
	 * @param gameModel {@link GameSetupPanel#gameModel}
	 */
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
	
	/**
	 * Places the <code>pieceSelected</code> at a place on the <code>gameModel</code>'s board.
	 * 
	 * @param x the x-axis position at which <code>pieceSelected</code> will be placed.
	 * @param y the y-axis position at which <code>pieceSelected</code> will be placed.
	 */
	private void placePiece (int x, int y) {
		gameModel.getBoard().setLocation(x, y, pieceSelected);
		gameModel.getCurrentPlayer().subUnplacedPiece(pieceSelected.getRank());
		pieceSelector.panels.get(pieceSelected.getRank()).setBackground(TextureManager.getInstance().getColor("text high"));
		pieceSelected = null;
		pieceSelector.updatePieceCount();
	}
	
	/**
	 * Removes a piece from a location on the <code>gameModel</code>'s board.
	 * 
	 * @param x the x-axis position at which a piece will be removed.
	 * @param y the y-axis position at which a piece will be removed.
	 */
	private void removePiece (int x, int y) {
		gameModel.getCurrentPlayer().addUnplacedPiece(gameModel.getBoard().getGridLocation(x, y).getRank());
		gameModel.getBoard().setLocation(x, y, null);
		pieceSelector.updatePieceCount();
	}
	
	/**
	 * Displays and organizes the contents of the panel.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -4490157497028504961L;

		/**
		 * Creates a default <code>MainPanel</code>.
		 */
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
	
	/**
	 * A panel to display the <code>gameModel</code>'s board.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class BoardPanel extends JPanel {
		private static final long serialVersionUID = 2191062659801999504L;
		
		/** Stores the width of the board, which is used to display the image for the game board using this as its width and height.*/
		int boardWidth;
		/** Refers to the width of the individual squares on the game board on the screen.*/
		int gridWidth;
		/** Refers to the width of piece icons when they are drawn on the board.*/
		int pieceWidth;
		/** Refers to how far inset the board should be displayed horizontally.*/
		int boardHOffset;
		/** Refers to how far inset the board should be displayed vertically.*/
		int boardVOffset;
		
		/**
		 * Creates a BoardPanel.
		 */
		BoardPanel(){
			setOpaque(false);
			addMouseListener(new PiecePlacementListener());
			
			setBorder(TextureManager.getInstance().getBorder("boarddb"));
		}
		
		/**
		 * A MouseListener to interpret where the user is interacting with the board.
		 * 
		 * @author Clint Mooney
		 *
		 */
		private class PiecePlacementListener implements MouseListener {
			/** 
			 * Required override from <code>MouseListener</code> (Unused).
			 */
			@Override
			public void mouseClicked(MouseEvent e) {}
			
			/**
			 * Interprets the player's click to attempt to select the piece at the clicked location.
			 */
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
			
			/** 
			 * Required override from <code>MouseListener</code> (Unused).
			 */
			@Override
			public void mouseReleased(MouseEvent e) {}
			
			/** 
			 * Required override from <code>MouseListener</code> (Unused).
			 */
			@Override
			public void mouseEntered(MouseEvent e) {}
			
			/** 
			 * Required override from <code>MouseListener</code> (Unused).
			 */
			@Override
			public void mouseExited(MouseEvent e) {}
			
		}
		
		/**
		 * Draws the board and the pieces currently placed on the board.
		 */
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
	
	/**
	 * Displays a player's currently unplaced pieces and allows the player to select a piece to be placed on the board.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class PieceSelectorPanel extends JPanel {
		private static final long serialVersionUID = -4903082359930665109L;
		
		/** Contains a panel for each type of piece that a player can use.*/
		LinkedHashMap<String, PiecePanel> panels = new LinkedHashMap<String, PiecePanel>();
		
		/** Creates a default PieceSelectorPanel.*/
		PieceSelectorPanel() {
			setOpaque(false);
			
			setLayout(new GridLayout(0, 1));
			
			for(String rank : Piece.ranks) {
				panels.put(rank, new PiecePanel(rank));
				add(panels.get(rank));
			}
		}
		
		/**
		 * Displays the number of pieces that can still be placed in each corresponding <code>PiecePanel</code> of
		 * <code>panels</code>.
		 */
		public void updatePieceCount() {
			for(String rank : Piece.ranks) {
				int numLeft = gameModel.getCurrentPlayer().getNumberOfRank(rank);
				panels.get(rank).setNumLabel(numLeft);
			}
		};
		
		/**
		 * Displays the image that should be shown in each corresponding <code>PiecePanel</code> of <code>panels</code>.
		 */
		public void redrawPieces() {
			updatePieceCount();
			for(String rank : Piece.ranks) {
				panels.get(rank).setPieceImage(gameModel.getCurrentPlayer().getImage(rank));
			}
		}
		
		/**
		 * Displays a piece, its rank name, and the number of these pieces that can be placed.
		 * 
		 * @author Clint Mooney
		 *
		 */
		private class PiecePanel extends JPanel {
			private static final long serialVersionUID = -2618548198093147801L;

			/** Displays the image for a piece.*/
			JLabel icon;
			/** Displays the number of the piece that can placed.*/
			JLabel num;
			
			/** Contains the rank of the piece.*/
			String rank;
			
			/**
			 * Creates a PiecePanel corresponding to a certain type of piece.
			 * 
			 * @param rank denotes the type of piece to display.
			 */
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
			
			/**
			 * Replaces the number of pieces of this <code>PiecePanel</code> to the inputted value and changes the color based on whether
			 * pieces can still be placed.
			 * 
			 * @param numLeft refers to the number of pieces which can still be placed.
			 */
			public void setNumLabel(int numLeft) {
				if(numLeft == 0) {
					setBackground(TextureManager.getInstance().getColor("text low"));
				} else {
					setBackground(TextureManager.getInstance().getColor("text base"));
				}
				num.setText("x" + Integer.toString(numLeft));
			}
			
			/**
			 * Sets the image displayed in <code>icon</code> to a new image.
			 * 
			 * @param pieceIMG the image to be displayed.
			 */
			public void setPieceImage(BufferedImage pieceIMG) {
				icon.setIcon(new ImageIcon(pieceIMG));
			}
			
			/**
			 * Listens for interaction with <code>PiecePanel</code>.
			 * 
			 * @author Clint Mooney
			 *
			 */
			private class PieceSelectionListener implements MouseListener {
				/** 
				 * Required override from <code>MouseListener</code> (Unused).
				 */
				@Override
				public void mouseClicked(MouseEvent e) {}

				/**
				 * When the left mouse is pressed on a <code>PiecePanel</code>, the corresponding <code>Piece</code> is
				 * made the value of <code>pieceSelected</code>. If there are none left, an error is shown.
				 */
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
				/** 
				 * Required override from <code>MouseListener</code> (Unused).
				 */
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				/**
				 * Highlights the <code>PiecePanel</code> if it is able to be selected when the mouse enters its bounds.
				 */
				@Override
				public void mouseEntered(MouseEvent e) {
					if(!num.getText().equals("x0") && (pieceSelected != null ? pieceSelected.getRank() !=  rank : true)) {
						setBackground(TextureManager.getInstance().getColor("text high"));
					}
				}
				/**
				 * De-highlights the <code>PiecePanel</code> if it is able to be selected when the mouse enters its bounds.
				 */
				@Override
				public void mouseExited(MouseEvent e) {
					if(!num.getText().equals("x0") && (pieceSelected != null ? pieceSelected.getRank() != rank : true)) {
						setBackground(TextureManager.getInstance().getColor("text base"));
					}
				}
			}
		}
	}
	
	/**
	 * Displays buttons for the user to interact with to change the game state or switch turns.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class OptionPanel extends JPanel {
		private static final long serialVersionUID = 3449413331716894116L;
		
		/** Allows the <code>OptionPanel</code> to switch between its internal panels.*/
		CardLayout cards;
		
		/**
		 * Creates a default <code>OptionPanel</code>.
		 */
		OptionPanel(){
			setOpaque(false);
			
			cards = new CardLayout();
			setLayout(cards);

			add(new Player1Panel(), "player1");
			add(new Player2Panel(), "player2");
			
			cards.show(this, "player1");
		}
		
		/**
		 * Displays options available for Player One.
		 * 
		 * @author Clint Mooney
		 *
		 */
		private class Player1Panel extends JPanel {
			private static final long serialVersionUID = -4189781202604281005L;

			/** Creates a default <code>Player1Panel</code>.*/
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
		
		/**
		 * Displays options available for Player Two.
		 * 
		 * @author Clint Mooney
		 *
		 */
		private class Player2Panel extends JPanel {
			private static final long serialVersionUID = 3990173560168580410L;

			/** Creates a default <code>Player2Panel</code>.*/
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
	
	/**
	 * Listens for the user to press a button to switch turns and thus change the internal panels to display the opposite
	 * player's information.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class TurnListener implements ActionListener {
		/**
		 * Switches the turn if the player presses the button indicated or shuffles the pieces on the board if requested.
		 */
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
