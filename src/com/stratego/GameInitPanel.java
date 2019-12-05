package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * Displays two panels to select a color and name to be associated with each player.
 * 
 * @author Clint Mooney
 *
 */
public class GameInitPanel extends GraphicPanel{
	private static final long serialVersionUID = 1955627489428781035L;

	/** Listens to button presses and performs operations based on the buttons' <code>actionCommand</code>.*/
	ActionListener submitListener;
	
	/** A panel to choose a color for player one.*/
	GamepieceColorChooser leftPanel;
	
	/** Stores player one's currently selected color.*/
	Color player1Color;
	/** Stores player one's name.*/
	String player1Name;
	/** Stores the image created for player one.*/
	BufferedImage player1Image;
	
	/** A panel to choose a color for player two.*/
	GamepieceColorChooser rightPanel;

	/** Stores player two's currently selected color.*/
	Color player2Color;
	/** Stores player one's name.*/
	String player2Name;
	/** Stores the image created for player one.*/
	BufferedImage player2Image;
	
	/**
	 * Creates a GameInitPanel with a reference to the parent <code>ActionListener</code>.
	 * 
	 * @param submitListener {@link GameInitPanel#submitListener}
	 */
	GameInitPanel(ActionListener submitListener) {
		super(TextureManager.getInstance().getImage("background"));
		
		setLayout(new BorderLayout());
		
		this.submitListener = submitListener;
		
		player1Name = "John Doe";
		player2Name = "Mary Sue";
		
		player1Color = Color.red;
		player2Color = Color.blue;
		
		add(new MainPanel());
		
		add(Box.createGlue(), BorderLayout.SOUTH);
	}
	
	/**
	 * Displays and organizes the two players' setup panels.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class MainPanel extends JPanel {
		private static final long serialVersionUID = -9221511935291397389L;

		MainPanel(){
			setOpaque(false);
			
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
		private static final long serialVersionUID = 1050328621738298057L;

		/**
		 * Creates a default <code>MiddlePanel</code>.
		 */
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
	
	/**
	 * Displays a button to submit the user information.
	 * 
	 * @author there
	 *
	 */
	
	private class BottomPanel extends JPanel {
		private static final long serialVersionUID = 6381609899320147379L;

		/**
		 * Creates a default <code>BottomPanel</code>.
		 */
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
