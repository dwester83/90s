package menu;

import graphics.Screen;

public class Menu {
	
	private boolean removed = false;
	
	/**
	 * Default constructor, will be the center for all Dialog and Menus.
	 */
	public Menu() {
	}
	
	/**
	 * Menus currently have to update themselves
	 */
	public void update() {
	}
	
	/**
	 * Menus need to have a screen, and can setup how they want to render themselves.
	 * 
	 * @param screen Screen to do the work of rendering menus
	 */
	public void render(Screen screen) {
	}
	
	/**
	 * Remove a menu from existence.
	 */
	public void remove() {
		removed = true;
	}
	
	/**
	 * Validates if the menu is to be removed?
	 * 
	 * @return boolean The menu is removed from the array?
	 */
	public boolean isRemoved() {
		return removed;
	}
}
