package entity;

import graphics.Screen;
import graphics.Sprite;
import level.Level;

public class SnowSkyParticle extends Entity {
	protected Sprite sprite;
	
	protected int life;
	protected int time = 0;
	protected double xa, ya, za; // the amount of pixels it moves on the x and y axis
	protected double xx, yy, zz; // the position of the particle right now
	private double delta = random.nextGaussian();
	/**
	 * Constructor for this object
	 * @param x int
	 * @param y int
	 * @param sprite Sprite
	 */
	public SnowSkyParticle(int x, int y, int size, Level level){
		super(x,y, level, null);
		sprite = new Sprite(size, size, 0xffffff);
		this.xx = x;
		this.yy = y;
		this.life = (random.nextInt(1000) + 50);
		this.xa = 1;
		this.ya = 1;
		//this.zz = random.nextFloat() + 2.0;
	}
	/**
	 * Overrides the update method
	 */
	public void update(){
		time++;
		if (time > 740000) time = 0;
		if(time > life)remove();
		za -= 0.1;
		
		if (zz < 0){
			zz = 0;
			//za *= -0.6;
			xa *= 0.3;
			ya *= 0.5;
		}
		move(xx + xa, (yy + ya) + (zz + za));	

	}
	/**
	 * Move Logic
	 * @param x double
	 * @param y double
	 */
	private void move(double x, double y) {
		if(collision(x,y)){
			this.xa *= -0.8;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa  + delta;
		this.yy += ya + 1;
		this.zz += za;
	}
	/**
	 * no collision
	 * @param x double
	 * @param y double
	 * @return returns false
	 */
	public boolean collision(double x, double y){
		boolean solid = false;
		return solid;
	}
	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){
		screen.render((int)xx, (int) yy - (int)zz - 1, sprite);
	}
}
