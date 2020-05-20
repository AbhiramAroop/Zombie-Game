package game;

import java.util.List;

import edu.monash.fit2099.demo.mars.zombieArm;
import edu.monash.fit2099.demo.mars.zombieLeg;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
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
			if (inventory.get(i) instanceof Food) {
				actions.add(new eatAction((Food) inventory.get(i)));
				break;
			}
		}
		
		if (map.locationOf(this).getGround() instanceof Crop) {
			Crop crop = (Crop) map.locationOf(this).getGround();
			if (crop.isGrown()) 
				actions.add(new harvestAction());
		}
	
		for (int i = 0; i < this.getInventory().size(); i++) {
			if (this.getInventory().get(i) instanceof zombieArm || this.getInventory().get(i) instanceof zombieLeg)  {
				actions.add(new craftAction());
				
			}
		}
		
		return menu.showMenu(this, actions, display);
	}

}