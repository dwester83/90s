package entity;

import graphics.Screen;
import level.Level;
/**
 * A spawner creates other objects
 * @author jchanke2607
 *
 */
@SuppressWarnings("serial")
public class Spawner extends Entity{

	protected int totalIterations;
	protected int iterationsDone;
	protected int amount;
	
	public Spawner(int totalIterations, int amount, Level level) {
		super(0, 0, level, null);
		this.totalIterations = totalIterations;
		this.amount = amount;
	}


	public void update(){
		
	}
	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){

	}

}
