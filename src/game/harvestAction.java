
package game;

/**
 * 
 */

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * 
 * @author Abhiram Aroop
 * 
 *This class is used when a player or a Farmer needs to harvest a nearby crop to obtain Food. Different
 * actions are conducted based on the Actor, Player gets a Food in their inventory while Farmers drop a
 * Food item on the harvested Location.
 */

public class harvestAction extends Action{
	
	private Location location;
	
	/**
	 * This is a constructor method that initialises the Location where the harvesting process
	 * needs to take place.
	 * 
	 * @param location a Location object representing the Location that needs to be modified during harvesting.
	 */
	public harvestAction(Location location) {
		
		this.location = location;
		
	}
	/**
	 * Perform the Harvesting Action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user once the process is completed.
	 */

	public String execute(Actor actor, GameMap map) {
		
		if (actor instanceof Farmer) {
			
			location.setGround(new Dirt());
			Food food = new Food();
			
			location.addItem(food);
			
		}
		
		else if (actor instanceof Player) {
		
			location.setGround(new Dirt());
			
			Food food = new Food();
			
			actor.addItemToInventory(food);
			
	
		}
		
		return actor + " has harvested a Crop";
	}

	/**
	 * Returns a descriptive string for the harvesting process
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	
	public String menuDescription(Actor actor) {
		return "Harvest Crop";
	}

	
	

}
