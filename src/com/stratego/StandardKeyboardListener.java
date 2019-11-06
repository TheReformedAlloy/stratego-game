package com.stratego;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class StandardKeyboardListener implements KeyListener{

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_F11:
				((MainMenuView) e.getSource()).changeFullScreen();
				break;
			default:
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
