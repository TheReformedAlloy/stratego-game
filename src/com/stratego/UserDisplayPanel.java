package com.stratego;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class UserDisplayPanel extends JPanel {
	JPanel innerPanel;
	Font playerFont;
	JLabel playNo = new JLabel();
	Font nameFont;
	JLabel name = new JLabel();
	JLabel playerIcon = new JLabel();
	
	JPanel optionPanel;
	
	UserDisplayPanel(Game gameModel, JPanel optionPanel) {
		setOpaque(false);
		displayPlayer(gameModel);
		
		this.optionPanel = optionPanel;
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		Border padding = BorderFactory.createEmptyBorder(10, 10, 10, 10);
		setBorder(padding);
		
		add(Box.createVerticalStrut(50));
		innerPanel = new JPanel();
		innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.PAGE_AXIS));
			Border brownBevel = BorderFactory.createBevelBorder(BevelBorder.RAISED, new Color(157, 118, 93), new Color(125, 86, 61));
			Border brownBorder = BorderFactory.createLineBorder(new Color(157, 118, 93), 10);
			Border brownBevelBorder = BorderFactory.createCompoundBorder(brownBevel, brownBorder);
			innerPanel.setBorder(brownBevelBorder);
			innerPanel.setBackground(new Color(246, 214, 164));
			playerFont = new Font("Verdana", Font.PLAIN, 48);
			playNo.setFont(playerFont);
			playNo.setAlignmentX(CENTER_ALIGNMENT);
			innerPanel.add(playNo);
			playerIcon.setAlignmentX(CENTER_ALIGNMENT);
			innerPanel.add(playerIcon);
			nameFont = new Font("Verdana", Font.PLAIN, 36);
			name.setFont(nameFont);
			name.setAlignmentX(CENTER_ALIGNMENT);
			innerPanel.add(name);
			innerPanel.add(optionPanel);
		add(innerPanel);
		add(Box.createVerticalStrut(20));
		add(this.optionPanel);
		add(Box.createVerticalStrut(10));
	}
	
	public void displayPlayer(Game gameModel) {
		playNo.setText("Player " + Integer.toString(gameModel.whoseTurn));
		name.setText(gameModel.getCurrentPlayer().getPlayerName());
		playerIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage("player")));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
	}
}
