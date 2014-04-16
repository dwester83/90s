package items;

public class Armor extends Item {
	private int bodyPart; //1 = head, 2 = chest, 3 = legs, 4 = hand, 5 = feet
	private int defense;
	private boolean isEquiped;
	public Armor(String name, double cost, int groupSize, double weight, int partOfBody, int defense) {
		super(name, cost, groupSize, weight);
		setDefenseMultiplier(defense);
		bodyPart = partOfBody;
		isEquiped = false;
	}
	/**
	 * @return the bodyPart
	 */
	public int getBodyPart() {
		return bodyPart;
	}
	/**
	 * @return the defenseMultiplier
	 */
	public int getDefenseMultiplier() {
		return defense;
	}
	/**
	 * @param defenseMultiplier the defenseMultiplier to set
	 */
	public void setDefenseMultiplier(int defense) {
		this.defense = defense;
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
