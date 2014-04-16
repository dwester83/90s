package entity;

import graphics.AnimatedSprite;
import graphics.Screen;
import graphics.SpriteSheet;
import input.Keyboard;
import input.Mouse;
import inventory.Equipment;
import inventory.Inventory;
import items.Armor;
import items.Weapon;

import level.Level;
import main.Game;
import projectile.*;

/**
 * Player object that the player controls.
 * 
 * @author Josh, Dan, Erin, Tony
 */
public class Player extends SmartMob {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6359195750878189989L;
	
	private Keyboard keyboard;
	private Mouse mouse;
	private Inventory gameInventory;
	private Inventory playerInventory;
	private Equipment equipment;
	private int mouseChangeTimeOut=300;
	private int mouseCounter=mouseChangeTimeOut;
	private Integer prevMouseX=0,prevMouseY=0;
	private boolean keyboardMode=false;
	
	/**
	 * I'll update this later.
	 * 
	 * @param x
	 * @param y
	 * @param level
	 * @param sprite
	 * @param keyboard
	 * @param mouse
	 * @param gameInv
	 */
	public Player(int x, int y, Level level, AnimatedSprite sprite, Keyboard keyboard, Mouse mouse, Inventory gameInv) {
		super(x, y, level, sprite);
		this.keyboard = keyboard;
		this.mouse = mouse;
		setPushBox(2,-2,9,-9);
		gameInventory = gameInv;
		playerInventory = new Inventory(20);
		equipment = new Equipment(stats);
		
		System.out.println("test stats");

		System.out.println(stats);
		test();
		//setPushBox(13,-13,13,-13);
		setVulnerableBox(1,-1,8,-8);
		level.getCollisionManager().add(vulnerableBox);
	}
	public void update(){
		

		mouseCounter--;
		if(mouse.getX() != prevMouseX || mouse.getY() != prevMouseY) {
				keyboardMode=false;
				mouseCounter=mouseChangeTimeOut;
		}
		prevMouseX=mouse.getX();
		prevMouseY=mouse.getY();
		
		if(mouseCounter <= 0) {
			keyboardMode=true;
		}
		
		updateFacing();
		updateAttack();
		if(fireRate >= 0){fireRate--;}
		if(moving){
			sprite.updateAnimation();
		} else sprite.setFrame(0);
		
		int xa = 0;
		int ya = 0;
		
		if(facing <= -1.04 && facing >= -2.08){
			sprite.setDirectionFacing(0); //Upwards
		} else if(facing <= 2.08 && facing >= 1.04) {
			sprite.setDirectionFacing(2); //Forwards
		} else if(facing < 2.08 && facing > -1.04) {
			sprite.setDirectionFacing(3); //Right
		} else {
			sprite.setDirectionFacing(1); //Left
		}
		
		if(keyboard.up){
			ya--;
		}else if(keyboard.down){
			ya++;
		}
		if(keyboard.left){
			xa--;
		}else if(keyboard.right){
			xa++;
		}
		if(xa != 0 || ya != 0){ 
			if(move(position.getX() + xa, position.getY() + ya) && hasMeleeProjectile()){
				projectile.updatePosition(xa, ya);
			}
			moving = true;
		}else {
			moving = false;
		}
	}
	
	
	
	/**
	 * Resets player position and refills life upon death after a small portion of time
	 */
	
	public void reset()
	{
		position.set(20*32, 70*32);
		stats.setHPFull();
		stats.alive();
	}
	
	/**
	 * shooting logic
	 */
	private void updateAttack() {
		if((mouse.getButton() == 1 || keyboard.attack) && fireRate <= 0){
			double dx = mouse.getX() - Game.getWindowWidth() / 2; // mouse location  - players location (middle of screen)
			double dy = mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			
			shoot(position.getX()+sprite.getSpriteWidth(), position.getY()+sprite.getSpriteHeight(), dir);
			fireRate = 30;
		}
	}
	/**
	 * creates a projectile and adds it to the list in level
	 * @param x double
	 * @param y double
	 * @param dir double
	 */
	protected void shoot(double x, double y, double dir){
		//position + width of sprite / 2 is center of x or y - 8 half of projectile
		//projectile = new Melee(position.getX(), position.getY(), facing, level, this,new AnimatedSprite(new SpriteSheet("/textures/meleesheet.png",16,16), 0, 0, 8, 3, 2,true));

		projectile = new RangedOrb(position.getX() + sprite.getSpriteWidth()/2-8, position.getY()+sprite.getSpriteHeight()/2-8, facing, level, this,new AnimatedSprite(new SpriteSheet("/textures/wizardprojectiles.png",16,16), 0, 0, 1, 3, 2,true));
		level.add(projectile, 2);
	}
	private void updateFacing(){
		
		if(keyboardMode == false)
		{
			double dx = mouse.getX() - ((Game.getWindowWidth() / 2)+40); // mouse location  - players location (middle of screen)
			double dy = mouse.getY() - ((Game.getWindowHeight() / 2)+40);
			facing = Math.atan2(dy, dx);
		}
		else
		{
			if(keyboard.left) {
				facing=-Math.PI;
			} else if(keyboard.right) {
				facing=0;
			}
			if(keyboard.up) {
				facing=-(Math.PI/2);
			} else if(keyboard.down) {
				facing=(Math.PI/2);
			}
		}
	}

	/**
	 * Overrides the render method
	 */
	public void render(Screen screen){
		screen.render((int)position.getX(), (int)position.getY(), sprite.getSprite());
	}
	
	public void test(){
		System.out.println("testing Inventory");
		System.out.println(playerInventory.toString());
		System.out.println("testing game Inventory");
		System.out.println(gameInventory.toString());
		System.out.println("adding to player inventory");
		playerInventory.add(gameInventory.getItem(0),0);
		playerInventory.add(gameInventory.getItem(1),1);
		playerInventory.add(gameInventory.getItem(2),2);
		playerInventory.add(gameInventory.getItem(3),3);
		playerInventory.add(gameInventory.getItem(4),4);
		playerInventory.add(gameInventory.getItem(5),5);
		playerInventory.add(gameInventory.getItem(6),6);
		playerInventory.add(gameInventory.getItem(7),7);
		playerInventory.add(gameInventory.getItem(8),8);
		System.out.println(playerInventory.toString());
		System.out.println("Testing stats before equiping");
		System.out.println(stats.toString());
		System.out.println("Testing equiping");
		equipment.setBody((Armor) playerInventory.getItem(5));
		equipment.setHead((Armor) playerInventory.getItem(3));
		equipment.setHands((Armor) playerInventory.getItem(6));
		equipment.setFeet((Armor) playerInventory.getItem(8));
		equipment.setWeapon((Weapon) playerInventory.getItem(4));
		System.out.println(equipment.toString());
		System.out.println("testing stats");
		System.out.println(stats.toString());
		
	}
}