package utility;

import java.io.Serializable;

import entity.Entity;

public class HitBox implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 733774876115552459L;
	
	private int hitBoxType;
	private int topY;
	private int bottomY;
	private int leftX;
	private int rightX;
	
	Entity entity;
	
	protected boolean remove = false; //so the collision manager knows to remove this hitBox
	/**
	 * constructor for a hit box
	 * @param topY --offset for the top of the hit box
	 * @param bottomY --offset for the bottom of the hit box
	 * @param leftX --offset for the left of the hit box
	 * @param rightX --offset for the right of the hit box
	 * @param hitBoxType --hitBoxType 
	 * if (1) then push box
	 * if (2) then vulnerable box
	 * if (3) then attack box
	 * if (4) then immovable box
	 * @param entity --the Entity that this hit box is connected too
	 */
	public HitBox(int topY, int bottomY, int leftX, int rightX, int hitBoxType, Entity entity) {
		this.topY = topY;
		this.bottomY = bottomY;
		this.leftX = leftX;
		this.rightX = rightX;
		this.hitBoxType = hitBoxType;
		this.entity = entity;
	}

	/**
	 * @param topY the topY to set
	 */
	public void setTopY(int topY) {
		this.topY = topY;
	}

	/**
	 * @param bottomY the bottomY to set
	 */
	public void setBottomY(int bottomY) {
		this.bottomY = bottomY;
	}

	/**
	 * @param leftX the leftX to set
	 */
	public void setLeftX(int leftX) {
		this.leftX = leftX;
	}

	/**
	 * @param rightX the rightX to set
	 */
	public void setRightX(int rightX) {
		this.rightX = rightX;
	}
	/**
	 * returns an array with all the hitBox offsets in it
	 * 0 topY
	 * 1 bottomY
	 * 2 leftX
	 * 3 rightX
	 * @return int[]
	 */
	public int[] getHitBox(){
		int[] hitBox= new int[4];
		hitBox[0] = topY;
		hitBox[1] = bottomY;
		hitBox[2] = leftX;
		hitBox[3] = rightX;
		return hitBox;
	}
	/**
	 * This returns the type of the hitBox
	 * if (1) then push box
	 * if (2) then vulnerable box
	 * if (3) then attack box
	 * if (4) then immovable box
	 * @return int for the hitBoxType
	 */
	public int getHitBoxType() {
		return hitBoxType;
	}

	/**
	 * This sets the type of the hitBox
	 * if (1) then push box
	 * if (2) then vulnerable box
	 * if (3) then attack box
	 * if (4) then immovable box 
	 * @param hitBoxType the hitBoxType to set
	 */
	public void setHitBoxType(int hitBoxType) {
		this.hitBoxType = hitBoxType;
	}

	/**
	 * returns the entity that is connected to this hit box
	 * @return the entity
	 */
	public Entity getEntity() {
		return entity;
	}
	/**
	 * sets remove to true
	 */
	public void remove(){
		remove = true;
	}
	/**
	 * returns if this hitbox should be removed
	 * @return
	 */
	public Boolean isRemoved(){
		return remove;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public Entity wasHit(double x, double y){
		if(x >= (entity.getPositionX() + leftX) && x <= (entity.getPositionX() + entity.getWidth() + rightX) 
		&& y >= (entity.getPositionY() + topY) && y <= (entity.getPositionY()+ entity.getHeight() + bottomY)) {
			return entity;
		}
		return null;
	}
}
