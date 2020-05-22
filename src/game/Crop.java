package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

/**
 * 
 * @author Abhiram Aroop 
 *
 * A new Ground type Object that is represented in two states on the GameMap.
 * If it's fully grown its represented using a 'C', else 'c'. 
 *
 */


public class Crop extends Ground{

	private status state = status.UNRIPE;
	private int age = 0;
	
	/**
	 * A representation on the GameMap
	 */
	
	public Crop() {
		super('c');
	}
	
	/**
	 * To check if the Crop as RIPE status
	 * 
	 * @return A boolean based on the status
	 */
	
	public boolean isGrown() {
		
		if (state == status.RIPE) return true;
		
		return false;
		
	}
	/**
	 * A method that increases the age of the crop by 1 every turn
	 * 
	 * @param location The location of the Crop on the GameMap
	 */
	
	public void tick(Location location) {
		super.tick(location);

		age++;
		
		if (this.age == 20) {
			state = status.RIPE;
			this.displayChar = 'C';
		}
		
		
	}
	
	/**
	 * Manually adds 10 to the age of the Crop when called.
	 */
	
	public void addAge() {
		if (this.age < 20) this.age = Math.min(20,this.age + 10);
		
	}
	
	
	
	
	/**
	 * 
	 * @author Abhiram Aroop
	 *
	 * An enum to represent the Crop at different stages of growth.
	 */
	
	enum status {
		UNRIPE, RIPE
	}	
	
}
