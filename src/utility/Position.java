package utility;

import java.io.Serializable;

/**
 * Position of the entity.
 * 
 * @author Josh
 */
public class Position implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5658389965447422384L;
	
	private double x, y;
	/**
	 * sets the x,y to 0
	 */
	public Position(){
		set(0, 0);
	}
	/**
	 * sets this x,y to the past in x,y
	 * @param x int 
	 * @param y int
	 */
	public Position(double x, double y){
		set(x, y);
	}
	
	/**
	 * sets this Position's x and y to the past in Position's x and y
	 * @param position Position
	 */
	public Position(Position position){
		set(position.x, position.y);
	}
	/**
	 * the setter for both x and y
	 * @param x int
	 * @param y int
	 */
	public void set(double x, double y){
		this.x = x;
		this.y = y;
	}
	/**
	 * The getter for this object x position
	 * @return int
	 */
	public double getX(){
		return x;
	}
	/**
	 * The getter for this objects y position
	 * @return int
	 */
	public double getY(){
		return y;
	}
	/**
	 * Adds this Position to the past in Position
	 * @param position Position
	 * @return Position
	 */
	public Position add(Position position){
		this.x += position.x;
		this.y += position.y;
		return this;
	}
	/**
	 * Subtracts the past in Position from this Position
	 * @param position Position
	 * @return Position
	 */
	public Position subtract(Position position){
		this.x -= position.x;
		this.y -= position.y;
		return this;
	}
	/**
	 * The setter for this Position's x variable
	 * @param x int
	 * @return this Position
	 */
	public Position setX(double x){
		this.x = x;
		return this;
	}
	/**
	 * The setter for this Position's y variable
	 * @param y int
	 * @return Position
	 */
	public Position setY(double y){
		this.y = y;
		return this;
	}
	/**
	 * The equals method for the Position class
	 * checks to see if the x and y is the same
	 * @param object is a Position that needs to be passed in
	 */
	public boolean equals(Object object){
		if(!(object instanceof Position)) return false;
		Position pos = (Position) object;
		if(pos.getX() == this.getX() && pos.getY() == this.getY()) return true;
		return false;
	}
}
