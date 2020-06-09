package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

/**
 * The action taken by MamboMarie to summon 5 zombies
 * 
 * @author Abhiram Aroop
 *
 */
public class summoningAction extends Action{
	
	/**
	 * Used to add a Zombie at a Random Suitable Location
	 * 
	 * @param map The GameMap of MamboMarie
	 * @return true once a zombie is added
	 */
	public boolean addZombie(GameMap map) {
		
		Random xVal = new Random();
		Random yVal = new Random();
		
		int xRange = map.getXRange().max();
		int yRange = map.getYRange().max();
		
		int x = xVal.nextInt(xRange);
		int y = yVal.nextInt(yRange);
		
		if (!(map.at(x, y).getGround() instanceof Fence) && (map.at(x, y).getActor() == null)) {
			
			map.at(x, y).addActor(new Zombie("Mambo's Zombie"));
			
			return true;
		}
		
		return false;
	}

	@Override
	/**
	 * Perform the Action.
	 *
	 * @param actor The actor performing the action.
	 * @param map The map the actor is on.
	 * @return a description of what happened that can be displayed to the user.
	 */
	public String execute(Actor actor, GameMap map) {
		
		
		for (int i = 0; i < 5; i++) {
			
			boolean summoningStatus = addZombie(map);
			while (summoningStatus == false) {
				summoningStatus = addZombie(map);
			}
		}
		
		return menuDescription(actor);
	}

	@Override
	/**
	 * Returns a descriptive string
	 * @param actor The actor performing the action.
	 * @return the text we put on the menu
	 */
	public String menuDescription(Actor actor) {
		return actor + " has Summoned some Zombies!";
	}

	

}
