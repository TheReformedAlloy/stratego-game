package com.stratego;

import java.awt.*;

import javax.swing.*;

public class UserDisplayPanel extends JPanel {
	Font playerFont;
	JLabel playNo = new JLabel();
	Font nameFont;
	JLabel name = new JLabel();
	JLabel playerIcon = new JLabel();
	
	UserDisplayPanel(int whoseTurnIsIt, Game gameModel) {		
		displayPlayer(whoseTurnIsIt, gameModel);
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		add(Box.createVerticalGlue());
		playerFont = new Font("Verdana", Font.PLAIN, 48);
		playNo.setFont(playerFont);
		playNo.setAlignmentX(CENTER_ALIGNMENT);
		add(playNo);
		playerIcon.setAlignmentX(CENTER_ALIGNMENT);
		add(playerIcon);
		nameFont = new Font("Verdana", Font.PLAIN, 36);
		name.setFont(nameFont);
		name.setAlignmentX(CENTER_ALIGNMENT);
		add(name);
		add(Box.createVerticalGlue());
	}
	
	public void displayPlayer(int whoseTurnIsIt, Game gameModel) {
		playNo.setText("Player " + Integer.toString(whoseTurnIsIt));
		name.setText(gameModel.getPlayer(whoseTurnIsIt).getPlayerName());
		playerIcon.setIcon(new ImageIcon(gameModel.getPlayer(whoseTurnIsIt).getImage("blank")));
	}
}
