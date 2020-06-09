package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * 
 * @author Abhiram Aroop
 *
 * A new Human object with special Actions, he can add a Crop, fertilize and harvest it on the GameMap.
 */
public class Farmer extends Human{
	
	public Farmer(String name) {
		super(name, 'F', 50);
		
	}

	@Override
	/**
	 * Select and return an action to perform on the current turn. (newly added craftAction, harvestAction, fertilizeAction) 
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		Location location = map.locationOf(this);
		
		if (location.getGround() instanceof Dirt) {
			Random sowChance = new Random();
			if (sowChance.nextInt(3) == 0) {
				return new sowAction();
			}
		}
		
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().getGround() instanceof Crop))
				continue;
			if (((Crop) e.getDestination().getGround()).isGrown()) {
				return (new harvestAction(e.getDestination()));
			}
		}
				
		if (location.getGround() instanceof Crop && !(lastAction instanceof fertilizeAction) && !(lastAction instanceof sowAction)) {
			return new fertilizeAction();
			
		}
		
		return behaviour.getAction(this, map);
		
	}
	
}
		




	
