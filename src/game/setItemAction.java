package game;

import java.util.ArrayList;
import java.util.Collections;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class setItemAction extends Action{
	/**
	 * Item to be set on the ground
	 */
	protected Item item;
	
	/**
	 * Constructor for setItemAction class
	 * 
	 * @param item: item to be set
	 */
	public setItemAction(Item item) {
		this.item = item;
	}

	@Override
	/**
	 * The actor sets the item in the next available adjacent location and
	 * returns the description of the item set.
	 * 
	 * @param actor: the actor setting the item.
	 * @param map: the game map.
	 * 
	 * @return: the description of what item the actor set
	 */
	public String execute(Actor actor, GameMap map) {
		
		ArrayList<Exit> possExits =  new ArrayList<Exit>(map.locationOf(actor).getExits());
		Collections.shuffle(possExits);
		
		for(Exit CoorDrop : map.locationOf(actor).getExits()) {
			CoorDrop.getDestination().addItem(item);
			break;
		}
		return menuDescription(actor);
	}
	
	/**
	 * The actor sets the item in its current location and returns the description 
	 * of the item set.
	 * 
	 * @param actor: the actor setting the item.
	 * @param map: the game map.
	 * 
	 * @return: the description of what item the actor set.
	 */
	public String setCurrent(Actor actor, GameMap map) {
		map.locationOf(actor).addItem(item);
		return menuDescription(actor);
	}
	
	/**
	 * @Override
	 * A description of what the actor sets down
	 * 
	 * @param actor: the actor setting the item.
	 * 
	 * @return: a description of what item the actor set.
	 */
	public String menuDescription(Actor actor) {
		
		if (actor instanceof Zombie) {
			return actor.toString() + " loses its " + item;
		}
		else {
			return actor.toString() + " sets the " + item;
		}
	}
}
