package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * Class representing an ordinary human.
 * 
 * 
 * @author ram
 *
 */
public class Human extends ZombieActor {
	
	private Random successRate = new Random();
	private int toZombieCounter = 0;
	private Behaviour behaviour = new WanderBehaviour();

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

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// FIXME humans are pretty dumb, maybe they should at least run away from zombies?
		if (!this.isConscious()) {
			if (toZombieCounter < 11) {
				toZombieCounter += 1;
			}
			if ((4 < toZombieCounter && toZombieCounter < 11 && successRate.nextBoolean()) ||
					toZombieCounter == 10) {
				Location currentLocation = map.locationOf(this);
				map.removeActor(this);
				map.addActor(new Zombie("Geerrrrr"), currentLocation);
				return new DoNothingAction();	
			}
		}
		return behaviour.getAction(this, map);
	}

}
