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
	public boolean addZombie(GameMap map, Actor actor) {
		
		Random xVal = new Random();
		Random yVal = new Random();
		
		int x = xVal.nextInt(10) + (map.locationOf(actor).x() - 4);
		int y = yVal.nextInt(10)  + (map.locationOf(actor).y() - 4);
		
		if (!(map.at(x, y).getGround() instanceof Fence) && (map.at(x, y).getActor() == null) && (map.getXRange().min() < x) &&  (x < map.getXRange().max()) && (map.getYRange().min() < y) && (y < map.getYRange().max()) )  {
			
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
			
			boolean summoningStatus = addZombie(map, actor);
			while (summoningStatus == false) {
				summoningStatus = addZombie(map, actor);
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
