package game;

import edu.monash.fit2099.engine.Ground;

/**
 * A type of ground that can be entered by players but 
 * cannot be used to plant crops
 * 
 * @author Abhiram Aroop
 *
 */

public class Floor extends Ground {
	/**
	 * Constructor for Floor
	 */
	public Floor() {
		super('-');
		
	}
}
