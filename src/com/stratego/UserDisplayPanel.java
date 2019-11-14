package com.stratego;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UserDisplayPanel extends JPanel {
	Font playerFont;
	JLabel playNo = new JLabel();
	Font nameFont;
	JLabel name = new JLabel();
	JLabel playerIcon = new JLabel();
	
	UserDisplayPanel(int whoseTurnIsIt, Game gameModel) {
		
		changePlayer(whoseTurnIsIt, gameModel);
		
		JPanel innerContent = new JPanel();
		innerContent.setLayout(new BoxLayout(innerContent, BoxLayout.PAGE_AXIS));
		
			playerFont = new Font("Verdana", Font.PLAIN, 48);
			playNo.setFont(playerFont);
			playNo.setAlignmentX(CENTER_ALIGNMENT);
			innerContent.add(playNo, BorderLayout.NORTH);
			playerIcon.setAlignmentX(CENTER_ALIGNMENT);
			innerContent.add(playerIcon, BorderLayout.CENTER);
			nameFont = new Font("Verdana", Font.PLAIN, 36);
			name.setFont(nameFont);
			name.setAlignmentX(CENTER_ALIGNMENT);
			innerContent.add(name, BorderLayout.SOUTH);
		
		add(innerContent);
	}
	
	public void changePlayer(int whoseTurnIsIt, Game gameModel) {
		playNo.setText("Player " + Integer.toString(whoseTurnIsIt));
		name.setText(gameModel.getPlayer(whoseTurnIsIt).getPlayerName());
		playerIcon.setIcon(new ImageIcon(gameModel.getPlayer(whoseTurnIsIt).getImage("blank")));
	}
}
