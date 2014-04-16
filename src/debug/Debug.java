package debug;

import input.Keyboard;

/**
 * This is a debug class used for whatever type of debug features needed, more debug constructors can be added.
 * This whole package will contain any classes needed for debugging, that way it's all in one place.
 * 
 * @author Dan
 */
public class Debug {

	Keyboard key;
	boolean debugKey;

	/**
	 * Debug object, used for keyboard debug features. 
	 * 
	 * @param key Keyboard object to allow toggling of debug feature.
	 */
	public Debug(Keyboard key) {
		this.key = key;
	}

	/**
	 * Debug button being pressed.
	 */
	public void debugButton() {
		if (key.accept) {
			debugKey = true;
		} else if (key.cancel && debugKey) {
			debugKey = false;
		}
	}

	/**
	 * If Debug button has been pressed than if this method is called the graphics won't show.
	 * 
	 * @return boolean, on whether the debug button has been pressed.
	 */
	public boolean turnOffGraphics() {
		return debugKey;
	}
}