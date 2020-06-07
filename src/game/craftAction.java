package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * Special Action for attacking other Actors.
 */
public class craftAction extends Action {

	private WeaponItem weapon;
	
	public craftAction(WeaponItem weapon) {
		this.weapon = weapon;
	}	
	
	public boolean isZombieArm() {
		if (this.weapon instanceof zombieArm) return true;
		
		return false;
	}
	
	public void craftWeapon(Actor actor) {
		
		actor.removeItemFromInventory(this.weapon);
		
		if (isZombieArm()) {
			actor.addItemToInventory(new zombieClub());
			
		}
		
		else {
			actor.addItemToInventory(new zombieMace());
		}
		
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		
		craftWeapon(actor);
		
		if (isZombieArm()) return "Crafted a Zombie Club";
		else return "Crafted a Zombie Mace";
		
	}

	@Override
	public String menuDescription(Actor actor) {
		
		if (isZombieArm()) return "Craft Zombie Club";
		else return "Craft Zombie Mace";
		
	}
}


	
	