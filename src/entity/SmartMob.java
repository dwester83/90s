package entity;

import graphics.AnimatedSprite;
import level.Level;
import projectile.Melee;
import projectile.Projectile;
import utility.Position;
import utility.Stats;

public class SmartMob extends Mob {

	protected Projectile projectile;
	protected double facing;
	protected int time = 0;
	protected int fireRate = 10;
	protected Stats stats;
	
	public SmartMob(int x, int y, Level level, AnimatedSprite sprite) {
		super(x, y, level, sprite);
		stats = new Stats("");
		// TODO Auto-generated constructor stub
	}
	/**
	 * returns the double for the direction facing in radians
	 * @return
	 */
	public double getFacing(){
		return facing;
	}
	/**
	 * returns true if Entity has a melee weapon
	 * @return
	 */
	protected boolean hasMeleeProjectile(){
		if(projectile != null && projectile instanceof Melee) return true;
		return false;
	}
	
	public Stats getStats() {
		return stats;
	}
	public void removeProjectile(){
		projectile.remove();
		
	}
	/*public Inventory viewInventory() {
		return inventory;
	}*/
	
	
}
