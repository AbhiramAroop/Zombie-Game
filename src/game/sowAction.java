package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * 
 * @author Abhiram Aroop
 * 
 * An Action used by the Farmer Actor to 
 */

public class sowAction extends Action{
	
	/**
	 * This method uses exists to find a suitable location to set
	 * Crop Ground.
	 * 
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 */
	public void setRandomGroundCrop(Actor actor, GameMap map) {
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if ((e.getDestination().getGround() instanceof Dirt && e.getDestination() != map.locationOf(actor))) {
				e.getDestination().setGround(new Crop());
				break;
				
			}
		}
		
	}
		
	@Override
	/**
	 * Perform the Action of replacing a Dirt Ground with a Crop Ground.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	public String execute(Actor actor, GameMap map) {
		
		setRandomGroundCrop(actor, map);		
		
		return menuDescription(actor);
	}

	@Override
	/**
	 * Returns a descriptive string
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	public String menuDescription(Actor actor) {
		
		return actor + " sowed a Crop";
	}
}
