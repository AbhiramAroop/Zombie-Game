package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;

/**
 * 
 * @author Abhiram Aroop
 *
 * An Action to upgrade weapons for players, costing them a turn.
 */
public class craftAction extends Action {

	private WeaponItem weapon;
	
	/**
	 * Constructor method to initialise the WeaponItem that needs to be upgraded/replaced. 
	 * 
	 * @param weapon A WeaponItem representing the Item that needs to be upgraded
	 */
	public craftAction(WeaponItem weapon) {
		this.weapon = weapon;
	}	
	
	/**
	 * A boolean to check whether the WeaponItem provided by the Actor is a zombieArm
	 * 
	 * @return true if this.weapon is a zombieArm object, else false.
	 */
	public boolean isZombieArm() {
		if (this.weapon instanceof zombieArm) return true;
		
		return false;
	}
	
	/**
	 * This method replaces a simple weapon with a corresponding upgrade of that WeaponItem
	 * 
	 * @param actor the Actor who's WeponItem is getting upgraded
	 */
	
	public void craftWeapon(Actor actor) {
		
		actor.removeItemFromInventory(this.weapon);
		
		if (isZombieArm()) {
			actor.addItemToInventory(new zombieClub());
			
		}
		
		else {
			actor.addItemToInventory(new zombieMace());
		}
		
		
	}

	/**
	 * Performs the craft Action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	@Override
	public String execute(Actor actor, GameMap map) {
		
		craftWeapon(actor);
		
		if (isZombieArm()) return "Crafted a Zombie Club";
		else return "Crafted a Zombie Mace";
		
	}

	/**
	 * Returns a descriptive string for crating a new WeaponItem
	 * 
	 * @param actor The actor performing the crafting Action.
	 * @return the text we put on the menu
	 */
	@Override
	public String menuDescription(Actor actor) {
		
		if (isZombieArm()) return "Craft Zombie Club";
		else return "Craft Zombie Mace";
		
	}
}


	
	