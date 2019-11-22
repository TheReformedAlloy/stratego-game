package com.stratego;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

public class UserDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1070856373548483936L;
	Game gameModel;
	
	JPanel userPanel;
	Font playerFont;
	JLabel playNo = new JLabel();
	Font nameFont;
	JLabel name = new JLabel();
	JLabel playerIcon = new JLabel();
	
	JPanel optionPanel;
	
	JPanel piecePanel;
	JLabel selectedIcon;
	JLabel selectedName;
	
	UserDisplayPanel(Game gameModel, JPanel optionPanel) {
		setOpaque(false);
		this.gameModel = gameModel; 
		
		displayPlayer();
		
		this.optionPanel = optionPanel;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(padding);
		
		add(Box.createVerticalGlue());
		userPanel = new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.PAGE_AXIS));
			userPanel.setBorder(TextureManager.getInstance().getBorder("textdb"));
			userPanel.setBackground(TextureManager.getInstance().getColor("text base"));
			playerFont = new Font("Verdana", Font.PLAIN, 48);
			playNo.setFont(playerFont);
			playNo.setAlignmentX(CENTER_ALIGNMENT);
			userPanel.add(playNo);
			playerIcon.setAlignmentX(CENTER_ALIGNMENT);
			userPanel.add(playerIcon);
			nameFont = new Font("Verdana", Font.PLAIN, 36);
			name.setFont(nameFont);
			name.setAlignmentX(CENTER_ALIGNMENT);
			userPanel.add(name);
			userPanel.add(optionPanel);
		add(userPanel);
		add(Box.createVerticalStrut(20));
		add(this.optionPanel);
		add(Box.createVerticalStrut(20));
		piecePanel = new JPanel();
		piecePanel.setLayout(new BoxLayout(piecePanel, BoxLayout.PAGE_AXIS));
			piecePanel.setBorder(TextureManager.getInstance().getBorder("textdb"));
			piecePanel.setBackground(TextureManager.getInstance().getColor("text base"));
			JLabel selected = new JLabel("Selected:");
			selected.setFont(new Font("Verdana", Font.PLAIN, 36));
			selected.setAlignmentX(CENTER_ALIGNMENT);
			piecePanel.add(selected);
			selectedIcon = new JLabel(new ImageIcon(gameModel.getCurrentPlayer().getImage("blank")));
			selectedIcon.setAlignmentX(CENTER_ALIGNMENT);
			piecePanel.add(selectedIcon);
			selectedName = new JLabel("N/A");
			selectedName.setFont(new Font(("Verdana"), Font.PLAIN, 24));
			selectedName.setAlignmentX(CENTER_ALIGNMENT);
			piecePanel.add(selectedName);
		add(piecePanel);
		add(Box.createVerticalGlue());
	}
	
	public void displayPlayer() {
		playNo.setText("Player " + Integer.toString(gameModel.whoseTurn));
		name.setText(gameModel.getCurrentPlayer().getPlayerName());
		playerIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage("player")));
	}
	
	public void displayPiece(Piece selectedPiece) {
		if(selectedPiece != null) {
			selectedIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage(selectedPiece.getRank())));
			selectedName.setText(selectedPiece.getRank());
		} else {
			selectedIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage("blank")));
			selectedName.setText("N/A");
		}
	}
}
