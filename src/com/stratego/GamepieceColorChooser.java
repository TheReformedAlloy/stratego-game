package com.stratego;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;

/**
 * Allows a player to select a color to use to paint the template piece image with.
 * 
 * @author Clint Mooney
 *
 */
public class GamepieceColorChooser extends JPanel{
	private static final long serialVersionUID = -5857658494711191520L;
	
	/** Displays a custom color chooser to allow player to select a new color for their pieces.*/
	ColorPanel colorPanel;
	/** Displays a text control to allow the user to enter a name to be displayed.*/
	NamePanel namePanel;

	/** Stores the currently selected <code>Color</code>*/
	Color currentColor;
	/** Stores the name currently entered by the player.*/
	String currentName;
	
	/**
	 * Creates a GamepieceColorChooser for a player with a default name and color.
	 * 
	 * @param defaultName specifies the name entered into <code>namePanel</code> when it is initialized.
	 * @param playerColor specifies the color a piece should should be when the <code>ColorPanel</code> is initialized.
	 */
	GamepieceColorChooser(String defaultName, Color playerColor){
		super();
		
		currentName = defaultName;
		this.currentColor = playerColor;
		
		setLayout(new BorderLayout());
		
		
		setBackground(TextureManager.getInstance().getColor("text base"));
		setBorder(TextureManager.getInstance().getBorder("textdb"));
		
		colorPanel = new ColorPanel();
		
		add(colorPanel, BorderLayout.CENTER);
		namePanel = new NamePanel();
		add(namePanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
		
	/**
	 * Displays a custom color chooser to allow player to select a new color for their pieces.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class ColorPanel extends JPanel {
		private static final long serialVersionUID = -3733483172987839025L;
		
		/** The interface which provides a panel to choose the color to be used.*/
		JColorChooser customColor;
		/** Displays an example of a piece using the color selected.*/
		PreviewIcon customIcon;
		
		/**
		 * Creates a default <code>ColorPanel</code>.
		 */
		ColorPanel() {
			setOpaque(false);
			
			setLayout(new GridLayout(2, 1));
			
			customIcon = new PreviewIcon();
			add(customIcon);
			
			customColor = new JColorChooser();
			customColor.setColor(currentColor);
			customColor.setOpaque(false);
			
			AbstractColorChooserPanel clearPanel = ColorChooserComponentFactory.getDefaultChooserPanels()[3];
			clearPanel.setBackground(TextureManager.getInstance().getColor("text base"));
			AbstractColorChooserPanel[] colorPanels = {clearPanel};
			
			customColor.setChooserPanels(colorPanels);
			customColor.setPreviewPanel(new JPanel());
			customColor.getSelectionModel().addChangeListener(new CustomChangeListener());
			add(customColor);
		}
		
		/**
		 * Displays an example of a piece using the color selected.
		 * 
		 * @author Clint Mooney
		 *
		 */
		private class PreviewIcon extends JPanel{
			private static final long serialVersionUID = -2179382022452974454L;
			
			/** Contains an instance of the gray piece image to be hue-shifted.*/
			BufferedImage normalPiece;
			/** The image hue-shifted to the currently selected color.*/
			BufferedImage currentPiece;

			/**
			 * Creates a default <code>PreviewIcon</code>.
			 */
			PreviewIcon() {
				normalPiece = TextureManager.returnGrayPiece();
				currentPiece = TextureManager.returnGrayPiece();
				updateColor(currentColor);
				setOpaque(false);
			}
			
			/**
			 * Changes the color of <code>currentPiece</code> to a different color.
			 * 
			 * @param newColor specifies the color to be hue-shifted to.
			 */
			public void updateColor(Color newColor) {
				short redVal = (short) newColor.getRed();
				short greenVal = (short) newColor.getGreen();
				short blueVal = (short) newColor.getBlue();
				
				short[] red = new short[256];
				short[] green = new short[256];
				short[] blue = new short[256];
				short[] alpha = new short[256];
				for(short i = 0; i < 255; i++) {
					red[i] = (short) ((i / 255f) * redVal);
					green[i] = (short) ((i / 255f) * greenVal);
					blue[i] = (short) ((i / 255f) * blueVal);
					alpha[i] = i;
				}
				red[255] = 255;
				blue[255] = 255;
				green[255] = 255;
				alpha[255] = 255;
				
				short[][] rgb = new short[][]{
						red, green, blue, alpha
				};
				
				LookupTable conversion = new ShortLookupTable(0, rgb);
				LookupOp op = new LookupOp(conversion, null);
				op.filter(normalPiece, currentPiece);
				repaint();
			}
			
			/**
			 * Draws the currently hue-shifted image.
			 */
			@Override
			public void paintComponent(Graphics g){
				super.paintComponent(g);
				int boxHeight = (int) (getHeight() * .9);
				int boxStart = (getWidth() - boxHeight) / 2;
				
				g.setColor(Color.lightGray);
				g.fillRect(boxStart, 10, boxHeight, boxHeight);
				g.setColor(Color.black);
				g.drawRect(boxStart, 10, boxHeight, boxHeight);
				g.drawImage(currentPiece, boxStart, 8, boxHeight, boxHeight, null);
			}
			
			/**
			 * Gets the current image to serve as the base of the piece.
			 * 
			 * @return <code>GamepieceColorChooser.colorPanel.customIcon</code>.
			 */
			public BufferedImage getCurrentImage() {
				return currentPiece;
			}
		}
	}

	/**
	 * Displays a text control to allow the user to enter a name to be displayed.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class NamePanel extends JPanel {
		private static final long serialVersionUID = -4942806878162261270L;
		
		/**The text control in which the name of the player is entered.*/
		JTextField nameField;
		
		/**
		 * Creates a default <code>NamePanel</code>.
		 */
		NamePanel(){
			setOpaque(false);
			JLabel nameLabel = new JLabel("Name: ");
			nameLabel.setFont(new Font("Verdana", Font.PLAIN, 36));
			add(nameLabel);
			nameField = new JTextField(currentName, 10);
			nameField.setFont(new Font("Verdana", Font.PLAIN, 36));
			nameField.setHorizontalAlignment(JTextField.CENTER);
			add(nameField);
		}
	}
	
	/**
	 * Returns the current color selected.
	 * 
	 * @return {@link GamepieceColorChooser#colorPanel}
	 */
	public Color getColor() {
		return currentColor;
	}
	
	/**
	 * Returns the currently entered name.
	 * 
	 * @return {@link GamepieceColorChooser#currentName}
	 */
	public String getPlayerName() {
		return namePanel.nameField.getText();
	}
	
	/**
	 * Returns the currently hue-shifted image.
	 * 
	 * @return <code>GamepieceColorChooser.colorPanel.customIcon</code>.
	 */
	public BufferedImage getImage() {
		return colorPanel.customIcon.getCurrentImage();
	}

	/**
	 * Listens for changes in the color selection panel.
	 * 
	 * @author Clint Mooney
	 *
	 */
	private class CustomChangeListener implements ChangeListener {
		/**
		 * Updates the <code>ColorPanel.PreviewIcon</code> when a color is selected.
		 */
		@Override
		public void stateChanged(ChangeEvent e) {
			currentColor = colorPanel.customColor.getColor();
			colorPanel.customIcon.updateColor(currentColor);
		}
		
	}
}
