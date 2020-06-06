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
		List<Item> inventory = super.getInventory();
		
		// Handle multi-turn Actions
		if (lastAction.getNextAction() != null)
			return lastAction.getNextAction();
		
		if (lastAction instanceof eatAction)
			return lastAction;
		
		for (Item groundItem : map.locationOf(this).getItems()) {
			if (groundItem instanceof Ammo) {
				groundItem.getPickUpAction().execute(this, map);
			}
		}
		
		
		for (Object item : inventory) {

			if (item instanceof Food && this.hitPoints < this.maxHitPoints) {
				actions.add(new eatAction((Food) item));
				break;
			}
			else if (item instanceof zombieArm || item instanceof zombieLeg) {
				actions.add(new craftAction((WeaponItem) item));
				break;
			}
			else if (item instanceof Shotgun && ((Shotgun) item).canExecute()) {
				actions.add(new ShotgunAction());
				break;
			}
			else if (item instanceof SniperRifle && ((SniperRifle) item).canExecute(this, map)) {
				actions.add(new SniperAction(this, map));
				break;
			}
			else if (item instanceof Ammo) {
				for (Object item2 : inventory) {
					((RangedWeapon) item2).addAmmo();
					break;
				}
			}
		}
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (((Crop) e.getDestination().getGround()).isGrown()) {
				actions.add(new harvestAction(e.getDestination()));
			}
		}
		
		return menu.showMenu(this, actions, display);
	}

}