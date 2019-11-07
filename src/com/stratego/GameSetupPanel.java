package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class GameSetupPanel extends BackgroundPanel {
	
	int whoseTurnIsIt;
	
	ActionListener butListener;
	
	Game gameModel;
	
	Piece pieceSelected;
		
	GameSetupPanel(ActionListener butListener, Game gameModel){
		
		this.butListener = butListener;
		this.gameModel = gameModel;
		
		whoseTurnIsIt = 1;
		
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
				leftPanelConstraints.weighty = 7;
				leftPanelConstraints.weightx = 1;
				leftPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new JPanel(), leftPanelConstraints);
			
			GridBagConstraints mPanelConstraints = new GridBagConstraints();
				mPanelConstraints.gridx = 1;
				mPanelConstraints.gridy = 0;
				mPanelConstraints.weighty = 7;
				mPanelConstraints.weightx = 6;
				mPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new GamePanel(), mPanelConstraints);
			
			GridBagConstraints rightPanelConstraints = new GridBagConstraints();
				rightPanelConstraints.gridx = 6;
				rightPanelConstraints.gridy = 0;
				rightPanelConstraints.weighty = 7;
				rightPanelConstraints.weightx = 2;
				rightPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new JScrollPane(new PieceSelectorPanel()), rightPanelConstraints);
			
			GridBagConstraints bPanelConstraints = new GridBagConstraints();
				bPanelConstraints.gridx = 0;
				bPanelConstraints.gridy = 7;
				bPanelConstraints.gridwidth = 8;
				bPanelConstraints.weighty = 1;
				bPanelConstraints.weightx = 8;
				bPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new BottomPanel(), bPanelConstraints);
			
			GridBagConstraints spacerConstraints = new GridBagConstraints();
				spacerConstraints.gridx = 0;
				spacerConstraints.gridy = 8;
				spacerConstraints.gridwidth = 8;
				spacerConstraints.weighty = 0.25;
				spacerConstraints.weightx = 8;
				spacerConstraints.fill = GridBagConstraints.BOTH;
			add(new JPanel(), spacerConstraints);
				
		}
		
	}
	
	private class GamePanel extends JPanel {
		
		GamePanel() {
			setLayout(new BorderLayout());
			
			add(new BoardPanel(), BorderLayout.CENTER);
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
				
				if(pieceSelected != null) {
					System.out.println("Adding to board to " + clickX + ", " + clickY);
					gameModel.getBoard().setLocation(clickX, clickY, pieceSelected);
					pieceSelected = null;
					repaint();
				}else {
					System.out.println("Empty click at " + clickX + " : " + clickY);
				}
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
			gridWidth = boardWidth / 10;
			pieceWidth = (int) (gridWidth * .9);
			boardHOffset = (getWidth() - boardWidth) / 2;
			boardVOffset = (getHeight() - boardWidth) / 2;
			
			g.drawImage(TextureManager.BOARD, (getWidth() - boardWidth) / 2, (int) (getHeight() * .05), boardWidth, boardWidth, null);
			
			for(int y = 0; y < 10; y++) {
				for(int x = 0; x < 10; x++) {
					Piece pieceAtLoc = gameModel.getBoard().getGridLocation(y, x);
					if(pieceAtLoc != null) {
						if(pieceAtLoc.getOwner() == whoseTurnIsIt) {
							g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage(pieceAtLoc.getRank()), boardHOffset + gridWidth * x + gridWidth / 10, boardVOffset + gridWidth * y + gridWidth / 10, pieceWidth, pieceWidth,  null);
						} else {
							g.drawImage(gameModel.getPlayer(pieceAtLoc.getOwner()).getImage("blank"), boardHOffset + gridWidth * x + gridWidth / 10, boardVOffset + gridWidth * y + gridWidth / 10, pieceWidth, pieceWidth,  null);
						}
					}
				}
			}
		}
	}
	
	private class PieceSelectorPanel extends JPanel {
		
		PieceSelectorPanel() {
			setOpaque(false);
			
			setLayout(new GridLayout(0, 1));
			
			for(String rank : Piece.ranks.keySet()) {
				add(new PiecePanel(rank));
			}
		}
		
		private class PiecePanel extends JPanel {
			
			String rank;
			
			PiecePanel(String rank) {
				this.rank = rank;
				
				setOpaque(false);
				
				addMouseListener(new PieceSelectionListener());
				
				JLabel icon = new JLabel(new ImageIcon(gameModel.getPlayer(whoseTurnIsIt).getImage(rank)));
				add(icon);
				
				JLabel num = new JLabel("x" + Integer.toString(gameModel.getPlayer(whoseTurnIsIt).getNumberOfRank(rank)));
				add(num);
			}
			
			private class PieceSelectionListener implements MouseListener {

				@Override
				public void mouseClicked(MouseEvent e) {}

				@Override
				public void mousePressed(MouseEvent e) {
					if(pieceSelected != null) {
						System.out.println("Changing piece selected.");
						gameModel.getPlayer(whoseTurnIsIt).addUnplacedPiece(pieceSelected.rank);
					}
					System.out.println("Chose " + rank);
					pieceSelected = new Piece(whoseTurnIsIt, rank);
					gameModel.getPlayer(whoseTurnIsIt).subUnplacedPiece(rank);
				}

				@Override
				public void mouseReleased(MouseEvent e) {}

				@Override
				public void mouseEntered(MouseEvent e) {}

				@Override
				public void mouseExited(MouseEvent e) {}
			}
		}
	}
	
	private class BottomPanel extends JPanel {
		BottomPanel(){
			setLayout(new BorderLayout());
			
			setOpaque(false);
			
			GraphicButton exitButton = new GraphicButton("Exit Game", this.getWidth(), this.getHeight());
			exitButton.setActionCommand("main_menu");
			exitButton.addActionListener(GameDriver.getInstance().getStateChangeListener());
			add(exitButton);
		}
	}
	
	private void placePiece (int x, int y) {
		gameModel.getBoard().setLocation(x, y, pieceSelected);
		gameModel.getPlayer(whoseTurnIsIt).subUnplacedPiece(pieceSelected.getRank());
	}
	
	private class TurnListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand() == "end_turn") {
				whoseTurnIsIt = 2;
			}
		}
	}
}
