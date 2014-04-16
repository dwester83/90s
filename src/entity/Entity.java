package entity;

import graphics.AnimatedSprite;
import graphics.Screen;

import java.io.Serializable;
import java.util.Random;

import level.Level;
import utility.Position;
/**
 * 
 * @author jchanke2607
 *
 */
public class Entity implements Comparable<Entity>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6467860886638224754L;
	
	protected AnimatedSprite sprite;
	protected Position position; // The position of this object
	protected Level level; //What level this object is connected too
	protected boolean removed = false; //Needs to be removed by the Level class
	protected boolean solid = false; //is this entity solid
	protected boolean breakable = false; // is this entity breakable
	protected Random random = new Random();
	/**
	 * Constructor for an Entity that faces only one way
	 * @param x location on the screen
	 * @param y location on the screen
	 * @param level what level this entity is on
	 * @param sprite Animation
	 */
	public Entity(double x, double y, Level level, AnimatedSprite sprite) {
		position = new Position(x,y);
		this.level = level;
		this.sprite = sprite;
	}
	/**
	 * 
	 */
	public void update(){}
	/**
	 * 
	 */
	public void render(Screen screen){}
	/**
	 * returns this entitys size in pixels
	 * @return int
	 */
	public int getWidth(){
		return sprite.getSpriteWidth();
	}
	
	public int getHeight(){
		return sprite.getSpriteHeight();
	}
	
	public AnimatedSprite getAnimatedSprite(){
		return sprite;
	}

	/**
	 * returns a Position object containing an x and y coordinate
	 * @return Position object
	 */
	public Position getPosition(){
		return position;
	}
	/**
	 * returns this objects x
	 * @return int
	 */
	public double getPositionX(){
		return position.getX();
	}
	/**
	 * returns this objects y
	 * @return int
	 */
	public double getPositionY(){
		return position.getY();
	}
	/**
	 * 
	 * @return
	 */
	public Level getLevel(){
		return level;
	}
	/**
	 * sets both the x and y for this object
	 * @param x int
	 * @param y int
	 */
	public void setPosition(int x, int y){
		position.set(x, y);
	}
	/**
	 * sets the objects x
	 * @param x int
	 */
	public void setPositionX(int x){
		position.setX(x);
	}
	/**
	 * sets this objects y 
	 * @param y int
	 */
	public void setPositionY(int y){
		position.setY(y);
	}
	/**
	 * Sets the remove flag to true so that it can be removed by the Level class
	 */
	public void remove(){
		removed = true;
		
	}
	/**
	 * returns the boolean removed
	 * @return boolean
	 */
	public boolean isRemoved(){
		return removed;
	}
	/**
	 * returns a boolean, true if tile is solid
	 * @return true or false depending if the tile is solid
	 */
	public boolean isSolid(){
		return solid;
	}
	/**
	 * returns a boolean, true is the tile is breakable
	 * @return true or false depending if the tile is breakable
	 */
	public boolean isBreakable(){
		return breakable;
	}

	/**
	 * The equals method for this object compares the Position 
	 */
	public boolean equals(Object object){
		if(!(object instanceof Entity)) return false;
		Entity entity = (Entity) object;
		return (entity.position.getX() == this.position.getX() && entity.position.getY() == this.position.getY());
	}
	/**
	 * compares Y values and returns an int
	 * returns 1 if passed in object is greater than this Entity
	 * returns 0 if they are equal
	 * returns -1 if passed in object is less than this Entity
	 */
	@Override
	public int compareTo(Entity entity) {
		if(position.getY() < ((Entity)entity).position.getY()) return 1;
		else if(position.getY() > ((Entity)entity).position.getY()) return -1;
		return 0;
	}

}
