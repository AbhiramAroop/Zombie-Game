package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * An action that allows the player to reload their ranged weapons.
 * 
 * @author Immanuel Andrew Christabel
 */
public class ReloadAction extends Action{
	private Item usedAmmo;
	
	@Override
	/**
	 * Resets all the actor's ranged weapons to its full capacity.
	 * 
	 * @param actor The player.
	 * @param map The game map.
	 * @return A description of what the player did (i.e. reload their weapon).
	 */
	public String execute(Actor actor, GameMap map) {

		for (Item item : actor.getInventory()) {
			if (item instanceof Ammo) {
				usedAmmo = item;
			}
			if (item instanceof RangedWeapon) {
				((RangedWeapon) item).reload();
			}
		}
		actor.removeItemFromInventory(usedAmmo);
		
		return menuDescription(actor);
	}

	@Override
	/**
	 * Returns a string saying what the actor did (i.e. reload their weapon).
	 * 
	 * @param actor The player
	 */
	public String menuDescription(Actor actor) {
		return actor.toString() + " reload weapons";
	}

}
