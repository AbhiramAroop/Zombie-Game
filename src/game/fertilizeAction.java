package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * 
 * @author Abhiram Aroop
 *
 * A action that increases the age of a Crop at the Location of the
 * Farmer(Actor) on the GameMap.
 */
public class fertilizeAction extends Action {

	@Override
	/**
	 * Perform the fertilization Action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	public String execute(Actor actor, GameMap map) {
		
		Location location = map.locationOf(actor);
		Crop crop;
		crop = (Crop) location.getGround();
		crop.addAge();
		
		return menuDescription(actor);
	}

	@Override
	/**
	 * Returns a descriptive string based of the fertilization process.
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	public String menuDescription(Actor actor) {
	
		return actor + " fertilized a Crop.";
	}


}
