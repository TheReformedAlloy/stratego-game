package com.stratego;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

public class GraphicPanel extends JPanel{
	private static final long serialVersionUID = -5206622887323658779L;
	BufferedImage backgroundImage;
	
	GraphicPanel(BufferedImage background) {
		this.backgroundImage = background;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), null);
	}
}
