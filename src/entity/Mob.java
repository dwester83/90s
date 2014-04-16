package entity;

import graphics.AnimatedSprite;
import graphics.Screen;
import level.Level;
import sound.Sound;
import utility.HitBox;
import utility.Position;

public class Mob extends Entity {
	protected enum Direction{
		N, NE, E, SE, S, SW, W, NW
	}
	//private Stats stats;
	protected Position previous;
	protected boolean moving = false;
	protected HitBox vulnerableBox;
	protected HitBox pushBox;
	//private Sound walk = new Sound("./res/sounds/footstep.wav", false);
	private int time = 0;
	protected int speedOfMovement = 2;//the speed at which the npc moves, the larger the number the slower they move
	/**
	 * Constructor for the mob class
	 * @param x int x location
	 * @param y int y location
	 * @param level the Level this mob is connected to
	 * @param sprite the AnimatedSprite for this mob
	 */
	public Mob(int x, int y, Level level, AnimatedSprite sprite) {
		super(x, y, level, sprite);
		previous = new Position(x,y);
	}
	/**
	 * The move logic for this entity
	 * @param newX
	 * @param newY
	 */
	public boolean move(double newX, double newY){
		if(!level.getCollisionManager().collision(newX, newY, pushBox)){
			if(time % 60 == 0)previous.set(position.getX(), position.getY());
			
			position.set(newX, newY);
			time++;
			if(time == 60){
				//walk.play();
				time = 0;
			}
			return true;
		}else if(!level.getCollisionManager().collision(position.getX(), newY, pushBox)){
			position.setY(newY);
			if(time == 60){
				//walk.play();
				time = 0;
			}
			return true;
		}else if(!level.getCollisionManager().collision(newX, position.getY(), pushBox)){
			position.setX(newX);
			if(time == 60){
				//walk.play();
				time = 0;
			}
			return true;
		}else{
			//System.out.print("Can't Move");
			return false;
		}
	}

	/**
	 * 
	 * @param topY
	 * @param bottomY
	 * @param leftX
	 * @param rightX
	 */
	public void setVulnerableBox(int topY, int bottomY, int leftX, int rightX){
		vulnerableBox = new HitBox(topY, bottomY, leftX, rightX, 3, this);
		level.getCollisionManager().add(vulnerableBox);
	}
	/**
	 * 
	 * @param topY
	 * @param bottomY
	 * @param leftX
	 * @param rightX
	 */
	public void setPushBox(int topY, int bottomY, int leftX, int rightX){
		pushBox = new HitBox(topY, bottomY, leftX, rightX, 1, this);
		level.getCollisionManager().add(pushBox);
	}
	public boolean isMoving(){
		return moving;
	}
	public Position getPreviousPosition(){
		return previous;
	}
	/**
	 * 
	 */
	public void update(){
		
	}
	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){
		
		screen.render((int)position.getX(), (int)position.getY(), sprite.getSprite());
	}
}
