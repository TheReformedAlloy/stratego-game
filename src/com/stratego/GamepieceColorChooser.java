package com.stratego;

import java.awt.*;
import java.awt.color.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.colorchooser.*;
import javax.swing.event.*;

public class GamepieceColorChooser extends JPanel{
	ColorPanel colorPanel;
	NamePanel namePanel;
	
	Color currentColor;
	String currentName;
	
	GamepieceColorChooser(String defaultName, Color playerColor){
		super();
		
		currentName = defaultName;
		this.currentColor = playerColor;
		
		setOpaque(false);
		
		setLayout(new BorderLayout());
		
		colorPanel = new ColorPanel();
		
		add(colorPanel, BorderLayout.CENTER);
		namePanel = new NamePanel();
		add(namePanel, BorderLayout.SOUTH);
		
		setVisible(true);
	}
		
	private class ColorPanel extends JPanel {
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
			clearPanel.setOpaque(false);
			AbstractColorChooserPanel[] colorPanels = {clearPanel};
			
			customColor.setChooserPanels(colorPanels);
			customColor.setPreviewPanel(new JPanel());
			customColor.getSelectionModel().addChangeListener(new CustomChangeListener());
			add(customColor);
			
			//add(new ColorChooserPanel());
		}
		
		/*private class ColorChooserPanel extends JPanel {
			
			JSlider redSlider;
			JTextField redText;
			JSlider greenSlider;
			JTextField greenText;
			JSlider blueSlider;
			JTextField blueText;
			
			ColorChooserPanel(){
				setOpaque(false);
				
				setLayout(new GridLayout(3,2));
				
				redSlider = new JSlider(0, 255, 255);
				redSlider.setOpaque(false);
				add(redSlider);
				redText = new JTextField("255", 3);
				redText.setOpaque(false);
				add(redText);
				
				greenSlider = new JSlider(0, 255, 255);
				greenSlider.setOpaque(false);
				add(greenSlider);
				greenText = new JTextField("255", 3);
				greenText.setOpaque(false);
				add(greenText);
				
				blueSlider = new JSlider(0, 255, 255);
				blueSlider.setOpaque(false);
				add(blueSlider);
				blueText = new JTextField("255", 3);
				blueText.setOpaque(false);
				add(blueText);
			}
		}*/
		
		private class PreviewIcon extends JPanel{
			
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
		JTextField nameField;
		
		NamePanel(){
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
