package projectile;

import level.Level;
import utility.AttackBox;
import entity.SmartMob;
import graphics.AnimatedSprite;

public class Melee extends Projectile {
	private int hitBoxTransition = 0;
	AttackBox attackBox;
	int leftXOffset = -5;
	int middleXOffset = 7;
	int rightXOffset = 21;
	int topYOffset = 5;
	int middleYOffset = 10;
	int bottomYOffset = 20;
	
 	public Melee(double x, double y, double angle, Level level,SmartMob entity,
			AnimatedSprite sprite) {
		super(x, y, angle, level, sprite);
		this.entity = entity;
		speed = 6;
		range = 32;

		/*
		 * setting up attack animation and attackBox position
		 */
		if(angle <= .3925 && angle >= -.3925){
			//right
			sprite.setDirectionFacing(2);
			angle = 0;
			position.setX(position.getX()+8);
			position.setY(position.getY()+8);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle > .3925 && angle <= 1.1775){
			//right down
			angle = .785;
			sprite.setDirectionFacing(6);
			position.setX(position.getX()+8);
			position.setY(position.getY()+8);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle > 1.1775 && angle <= 1.9625){
			//down
			angle = 1.57;
			sprite.setDirectionFacing(1);
			position.setX(position.getX()+8);
			position.setY(position.getY()+16);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle > 1.9625 && angle <= 2.7475){
			//left down
			angle =2.355;
			sprite.setDirectionFacing(7);
			position.setX(position.getX()+8);
			position.setY(position.getY()+8);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle < -.3925 && angle >= -1.1775){
			//right up
			angle = -.785;
			sprite.setDirectionFacing(4);
			position.setX(position.getX()+12);
			position.setY(position.getY()+12);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle < -1.1775 && angle >= -1.9625){
			//up
			angle = -1.57;
			sprite.setDirectionFacing(0);
			position.setX(position.getX()+8);
			position.setY(position.getY()+8);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else if(angle < -1.9625 && angle >= -2.7475){
			//up left
			angle = -2.355;
			sprite.setDirectionFacing(5);
			position.setX(position.getX()+8);
			position.setY(position.getY()+12);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}else{
			//left
			angle = 3.14;
			sprite.setDirectionFacing(3);
			position.setX(position.getX()+8);
			position.setY(position.getY()+8);
			attackBox = new AttackBox(5, -5, 11, 0,16,angle, this);
		}
		nx = speed * Math.cos(angle);
		ny = speed * Math.sin(angle);

	}
	/**
	 * The logic for this object
	 */
	public void update(){
		
		if(time % 2 == 0 && sprite.updateAnimation()){
			hitBoxTransition++;
			move();
			updateHitBox();
		}
		if(hitBoxTransition == 3){ 
			attackBox.remove();
			this.remove();
		}
		
		time++;
	}
	/**
	 * the move method for this object
	 */
	protected void move(){
		position.setX((int)(position.getX() + nx));
		position.setY((int)(position.getY()+ ny));
	}
	/**
	 * 
	 */
	protected void updateHitBox(){
		//System.out.println(position.getX() + " is X, and Y is " + position.getY());
		level.getCollisionManager().attackCollision(position.getX(), position.getY(), attackBox);
	}

}
