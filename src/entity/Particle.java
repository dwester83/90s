package entity;

import utility.HitBox;
import level.Level;
import graphics.Screen;
import graphics.Sprite;


/**
 * particle is a square moving block
 * @author jchanke2607
 *
 */
public class Particle extends Entity{
	protected Sprite sprite;
	protected boolean doesBounce;
	protected int life;
	protected int time = 0;
	protected double xa, ya, za; // the amount of pixels it moves on the x and y axis
	protected double xx, yy, zz; // the position of the particle right now
	
	/**
	 * Constructor for the particle
	 * @param x int
	 * @param y int
	 * @param life int how long the particle lasts
	 * @param sprite Sprite
	 */
	public Particle(int x, int y, Level level,int life, int color, int size, boolean doesBounce){
		super(x,y, level, null);
		sprite = new Sprite(size, size, color);
		this.doesBounce = doesBounce;
		this.xx = x;
		this.yy = y;
		this.life = life + (random.nextInt(100) + 50);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	/**
	 * Overrides the update method.
	 * particle logic here
	 */
	public void update(){
		time++;
		if (time > 740000) time = 0; 
		if(time > life)remove();
		za -= 0.1;
		
		if (zz < 0){
			zz = 0;
			za *= -0.6;
			xa *= 0.3;
			ya *= 0.3;
		}
		move((int)xx + (int)xa, (int)((yy + ya) + (zz + za)));	
	}
	
	/**
	 * calculates the direction and speed in which the particle moves
	 * @param x double
	 * @param y double
	 */
	private void move(int x, int y) {
		
		if(doesBounce && level.getCollisionManager().collision(x,y,new HitBox(0,0,0,0,0,this))){
			this.xa *= -0.8;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za;
	}
	/**
	 * returns this entitys size in pixels
	 * @return int
	 */
	public int getWidth(){
		return sprite.getWidth();
	}
	
	public int getHeight(){
		return sprite.getHeight();
	}
	/**
	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){
		screen.render((int)xx, (int) yy - (int)zz, sprite);
	}

}
