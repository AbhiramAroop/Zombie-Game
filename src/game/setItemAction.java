package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class setItemAction extends Action{
	protected Item item;
	
	public setItemAction(Item item) {
		this.item = item;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		map.locationOf(actor).addItem(item);
		return menuDescription(actor);
	}
	
	@Override
	public String menuDescription(Actor actor) {
		
		if (actor instanceof Zombie) {
			return actor.toString() + " loses its" + item.getClass().getName();
		}
		else {
			return actor.toString() + " sets the " + item;
		}
	}
}
