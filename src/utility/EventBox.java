package utility;

import entity.Entity;
import events.EventManager;


public class EventBox {

	private Position position = new Position();
	private int width, height;
	EventManager eventManager;
	int choice;
	
	public EventBox(int x, int y, int width, int height,EventManager eventManager, int choice) {
		position.set(x, y);
		this.width = width;
		this.height = height;
		this.eventManager = eventManager;
		this.choice = choice;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean wasHit(double x, double y, Entity entity){
		if(x >= position.getX() && x <= (position.getX() + width) 
		&& y >= position.getY() && y <= (position.getY()+ height)) {
			System.out.println("Event");
			eventManager.doEvent(choice);
			return true;
			//run event here
		}
		return false;
	}
	
	
}
