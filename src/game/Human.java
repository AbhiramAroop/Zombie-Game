package game;

import java.util.List;
import java.util.Random;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.PickUpItemAction;

/**
 * Class representing an ordinary human.
 *
 */
public class Human extends ZombieActor {

	protected Behaviour behaviour = new WanderBehaviour();

	/**
	 * The default constructor creates default Humans
	 * 
	 * @param name the human's display name
	 */
	public Human(String name) {
		super(name, 'H', 50, ZombieCapability.ALIVE);
	}
	
	/**
	 * The protected constructor can be used to create subtypes
	 * of Human, such as the Player
	 * 
	 * @param name the human's display name
	 * @param displayChar character that will represent the Human in the map 
	 * @param hitPoints amount of damage that the Human can take before it dies
	 */
	protected Human(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints, ZombieCapability.ALIVE);
	}
	
	/**
	 * Select and return an action to perform on the current turn. (newly added eatAction)
	 * Actor can only conduct 1 Action per turn.
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		
		for (int i = 0; i < map.locationOf(this).getItems().size(); i++) {
			if (map.locationOf(this).getItems().get(i) instanceof Food) {
				return new PickUpItemAction(map.locationOf(this).getItems().get(i));
			}
		}

		List<Item> inventory = getInventory();
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Food && this.hitPoints < this.maxHitPoints) {
				return new eatAction((Food) inventory.get(i));
			}
		}
		
		return behaviour.getAction(this, map);
	}
}