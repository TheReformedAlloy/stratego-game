package com.stratego;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;

public class GamepieceColorChooser extends JPanel{
	private static final long serialVersionUID = -5857658494711191520L;
	ColorPanel colorPanel;
	NamePanel namePanel;
	
	Color currentColor;
	String currentName;
	
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
		
	private class ColorPanel extends JPanel {
		private static final long serialVersionUID = -3733483172987839025L;
		JColorChooser customColor;
		PreviewIcon customIcon;
		
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
		
		private class PreviewIcon extends JPanel{
			private static final long serialVersionUID = -2179382022452974454L;
			BufferedImage normalPiece;
			BufferedImage currentPiece;
			
			PreviewIcon() {
				normalPiece = TextureManager.returnGrayPiece();
				currentPiece = TextureManager.returnGrayPiece();
				updateColor(currentColor);
				setOpaque(false);
			}
			
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
			
			public BufferedImage getCurrentImage() {
				return currentPiece;
			}
		}
	}

	private class NamePanel extends JPanel {
		private static final long serialVersionUID = -4942806878162261270L;
		JTextField nameField;
		
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
	
	public Color getColor() {
		return currentColor;
	}
	
	public String getPlayerName() {
		return namePanel.nameField.getText();
	}
	
	public BufferedImage getImage() {
		return colorPanel.customIcon.getCurrentImage();
	}

	private class CustomChangeListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent e) {
			currentColor = colorPanel.customColor.getColor();
			colorPanel.customIcon.updateColor(currentColor);
		}
		
	}
}
