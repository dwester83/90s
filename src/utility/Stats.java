package utility;

import java.io.Serializable;

public class Stats implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1970309985169932129L;
	
	private int str = 1;
	private int maxHP = 10;
	private int currentHP = 10;
	private int maxMP = 10;
	private int currentMP = 10;
	private int speed = 1;
	private int totalWeight = 0;
	private int maxWeight = 50;
	private int growthPoints = 0;
	private final int costModifier = 200;
	private int armorBonus = 0;
	private int weaponBonus = 0;
	
	private boolean dead = false;
	
	private double strModifier;
	private double defModifier;
	private double HPModifier;
	private double MPModifier;
	private double speedModifier;
	
	/**
	 * Initializes all the stats and modifiers based on which type of Mob it is.
	 */
	public Stats(String job) {

		if(job.equalsIgnoreCase("warrior")) {
			strModifier = 1.5;
			defModifier = 1.2;
			HPModifier = 2;
			MPModifier = 0.5;
			speedModifier = 0.7;
		}
		
		else if(job.equalsIgnoreCase("mage")) {
			strModifier = 1.5;
			defModifier = 0.8;
			HPModifier = 1;
			MPModifier = 2;
			speedModifier = 1;
		}
		
		else if(job.equalsIgnoreCase("rogue")) {
			strModifier = 1.5;
			defModifier = 1;
			HPModifier = 1.2;
			MPModifier = 0.8;
			speedModifier = 1.5;
		}
		
		else {
			//System.out.println("default stats");
			strModifier = 2;
			defModifier = 1;
			HPModifier = 1;
			MPModifier = 1;
			speedModifier = 1;
		}
	}
	public void setArmorBonus(int armorBonus){
		this. armorBonus = armorBonus;
	}
	public void setWeaponBonus(int weaponBonus){
		this.weaponBonus = weaponBonus;
	}
	public void dead() {
		dead = true;
	}
	public void alive(){
		dead = false;
	}
	public boolean isDead() {
		return dead;
	}
	
	public int getStr() {
		return str;
	}
	public int getMaxHP(){
		return maxHP;
	}
	
	
	public int getCurrentHP() {
		return currentHP;
	}
	
	public int getCurrentMP() {
		return currentMP;
	}
	
	public int getMaxMP(){
		return maxMP;
	}
	
	public int getSpeed() {
		return speed;
	}
	
	public int getCurrentWeight() {
		return totalWeight;
	}
	
	public int getWeaponBonus() {
		return weaponBonus;
	}
	
	public int getArmorBonus() {
		return armorBonus;
	}
	
	public double getModStr() {
		return str*strModifier;
	}
	
	public double getModDef() {
		return armorBonus*defModifier;
	}
	
	public double getModHP() {
		return maxHP*HPModifier;
	}
	
	public double getModMP() {
		return maxMP*MPModifier;
	}
	
	public double getModSpeed() {
		return speed*speedModifier;
	}
	
	public void takeDamage(int damage) {
		currentHP -= damage;
	}
	
	public void gainPoints(int pointsReceived) {
		growthPoints += pointsReceived;
	}
	public void setHPFull(){
		currentHP = maxHP;
		
	}
	// Setters for leveling up
	//------------------------------------------------------------
	public void setStr() {
		if(growthPoints >= str*costModifier) {
			growthPoints -= str*costModifier;
			str++;
		} else { System.out.println("Not enough growth points.");}
	}
	

	public void setHP() {
		if(growthPoints >= (maxHP/10)*costModifier) {
			growthPoints -= (maxHP/10)*costModifier;
			maxHP+=10;
		} else { System.out.println("Not enough growth points.");}
	}
	
	public void setMP() {
		if(growthPoints >= (maxMP/10)*costModifier) {
			growthPoints -= (maxMP/10)*costModifier;
			maxMP+=10;
		} else { System.out.println("Not enough growth points.");}
	}
	
	public void setSpeed() {
		if(growthPoints >= speed*costModifier) {
			growthPoints -= speed*costModifier;
			speed++;
		} else { System.out.println("Not enough growth points.");}
	}
	
	public void setTotalWeight() {
		if(growthPoints >= str*costModifier) {
			growthPoints -= str*costModifier;
			str++;
		} else { System.out.println("Not enough growth points.");}
	}
	public String toString(){
		return "Armor Bonus--" + armorBonus + "\nWeapon Bonus--" + weaponBonus;
	}
}
