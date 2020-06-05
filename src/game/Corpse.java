package game;

import java.util.Random;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;


/**
 * A portable item of a dead actor's body. The corpse will turn into a Zombie after
 * 5 to 10 turns. If there are no available spots for the Zombie to spawn, it will
 * only spawn once an available spot is open.
 * 
 * @author Immanuel Andrew Christabel
 */
public class Corpse extends PortableItem {
	/**
	 * Variable that determines the likelihood of a Zombie spawning
	 */
	private Random successRate = new Random();
	/**
	 * Counter that holds the number of turns a player has died. After 5 to 10
	 * turns, the player will turn into a Zombie. 
	 */
	private Integer toZombieCounter = new Integer(0);

	/**
	 * Makes a Corpse object
	 * 
	 * @param name: name of the corpse
	 */
	public Corpse(String name) {
		super(name, '%');
	}
	
	/**
	 * Adds a Zombie to the next available adjacent position of the actor. If a Zombie is
	 * spawned successfully, it will return true. Else, if there are no available spots for
	 * the Zombie to spawn, it will return false. 
	 * 
	 * @param currentLocation: the location of the actor.
	 * 
	 * @return true if the Zombie spawned. False otherwise
	 */
	private Boolean addZombie(Location currentLocation) {
		
		for (Exit posDrop : currentLocation.getExits()) {
			if (!posDrop.getDestination().containsAnActor()) {
				posDrop.getDestination().addActor(new Zombie(this.toString()));
				System.out.println(this.toString() + " comes back fromt the dead!");
				return true;
			}
		}
		return false;
	}
	
	@Override
	/**
	 * Increments the toZombieCounter by 1. If the toZombieCounter is between 4 to 9,
	 * there is a 50% chance that the corpse is removed from the player's inventory
	 * and a Zombie is spawned. This is guaranteed if the toZombieCounter is 10.
	 * 
	 * @param currentLoaction: the location of the actor.
	 * @param actor: the actor that has a corpse in its inventory.
	 */
	public void tick(Location currentLocation, Actor actor) {
		if (toZombieCounter < 10) {
			toZombieCounter += 1;
		}
		
		if ((toZombieCounter > 4 && toZombieCounter < 10 && successRate.nextBoolean()) ||
				(toZombieCounter.equals(10))) {
			
			if (this.addZombie(currentLocation)) {
				actor.removeItemFromInventory(this);
			}
		}
	}
	
	@Override
	/**
	 * Increments the toZombieCounter by 1. If the toZombieCounter is between 4 to 9,
	 * there is a 50% chance that the corpse turns into a Zombie. This is guaranteed 
	 * if the toZombieCounter is 10.
	 */
	public void tick(Location currentLocation) {
		if (toZombieCounter < 10) {
			toZombieCounter += 1;
		}
		
		if ((toZombieCounter > 4 && toZombieCounter < 10 && successRate.nextBoolean()) ||
				(toZombieCounter.equals(10))) {
			
			if (currentLocation.containsAnActor()) {
				if (this.addZombie(currentLocation)) {
				currentLocation.removeItem(this);
				}
			}
			
			else {
				currentLocation.addActor(new Zombie(this.toString()));
				currentLocation.removeItem(this);
				System.out.println(this.toString() + " comes back fromt the dead!");
			}
		}
	}

}
