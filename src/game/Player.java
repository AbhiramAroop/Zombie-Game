package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Menu;
import edu.monash.fit2099.engine.WeaponItem;

/** 
 * Class representing the Player.
 */
public class Player extends Human {

	private Menu menu = new Menu();

	/**
	 * Constructor.
	 *
	 * @param name        Name to call the player in the UI
	 * @param displayChar Character to represent the player in the UI
	 * @param hitPoints   Player's starting number of hitpoints
	 */
	public Player(String name, char displayChar, int hitPoints) {
		super(name, displayChar, hitPoints);
	}
	

	@Override
	/**
	 * Select and return an action to perform on the current turn. (newly added eatAction, harvestAction, craftAction)
	 *
	 * @param actions    collection of possible Actions for this Actor
	 * @param lastAction The Action this Actor took last turn. Can do interesting things in conjunction with Action.getNextAction()
	 * @param map        the map containing the Actor
	 * @param display    the I/O object to which messages may be written
	 * @return the Action to be performed
	 */
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		if (lastAction.getClass().getName() == "eatAction")
			return lastAction;
		
		actions.add(new quitAction());
		
		if (map.locationOf(this).getGround() instanceof Vehicle) {
			return new DoNothingAction();
		}
		
		
		List<Item> inventory = getInventory();
		
		for (Object item : inventory) {
			if (item instanceof Food && this.hitPoints < this.maxHitPoints) {
				actions.add(new eatAction((Food) item));
				break;
			}
			else if (item instanceof zombieArm || item instanceof zombieLeg) {
				actions.add(new craftAction((WeaponItem) item));
				break;
			}
			else if (item instanceof Shotgun) {
				actions.add(new ShotgunAction());
			}
			else if (item instanceof SniperRifle && ((SniperRifle) item).canExecute(this, map)) {
				actions.add(new SniperAction(this, map));
			}
		}
		
	
		
		for (int i = 0; i < this.getInventory().size(); i++) {
			if (this.getInventory().get(i) instanceof zombieArm || this.getInventory().get(i) instanceof zombieLeg)  {
				actions.add(new craftAction((WeaponItem) this.getInventory().get(i)));
				break;
				
			}
		}
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().getGround() instanceof Crop))
				continue;
			if (((Crop) e.getDestination().getGround()).isGrown()) {
				actions.add(new harvestAction(e.getDestination()));
			}
		}
		
		return menu.showMenu(this, actions, display);
	}

}