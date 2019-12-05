package com.stratego;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.Border;

/**
 * A <code>JPanel</code> used to display a <code>Player</code>'s information on the left side of the <code>GameView</code>,
 * corresponding to the player whose turn it is.
 * 
 * @author Clint Mooney
 *
 */
public class UserDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1070856373548483936L;
	/** Contains a reference to the <code>Game</code> object which contains the relevant player information.*/
	Game gameModel;
	
	/** An internal panel to hold visual components about the player and buttons which could be pressed.*/
	JPanel userPanel;
	/** The font to be used for displaying the player and their turn order.*/
	Font playerFont;
	/** The <code>JComponent</code> which will contain the player and their turn order.*/
	JLabel playNo = new JLabel();
	/** The font to be used for displaying the player's name.*/
	Font nameFont;
	/** The <code>JComponent</code> which will contain the player's name.*/
	JLabel name = new JLabel();
	/** The <code>JComponent</code> which will contain the player's <code>playerPieceImage</code>.*/
	JLabel playerIcon = new JLabel();
	
	/** An internal panel to organize buttons which could be pressed by the player.*/
	JPanel optionPanel;
	
	/** An internal panel to display which piece has been selected by a player.*/
	JPanel piecePanel;
	/** Displays information about whether a piece was selected.*/
	JLabel selected;
	/** Displays the actual image of the piece which was selected.*/
	JLabel selectedIcon;
	/** Displays the rank of the piece selected.*/
	JLabel selectedName;
	
	/**
	 * Creates a UserDisplayPanel.
	 * 
	 * @param gameModel {@link UserDisplayPanel#gameModel}
	 * @param optionPanel {@link UserDisplayPanel#optionPanel}
	 */
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
			selected = new JLabel("Selected:");
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
	
	/**
	 * Updates the information displayed to represent the player whose turn it currently is in <code>gameModel</code>.
	 */
	public void displayPlayer() {
		playNo.setText("Player " + Integer.toString(gameModel.whoseTurn));
		name.setText(gameModel.getCurrentPlayer().getPlayerName());
		playerIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage("player")));
	}
	
	
	/**
	 * Updates the information displayed to represent the piece which was most recently selected.
	 * 
	 * @param selectedPiece refers to the <code>Piece</code> last selected by the user.
	 * @param wasAttacked indicates whether the selection was an attack.
	 */
	public void displayPiece(Piece selectedPiece, boolean wasAttacked) {
		if(selectedPiece != null) {
			selectedIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage(selectedPiece.getRank())));
			selectedName.setText(selectedPiece.getRank());
		} else {
			selectedIcon.setIcon(new ImageIcon(gameModel.getCurrentPlayer().getImage("blank")));
			selectedName.setText("N/A");
		}
		
		if(wasAttacked) {
			selected.setText("Attacker:");
		} else {
			selected.setText("Selected:");
		}
	}
}
