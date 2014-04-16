package utility;

import entity.Entity;

public class AttackBox extends HitBox {
	
	private double angle;

	public AttackBox(int topY, int bottomY, int leftX, int rightX,
			int hitBoxType, double angle, Entity entity) {
		super(topY, bottomY, leftX, rightX, hitBoxType, entity);
		this.angle = angle;
	}
	public double getAngle(){
		return angle;
	}
	
	public void setAngle(double newAngle){
		angle = newAngle;
	}
	public void remove(){
		remove = true;
		entity.remove();
		
	}
}
