package utility;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import projectile.Projectile;
import projectile.RangedOrb;
import sound.SoundManager;
import entity.Entity;
import entity.MagicExplosionSpawner;
import entity.NPC;
import entity.Player;
import entity.SmartMob;
import graphics.Screen;
/**
 * This class handles all of the collisions in the level
 * @author jchanke2607
 *
 */
public class CollisionManager implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1316765823314801651L;
	
	private List<HitBox> hitBoxes;
	private List<EventBox> eventBoxes;
	private Combat combat = new Combat();
	private SoundManager soundManager = SoundManager.getInstance();
	/**
	 * 
	 */
	public CollisionManager(){
		hitBoxes = new ArrayList<HitBox>(); 
		eventBoxes = new ArrayList<EventBox>();
	}
	
	/**
	 * 
	 * @param entity
	 */
	public void add(HitBox hitBox){
		hitBoxes.add(hitBox);
	}
	
	/**
	 * 
	 * @param eventBox
	 */
	public void addEventBox(EventBox eventBox){
		eventBoxes.add(eventBox);
	}
	
	/**
	 * returns a boolean if the past in HitBox pushBox has collided with another pushBox or immovableBox
	 * @param entity
	 * @return Entity
	 */
	public Entity collisionEntity(double x, double y, HitBox pushBox){
		remove();
		eventCollision(x,y,pushBox);
		for(int i = 0; i < hitBoxes.size(); i++){
			if(hitBoxes.get(i).getEntity().equals(pushBox.getEntity())){continue;}
			if(hitBoxes.get(i).getHitBoxType() == 1 || hitBoxes.get(i).getHitBoxType() == 4){
				if(hasCollided(x, y, pushBox, hitBoxes.get(i))){return hitBoxes.get(i).getEntity();}
			}
		}
		return null;
	}
	
	/**
	 * returns a boolean if the past in HitBox pushBox has collided with another pushBox or immovableBox
	 * @param entity
	 * @return boolean
	 */
	public boolean collision(double x, double y, HitBox pushBox){
		boolean hasCollided = false;
		remove();
		eventCollision(x,y,pushBox);
		for(int i = 0; i < hitBoxes.size(); i++){
			if(hitBoxes.get(i).getEntity().equals(pushBox.getEntity())){continue;}
			if(hitBoxes.get(i).getHitBoxType() == 1 || hitBoxes.get(i).getHitBoxType() == 4){
				if(hasCollided(x, y, pushBox, hitBoxes.get(i))){hasCollided = true;}
			}
		}
		return hasCollided;
	}
	
	/**
	 * Determines if attack has succeeded or not and calls the actions
	 * @param entity
	 * @return Entity
	 */
	public void attackCollision(double x, double y,AttackBox attackBox){
		Projectile projectile = (Projectile) attackBox.getEntity();
		Entity entity = projectile.getEntity();
		for(int i = 0; i < hitBoxes.size(); i++){
			if(hitBoxes.get(i).getEntity() instanceof NPC && entity instanceof NPC){continue;}
			if(hitBoxes.get(i).getEntity() instanceof Player && entity instanceof Player){continue;}
			//checks to see if push box was hit
			if(hitBoxes.get(i).getHitBoxType() == 1){
				if(hasCollided(x, y, attackBox, hitBoxes.get(i))){
					combat.wasPushed((SmartMob)hitBoxes.get(i).getEntity(),(SmartMob)entity, attackBox);
					soundManager.playSound("Hit", false);
					attackBox.remove();
					return;
				}
			}
			//checks to see if vulnerable box was hit
			if(hitBoxes.get(i).getHitBoxType() == 3){
				if(hasCollided(x, y, attackBox, hitBoxes.get(i))){
					combat.calculateDamage((SmartMob) entity,(SmartMob)hitBoxes.get(i).getEntity());
					attackBox.remove();
					return;
				}
			}
			//checks to see if immovable box was hit
			if(hitBoxes.get(i).getHitBoxType() == 4){
				if(hasCollided(x, y, attackBox, hitBoxes.get(i))){
					attackBox.remove();
					return;
					//stop attack here and any stop attack sounds and animations
				}
				
			}
		}
	}
	/**
	 * checks to see if an eventBox has been triggered by the pushBox
	 * @param x location in game
	 * @param y location in game
	 * @param pushBox HitBox
	 */
	public void eventCollision(double x, double y, HitBox pushBox){
		if(pushBox.getEntity() instanceof Player){
			for(int i = 0; i < eventBoxes.size(); i++){
				if(hasCollided(x, y, pushBox, hitBoxes.get(i))){
					//call event stuff here
				}
			}
		}
	}
	/**
	 * checks to see if first hitbox has collided with second hitBox
	 * @param x location in game
	 * @param y location in game
	 * @param first HitBox
	 * @param second HitBox
	 * @return true if first HitBox hit Second HitBox
	 */
	private boolean hasCollided(double x, double y, HitBox first, HitBox second){
		boolean hasCollided = false;
		Entity entity = first.getEntity();
		int[] hitBox = first.getHitBox();
		if(second.wasHit(x + hitBox[2], y + hitBox[0]) != null){
			hasCollided = true;
		}else if(second.wasHit(x + entity.getWidth() + hitBox[3], y + hitBox[0]) != null){
			hasCollided = true;
		}else if(second.wasHit(x + hitBox[2], y + entity.getHeight() + hitBox[1]) != null){
			hasCollided = true;
		}else if(second.wasHit(x + entity.getWidth() + hitBox[3], y + entity.getHeight() + hitBox[1]) != null){
			hasCollided = true;
		}
		if(hasCollided){explosion(first);}
		return hasCollided;
	}
	
	private void explosion(HitBox first){
		Entity temp = first.getEntity();
		if(temp instanceof RangedOrb){
			System.out.println("ranged orb");
			temp.getLevel().add(
			new MagicExplosionSpawner((int)(temp.getPositionX() + (temp.getWidth()/2)),
					(int)(temp.getPositionY() + (temp.getHeight()/2)), 10, 10, 1, temp.getLevel()), 2);
		}
	}
	/**
	 * draws a colored rectangle around any HitBoxes on screen
	 * used for testing
	 * @param g
	 * @param screen
	 */
	public void drawHitBoxes(Graphics g, Screen screen){
		for(int i = 0; i < hitBoxes.size(); i++){
			int[] hitbox = hitBoxes.get(i).getHitBox();
			if(hitBoxes.get(i).getHitBoxType() == 1)g.setColor(Color.blue);
			else if(hitBoxes.get(i).getHitBoxType() == 2)g.setColor(Color.green);
			else if(hitBoxes.get(i).getHitBoxType() == 3)g.setColor(Color.red);
			else g.setColor(Color.white);
			g.drawRect((int)((hitBoxes.get(i).getEntity().getPositionX()*3) + hitbox[2]*3 - screen.getXOffset()*3), (int)((hitBoxes.get(i).getEntity().getPositionY()*3) + hitbox[0]*3 - screen.getYOffset()*3), hitBoxes.get(i).getEntity().getWidth()*3 - hitbox[2]*3 + hitbox[3]*3, hitBoxes.get(i).getEntity().getHeight()*3 - hitbox[0]*3 + hitbox[1]*3);	
		}
	}

	/**
	 * checks to see if the HitBoxes is ok to remove then removes it
	 */
	private void remove() {
		for(int i = 0; i < hitBoxes.size(); i++){
			if(hitBoxes.get(i).isRemoved()){
				hitBoxes.remove(i);
			}
		}
	}
}
