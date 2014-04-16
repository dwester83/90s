package utility;

/**
 * 
 * @author jchanke2607
 *
 */
public class Node {
	private Position position;
	private Node parent; //the previous Node
	private double gCost; //the cost to go from Entities current Node to this Node
	private double hCost; //the cost to go in a straight line from this Node to End Node
	/**
	 * 
	 * @param x double for x position of Node in game
	 * @param y double for y position of Node in game
	 * @param parent the previous Node before this Node
	 * @param gCost the cost to go from Entities current Node to this Node
	 * @param hCost the cost to go in a straight line from this Node to End Node
	 */
	public Node(Position position, Node parent, double gCost, double hCost) {
		this.position = position;
		this.parent = parent;
		this.gCost = gCost;
		this.hCost = hCost;
	}
	/**
	 * The getter for the know cost of the previously visited Nodes added up
	 * @return double
	 */
	public double getGCost(){
		return gCost;
	}
	/**
	 * The getter for the distance between the current Node and the goal Node
	 * @return double
	 */
	public double getHCost(){
		return hCost;
	}
	/**
	 * The getter for the gCost + hCost
	 * @return double
	 */
	public double getFCost(){
		return (gCost + hCost);
	}
	/**
	 * The getter for the parent Node of this Node
	 * @return Node
	 */
	public Node getParent(){
		return parent;
	}
	/**
	 * The getter for this Nodes x position
	 * @return double
	 */
	public double getX(){
		return position.getX();
	}
	/**
	 * The getter for this Nodes y position
	 * @return double
	 */
	public double getY(){
		return position.getY();
	}
	/**
	 * The getter for this Nodes x and y position together
	 * @return Position object
	 */
	public Position getPosition(){
		return position;
	}
}
