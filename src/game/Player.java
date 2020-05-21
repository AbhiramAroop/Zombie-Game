package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
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
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		if (lastAction.getClass().getName() == "eatAction")
			return lastAction;
		
		List<Item> inventory = getInventory();
		
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) instanceof Food && this.hitPoints < this.maxHitPoints) {
				actions.add(new eatAction((Food) inventory.get(i)));
				break;
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