package com.stratego;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

/**
 * Displays a JPanel with a background image.
 * 
 * @author Clint Mooney
 *
 */
public class GraphicPanel extends JPanel{
	private static final long serialVersionUID = -5206622887323658779L;
	
	/** Stores the background image to be displayed in the <code>GraphicPanel</code>*/
	BufferedImage backgroundImage;
	
	/**
	 * Creates a <code>GraphicPanel</code>.
	 * 
	 * @param background a <code>BufferedImage</code> that will be used to store an image to be displayed in the
	 * background of the <code>GraphicPanel</code>.
	 */
	GraphicPanel(BufferedImage background) {
		this.backgroundImage = background;
	}
	
	/**
	 * Overrides <code>JPanel.paintComponent</code> to draw <code>backgroundImage</code> at the full size of the
	 * <code>GraphicPanel</code>.
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}
}
