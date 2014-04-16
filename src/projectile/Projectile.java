package projectile;

import entity.Entity;
import entity.Player;
import entity.SmartMob;
import graphics.AnimatedSprite;
import graphics.Screen;

import java.util.Random;

import utility.AttackBox;
import level.Level;

public class Projectile extends Entity {
	protected int time = 0;
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double nx, ny;
	protected double speed, range, distance;
	protected SmartMob entity;
	protected AttackBox attackBox;
	protected final Random random = new Random();

	/**
	 * Constructor for this Projectile has a position and a direction
	 * @param x double
	 * @param y double
	 * @param dir double
	 */
	public Projectile(double x, double y, double angle, Level level, AnimatedSprite sprite){
		super(x,y,level,sprite);
		xOrigin = x;
		yOrigin = y;
		this.angle = angle;

	}
	public void updatePosition(double xChange, double yChange){
		position.set(position.getX() + xChange, position.getY() + yChange);
	}
	public SmartMob getEntity(){
		return entity;
	}
	public void remove(){
		removed = true;
		//attackBox = null;
	}
	/**
	 * The move method for Projectile
	 */
	protected void move(){
		
	}
	/**
	 * the render method for this object
	 */
	public void render(Screen screen){
		screen.render((int)position.getX(), (int)position.getY(), sprite.getSprite());
	}

}
