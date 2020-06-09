package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;

/**
 * A new type of entity that summons 5 Zombies every 10 turns.
 * 
 * @author Abhiram Aroop
 *
 */

public class MamboMarie extends ZombieActor{

	/**
	 * Action for MamboMarie to pathfind
	 */
	private Behaviour behaviour = new WanderBehaviour();
	private int summoningCounter = 1;
	
	/**
	 * A constructor for ZombieActor  
	 */
	
	public MamboMarie() {
		super("Mambo Marie", 'M', 100, ZombieCapability.UNDEAD);
	}

	@Override
	/**
	 * Select and return an action to perform on the current turn. 
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (this.summoningCounter == 10) { //Counter to check how many turns till summoning zombies
			this.summoningCounter = 1;
			return new summoningAction();
		}
		
		if (!(lastAction instanceof summoningAction)) { 
			this.summoningCounter ++;
		}
		
		
		return behaviour.getAction(this, map);
	}

	

}
