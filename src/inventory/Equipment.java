package inventory;

import items.Armor;
import items.Weapon;
import utility.Stats;

public class Equipment{
	private Weapon  weapon;
	private Armor head;
	private Armor body;
	private Armor hands;
	private Armor feet;
	private Stats mobStats;
	
	public Equipment(Stats mobStats){
		this.mobStats = mobStats;
		
	}
	/**
	 * 
	 * @param toEquip
	 */
	public void setHead(Armor toEquip){
		head = toEquip;
		calcBonus();
	}
	/**
	 * 
	 * @param toEquip
	 */
	public void setBody(Armor toEquip){
		body = toEquip;
		calcBonus();
	}
	/**
	 * 
	 * @param toEquip
	 */
	public void setHands(Armor toEquip){
		hands = toEquip;
		calcBonus();
	}
	/**
	 * 
	 * @param toEquip
	 */
	public void setFeet(Armor toEquip){
		feet = toEquip;
		calcBonus();
	}
	/**
	 * 
	 * @param toEquip
	 */
	public void setWeapon(Weapon toEquip){
		weapon = toEquip;
		mobStats.setWeaponBonus(toEquip.getDamageMultiplier());
		
	}
	public Armor getHead(){
		return head;
	}


	private void calcBonus() {
		int headInt;
		int bodyInt;
		int handsInt;
		int feetInt;
		if(head == null){headInt = 0;
		}else headInt = head.getDefenseMultiplier();
		
		if(body == null){bodyInt = 0;
		}else bodyInt = body.getDefenseMultiplier();
		
		if(hands == null){handsInt = 0;
		}else handsInt = hands.getDefenseMultiplier();
		
		if(feet == null){feetInt = 0;
		}else feetInt = feet.getDefenseMultiplier();
		
		int bonus =  (headInt + bodyInt + handsInt + feetInt);
		mobStats.setArmorBonus(bonus);
	}
	public String toString(){
		return weapon.toString() + "(equiped) \n"+ head.toString() +"(equiped)\n"+ body.toString() +"(equiped)\n"+ hands.toString() +
				"(equiped)\n"+ feet.toString()+"(equiped)";
	}
}
