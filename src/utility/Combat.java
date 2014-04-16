package utility;

import java.io.Serializable;

import projectile.RangedOrb;
import level.Level;
import entity.BloodSpawner;
import entity.Entity;
import entity.MagicExplosionSpawner;
import entity.NPC;
import entity.SmartMob;

public class Combat implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3470731642099191235L;

	public Combat() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * This method calculates the battle damage
	 * @param attacker the entity attacking
	 * @param defender the entity defending
	 */
	public void calculateDamage(SmartMob attacker, SmartMob defender){
		if(defender instanceof NPC){
			NPC npc = (NPC)defender;
			npc.setEnemy(attacker);
		}
		Stats attackStats = attacker.getStats();
		Stats defendStats = defender.getStats();
		System.out.println("Attacker: " + (attackStats.getModStr() + attackStats.getWeaponBonus() + ", Defender :" + (defendStats.getModDef() + defendStats.getArmorBonus())));
		int attackDamage = (int)attackStats.getModStr() + attackStats.getWeaponBonus();
		int defense = (int)defendStats.getModDef() + defendStats.getArmorBonus();
		int damage;
		if(defense >= attackDamage){
			damage = 1;
		}else damage = attackDamage - defense;
		
		defendStats.takeDamage(damage);
		if(defendStats.getCurrentHP() == 0) {
			defendStats.dead();
			attackStats.gainPoints(400);
		}
		System.out.println("Damage: " + damage);
		int xPosition = (int)(defender.getPositionX() + (defender.getWidth()/2));//gets the middle of the entity
		int yPosition = (int)(defender.getPositionY() + (defender.getHeight()/2));
		Level level = defender.getLevel();
		BloodSpawner bs = new BloodSpawner(xPosition, yPosition, 100, 50, 1, level);
		level.add(bs, 2);
		
		
	}
	
	public void wasPushed(SmartMob defender,SmartMob attacker, AttackBox attackBox){
		if(defender instanceof NPC){
			NPC npc = (NPC)defender;
			npc.setEnemy(attacker);
		}
		double nx = 8 * Math.cos(attackBox.getAngle());
		double ny = 8 * Math.sin(attackBox.getAngle());
		defender.move((int)(defender.getPositionX() + nx),(int)(defender.getPositionY() + ny));
	}
	
}
