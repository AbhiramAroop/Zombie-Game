package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import game.Food;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * 
 * @author Abhiram Aroop
 *
 * This class is used to heal a Actor given that they have a Food item in their inventory.
 * It removes the Food item from the Actor's inventory and heals them for 10 hitpoints.
 */

public class eatAction extends Action{
	
	protected Item food;

	/**
	 * Constructor to initialise the food Item.
	 * 
	 * @param food A Food object representing the Food item of the Actor.
	 */
	public eatAction(Food food) {
		
		this.food = food;
		
		}
	
	/**
	 * Perform the Harvest Action.
	 *
	 * @param actor The actor performing the harvest action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	
	public String execute(Actor actor, GameMap map) {	
		
		if (this.food != null) {
			actor.heal(10);
			actor.removeItemFromInventory(this.food);
			
		}
		
		return actor + " ate some food and replenished some health!";
	}

	/**
	 * Returns a descriptive string to represent the eating process of the Actor
	 * 
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu for the actor
	 */
	
	public String menuDescription(Actor actor) {
		return actor + "eat food";
	}


}
