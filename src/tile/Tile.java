package tile;

import java.io.Serializable;

import utility.HitBox;
import level.Level;
import entity.Entity;
import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.Sprite;
/**
 * An Object with a Sprite
 * @author jchanke2607
 *
 */
public class Tile extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059056585342459860L;
	
	private HitBox vulnerableBox = null;
	private HitBox pushBox = null;
	private HitBox immovableBox = null;
	/**
	 * constructor Tile class takes a sprite
	 * @param sprite
	 */
	public Tile(int x, int y, Level level,boolean solid, boolean breakable, AnimatedSprite sprite){
		super(x,y,level,sprite);
		this.breakable = breakable;
		this.solid = solid;
		/*if(breakable){
			setVulnerableBox(0,0,0,0);
		}
		if(solid){
			setImmovableBox(0,0,0,0);
		}
		*/
	}
	/**
	 * logic that needs to be done by the tile class
	 */
	public void update(){	
	}
	/**
	 * renders this Tile to the screen at a specific position
	 * @param x int
	 * @param y int
	 * @param screen Screen
	 */
	public void render(Screen screen){
		screen.render((int)position.getX(), (int)position.getY(), sprite.getSprite());
	}
	/**
	 * returns the current sprite
	 * @return
	 */
	public Sprite getSprite(){
		return sprite.getSprite();
	}
	/**
	 * 
	 * @param topY
	 * @param bottomY
	 * @param leftX
	 * @param rightX
	 */
	public void setVulnerableBox(int topY, int bottomY, int leftX, int rightX){
		vulnerableBox = new HitBox(topY, bottomY, leftX, rightX, 3, this);
		level.getCollisionManager().add(vulnerableBox);
	}
	public int[] getVulnerableBox(){
		return vulnerableBox.getHitBox();
	}
	/**
	 * 
	 * @param topY
	 * @param bottomY
	 * @param leftX
	 * @param rightX
	 */
	public void setPushBox(int topY, int bottomY, int leftX, int rightX){
		pushBox = new HitBox(topY, bottomY, leftX, rightX, 1, this);
		level.getCollisionManager().add(pushBox);
	}
	public int[] getPushBox(){
		return pushBox.getHitBox();
	}
	/**
	 * 
	 * @param topY
	 * @param bottomY
	 * @param leftX
	 * @param rightX
	 */
	public void setImmovableBox(int topY, int bottomY, int leftX, int rightX){
		immovableBox = new HitBox(topY, bottomY, leftX, rightX, 4, this);
		level.getCollisionManager().add(immovableBox);
	}
	public int[] getImmovableBox(){
		return immovableBox.getHitBox();
	}
}
