package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class GameInitPanel extends BackgroundPanel{
	
	ActionListener submitListener;
	
	GamepieceColorChooser leftPanel;
	
	Color player1Color;
	String player1Name;
	BufferedImage player1Image;
	
	GamepieceColorChooser rightPanel;
	
	Color player2Color;
	String player2Name;
	BufferedImage player2Image;
	
	GameInitPanel(ActionListener submitListener) {
		super();
		
		setLayout(new BorderLayout());
		
		this.submitListener = submitListener;
		
		player1Name = "John Doe";
		player2Name = "Mary Sue";
		
		player1Color = Color.red;
		player2Color = Color.blue;
		
		add(new MainPanel());
		add(new EmptyPanel(), BorderLayout.SOUTH);
	}
	
	private class MainPanel extends EmptyPanel {
		
		MainPanel(){
			super();
			
			setLayout(new GridBagLayout());
			
			GridBagConstraints cPanelConstraints = new GridBagConstraints();
				cPanelConstraints.gridx = 0;
				cPanelConstraints.gridy = 0;
				cPanelConstraints.gridheight = 4;
				cPanelConstraints.gridwidth = 1;
				cPanelConstraints.weighty = 1;
			MiddlePanel colorPanel = new MiddlePanel();
			add(colorPanel, cPanelConstraints);
			
			GridBagConstraints bPanelConstraints = new GridBagConstraints();
				bPanelConstraints.gridx = 0;
				bPanelConstraints.gridy = 4;
				bPanelConstraints.gridheight = 1;
				bPanelConstraints.gridwidth = 1;
				bPanelConstraints.weighty = 1;
				bPanelConstraints.fill = GridBagConstraints.BOTH;
			add(new BottomPanel(), bPanelConstraints);
		}
		
	}
	
	private class MiddlePanel extends JPanel {
		MiddlePanel() {
			GridLayout panelLayout = new GridLayout(1,2);
			panelLayout.setHgap(20);
			setLayout(panelLayout);
			
			setOpaque(false);
			
			leftPanel = new GamepieceColorChooser(player1Name, player1Color);
			add(leftPanel);
			
			rightPanel = new GamepieceColorChooser(player2Name, player2Color);
			add(rightPanel);
		}
	}
	
	private class BottomPanel extends JPanel {
		BottomPanel() {
			setLayout(new BorderLayout());
			
			setOpaque(false);
			
			GraphicButton submitButton = new GraphicButton("Submit", this.getWidth(), this.getHeight());
			submitButton.setActionCommand("start_setup");
			submitButton.addActionListener(new PlayerListener());
			submitButton.addActionListener(submitListener);
			add(submitButton);
		}
	}
	
	public Player getPlayer1() {
		return new Player(player1Color, player1Name, 1, player1Image);
	}
	
	public Player getPlayer2() {
		return new Player(player2Color, player2Name, 2, player2Image);
	}
	
	private class PlayerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			player1Color = leftPanel.getColor();
			player1Image = leftPanel.getImage();
			player1Name = leftPanel.getPlayerName();
			
			player2Color = rightPanel.getColor();
			player2Image = rightPanel.getImage();
			player2Name = rightPanel.getPlayerName();
		}
		
	}
}
