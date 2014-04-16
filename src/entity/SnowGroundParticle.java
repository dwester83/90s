package entity;

import graphics.Screen;
import graphics.Sprite;
import level.Level;
import utility.HitBox;

@SuppressWarnings("serial")
public class SnowGroundParticle extends Entity{


	protected Sprite sprite;
	protected boolean doesBounce = false;
	protected int life;
	protected int time = 0;
	protected double xa, ya, za; // the amount of pixels it moves on the x and y axis
	protected double xx, yy, zz; // the position of the particle right now
	/**
	 * Constructor for this object
	 * @param x int
	 * @param y int
	 * @param sprite Sprite
	 */
	public SnowGroundParticle(int x, int y, int size, Level level){
		super(x,y, level, null);
		sprite = new Sprite(size, size, 0xffffff);
		this.xx = x;
		this.yy = y;
		this.life = (random.nextInt(500) + 100);
		this.xa = random.nextGaussian();
		this.ya = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
	}
	/**
	 * Overrides the update method
	 */
	public void update(){
		time++;
		if (time > 740000) time = 0;
		if(time > life)remove();
		za -= 0.2;
		
		if (zz < 0){
			zz = 0;
			za *= -0.6;
			xa *= 0.4;
			ya *= 0.4;
		}
		move(xx + xa, (yy + ya) + (zz + za));	
	}
	/**
	 * move logic
	 * @param x double
	 * @param y double
	 */
	private void move(double x, double y) {
		if(doesBounce && level.getCollisionManager().collision(x,y,new HitBox(0,0,0,0,0,this))){
			this.xa *= -0.8;
			this.ya *= -0.5;
			this.za *= -0.5;
		}
		this.xx += xa;
		this.yy += ya;
		this.zz += za-1;//the amount of bounce

	}

	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){
		screen.render((int)xx, (int) yy - (int)zz - 1, sprite);
	}
}
