package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

public class GraphicButton extends JComponent implements MouseListener{
	
	private boolean mouseEntered = false;
	private boolean mousePressed = false;
	
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	String command;
	
	String text;
	int margin = 0;
	
	GraphicButton(String text){
		super();
		
		enableInputMethods(true);
		addMouseListener(this);
		
		this.text = text;
		setFocusable(false);
	}
	
	GraphicButton(String text, int margin) {
		super();
		
		enableInputMethods(true);
		addMouseListener(this);
		
		this.text = text;
		this.margin = margin;
		
		setFocusable(false);
	}
	
	GraphicButton(String text, int width, int height) {
		super();
		
		enableInputMethods(true);
		addMouseListener(this);
		
		this.text = text;
		setFocusable(false);
		
		if(height < width / 8) {
			setSize(height * 8, height);
		}else {
			setSize(width, width / 8);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		int buttonWidth;
		int buttonHeight;
		
		if(getHeight() < getWidth() / 8) {
			buttonHeight = getHeight();
			buttonWidth = buttonHeight * 8;
		}else {
			buttonWidth = getWidth();
			buttonHeight = buttonWidth / 8;
		}
		
		int hBoxOffset = (getWidth() - buttonWidth) / 2;
		int vBoxOffset = (getHeight() - buttonHeight) / 2;
		
		if(mousePressed) {
			g2d.setColor(new Color(45, 26, 26));
			g2d.drawImage(TextureManager.BUTTON_PRESSED, hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}else if(mouseEntered) {
			g2d.setColor(new Color(95, 76, 76));
			g2d.drawImage(TextureManager.BUTTON_HOVER, hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}else {
			/*
			 * buttonWidth -= 50; buttonHeight -= 20; hBoxOffset += 25; vBoxOffset += 10;
			 */
			g2d.setColor(new Color(70, 51, 51));
			g2d.drawImage(TextureManager.BUTTON, hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}
		
		Font drawFont = new Font("Verdana", Font.PLAIN, buttonHeight/4);
		FontMetrics dfMetric = g2d.getFontMetrics(drawFont);
		int hOffset = hBoxOffset + (buttonWidth / 2) - (dfMetric.stringWidth(text) / 2);
		int vOffset = vBoxOffset + (buttonHeight / 2) + (dfMetric.getHeight() / 4);
		g2d.setFont(drawFont);
		g2d.drawString(text, hOffset, vOffset);
	}

	public void setActionCommand(String cmdName) {
		command = cmdName;
	}
	
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	public void notifyListeners(MouseEvent e) {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command, e.getWhen(), e.getModifiersEx());
		synchronized(listeners) {
			for (int i = 0; i < listeners.size(); i++) {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		mousePressed = true;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		notifyListeners(e);
		mousePressed = false;
		repaint();
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered = false;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}

}
