package items;

public class Weapon extends Item {

	private int damageMultiplier;
	private int numberOfHands;
	private boolean isEquiped;
	public Weapon(String name, double cost, int groupSize, double weight, int damage, int hands) {
		super(name, cost, groupSize, weight);
		setDamageMultiplier(damage);
		setNumberOfHands(hands);
		isEquiped = false;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the numberOfHands
	 */
	public int getNumberOfHands() {
		return numberOfHands;
	}
	/**
	 * @param numberOfHands the numberOfHands to set
	 */
	public void setNumberOfHands(int hands) {
		numberOfHands = hands;
	}
	/**
	 * @return the damageMultiplier
	 */
	public int getDamageMultiplier() {
		return damageMultiplier;
	}
	/**
	 * @param damageMultiplier the damageMultiplier to set
	 */
	public void setDamageMultiplier(int damage) {
		damageMultiplier = damage;
	}
	public void unEquiped(){
		isEquiped = false;
	}
	public void equip(){
		isEquiped = true;
	}
	public boolean isEquiped(){
		return isEquiped;
	}
	/**
	 * String representation of this Weapon
	 */
	public String toString()
	{
		return super.toString();		
	}

}
