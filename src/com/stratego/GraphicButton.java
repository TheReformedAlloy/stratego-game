package com.stratego;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Draws an image that responds to mouse input and notifies an array of listeners when the button is pressed.
 * 
 * @author Clint Mooney
 *
 */
public class GraphicButton extends JComponent implements MouseListener{
	private static final long serialVersionUID = 6199235732279404886L;
	
	/** Indicates whether the mouse cursor is inside the <code>GraphicButton</code>*/
	private boolean mouseEntered = false;
	/** Indicates whether the left mouse button has been pressed down on the <code>GraphicButton</code>*/
	private boolean mousePressed = false;
	
	/** Stores ActionListeners to pass events along to.*/
	private ArrayList<ActionListener> listeners = new ArrayList<ActionListener>();
	
	/** Stores an identifier to be passed to each element of <code>listeners</code>*/
	String command;
	
	/** Stores text to be displayed at the center of the <code>GraphicButton</code>.*/
	String text;
	/** Stores a number which indicates a number of pixels to indent from the sides of the <code>GraphicButton</code>
	 * before drawing the current image.*/
	int margin = 0;
	
	/**
	 * Creates a <code>GraphicButton</code> without redefining <code>margin</code> but setting <code>text</code>
	 * to the input <code>String</code>.
	 * 
	 * @param text contains text to display on the <code>GraphicButton</code> to describe its purpose.
	 */
	GraphicButton(String text){
		super();
		
		enableInputMethods(true);
		addMouseListener(this);
		
		this.text = text;
		setFocusable(false);
	}

	/**
	 * Creates a <code>GraphicButton</code> setting <code>text</code> and <code>margin</code> to the
	 * inputs of the same names.
	 * 
	 * @param text contains text to display on the <code>GraphicButton</code> to describe its purpose.
	 * @param margin indicates the number of pixels to inset the Image from the sides of the <code>GraphicButton</code>
	 */
	GraphicButton(String text, int margin) {
		super();
		
		enableInputMethods(true);
		addMouseListener(this);
		
		this.text = text;
		this.margin = margin;
		
		setFocusable(false);
	}
	
	/**
	 * Creates a <code>GraphicButton</code> setting <code>text</code> to the input of the same name
	 * and defining the amount of space the <code>GraphicButton</code> will take up.
	 * 
	 * @param text contains text to display on the <code>GraphicButton</code> to describe its purpose.
	 * @param width indicates the width of the <code>GraphicButton</code> on the container.
	 * @param height indicates the height of the <code>GraphicButton</code> on the container.
	 */
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
	
	/**
	 * Overrides <code>JComponent.paintComponent</code> to paint either '<code>assets/gui/Button.png</code>',
	 * '<code>assets/gui/ButtonHover.png</code>', and '<code>assets/gui/ButtonPressed.png</code>' based on whether the mouse
	 * is outside of, inside of, or pressing on the <code>GraphicButton</code>.
	 */
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
			g2d.setColor(TextureManager.getInstance().getColor("font low"));
			g2d.drawImage(TextureManager.getInstance().getImage("button press"), hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}else if(mouseEntered) {
			g2d.setColor(TextureManager.getInstance().getColor("font high"));
			g2d.drawImage(TextureManager.getInstance().getImage("button hover"), hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}else {
			/*
			 * buttonWidth -= 50; buttonHeight -= 20; hBoxOffset += 25; vBoxOffset += 10;
			 */
			g2d.setColor(TextureManager.getInstance().getColor("font base"));
			g2d.drawImage(TextureManager.getInstance().getImage("button"), hBoxOffset, vBoxOffset, buttonWidth, buttonHeight, null);
		}
		
		Font drawFont = new Font("Verdana", Font.PLAIN, buttonHeight/2);
		FontMetrics dfMetric = g2d.getFontMetrics(drawFont);
		int hOffset = hBoxOffset + (buttonWidth / 2) - (dfMetric.stringWidth(text) / 2);
		int vOffset = vBoxOffset + (buttonHeight / 2) + (dfMetric.getHeight() / 4);
		g2d.setFont(drawFont);
		g2d.drawString(text, hOffset, vOffset);
	}

	/** 
	 * Sets <code>command</code> to the value of <code>cmdName</code>.
	 * 
	 * @param cmdName the <code>String</code> to be associated with the <code>GraphicButton</code>.
	 */
	public void setActionCommand(String cmdName) {
		command = cmdName;
	}
	
	/**
	 * Adds an <code>ActionListener</code> to the <code>listeners</code> <code>ArrayList</code>. 
	 * 
	 * @param listener an <code>ActionListener</code> to be added.
	 */
	public void addActionListener(ActionListener listener) {
		listeners.add(listener);
	}
	
	/** 
	 * Creates an <code>ActionEvent</code> with <code>command</code> as a specifier to process mouse presses.
	 * 
	 * @param e <code>MouseEvent</code> to be converted to an <code>ActionEvent</code> and sent to each <code>ActionListener</code> in <code>listeners</code>.
	 */
	public void notifyListeners(MouseEvent e) {
		ActionEvent evt = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, command, e.getWhen(), e.getModifiersEx());
		synchronized(listeners) {
			for (int i = 0; i < listeners.size(); i++) {
                ActionListener tmp = listeners.get(i);
                tmp.actionPerformed(evt);
            }
		}
	}
	
	/** 
	 * Required override from <code>MouseListener</code> (Unused).
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	/** 
	 * Sets <code>mousePressed</code> to true to paint the button pressed image.
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mousePressed = true;
		repaint();
	}

	/** 
	 * Sets <code>mousePressed</code> to false to paint the default button image.
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		notifyListeners(e);
		mousePressed = false;
		repaint();
	}

	/**
	 * Changes the cursor to the hand cursor and sets <code>mouseEntered</code> to <code>true</code> to paint
	 * the button hover image.
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		mouseEntered = true;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		repaint();
	}

	/**
	 * Changes the cursor to the default cursor and resets <code>mouseEntered</code> to paint the default
	 * button image.
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		mouseEntered = false;
		setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		repaint();
	}
}
