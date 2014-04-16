package projectile;

import utility.AttackBox;
import level.Level;
import entity.Player;
import entity.SmartMob;
import graphics.AnimatedSprite;
import graphics.Screen;

public class RangedOrb extends Projectile {
	private int fireRate = 0;  // higher is slower
	
	public RangedOrb(double x, double y, double angle, Level level,SmartMob entity,
			AnimatedSprite sprite) {
		super(x, y, angle, level, sprite);
		System.out.println("projectile");
		this.entity = entity;
		range = 200;
		speed = 2;
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);
		attackBox = new AttackBox(3, -3, 3, -3, 2, angle, this);
		//level.addHitBox(attackBox);

	}

	/**
	 * The logic for this object
	 */
	public void update(){
		sprite.updateAnimation();	
		move();
	}
	/**
	 * the move method for this object
	 */
	protected void move(){
		position.setX(position.getX() + nx);
		position.setY(position.getY()+ ny);
		level.getCollisionManager().attackCollision(position.getX(), position.getY(), attackBox);
		if(distance() > range) remove();
	}
	/**
	 * finds the distance between two locations
	 * @return double
	 */
	private double distance() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - position.getX())*(xOrigin - position.getX())
				+ (yOrigin - position.getY())*(yOrigin - position.getY())));
		return dist;
	}
	public int getFireRate(){
		return fireRate;
	}

}
