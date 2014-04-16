package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener 
{
	private boolean[] keys = new boolean[120]; //create an array of booleans to hold values for each key
	public boolean up, down, left, right, accept, cancel, menu, attack;
	
	public void update()
	{
		up = keys[KeyEvent.VK_UP] || keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		accept = keys[KeyEvent.VK_Z];
		cancel = keys[KeyEvent.VK_X];
		menu = keys[KeyEvent.VK_C];
		attack = keys[KeyEvent.VK_Q];
	}
	
	public void keyPressed(KeyEvent e) 
	{
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) 
	{
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}


}
