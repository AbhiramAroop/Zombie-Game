package game;

import java.util.Random;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Weapon;

/**
 * Special Action for attacking other Actors.
 */
public class AttackAction extends Action {

	/**
	 * The Actor that is to be attacked
	 */
	protected Actor target;
	/**
	 * Random number generator
	 */
	protected Random rand = new Random();

	/**
	 * Constructor.
	 * 
	 * @param target the Actor to attack
	 */
	public AttackAction(Actor target) {
		this.target = target;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// The weapon the actor is holding
		Weapon weapon = actor.getWeapon();
		// Flag is used to indicate whether the actor will hit or miss its attack.
		Boolean flag = false;
		// Used to generate a random number
		Random successRate = new Random();
		
		// If the weapon is a bite, change the success rate to 1/3.
		if (weapon.verb() == "bites") {
			if (successRate.nextInt(4) == 3) {
				flag = true;
			}
		}
		// Else, the success rate is a 1/2/
		else {
			flag = successRate.nextBoolean();
		}
		// If the flag is false, the actor misses.
		if (flag == false) {
			return actor + " misses " + target + ".";
		}
		// Else, the actor executes its attack and heals itself.
		actor.heal(5);
		int damage = weapon.damage();
		String result = actor + " " + weapon.verb() + " " + target + " for " + damage + " damage.";

		target.hurt(damage);
		if (!target.isConscious()) {
			Item corpse = new PortableItem("dead " + target, '%');
			map.locationOf(target).addItem(corpse);
			
			Actions dropActions = new Actions();
			for (Item item : target.getInventory())
				dropActions.add(item.getDropAction());
			for (Action drop : dropActions)		
				drop.execute(target, map);

			map.removeActor(target);	
			
			result += System.lineSeparator() + target + " is killed.";
		}

		return result;
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " attacks " + target;
	}
}
