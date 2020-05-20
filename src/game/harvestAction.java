package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class harvestAction extends Action{
	
	public String execute(Actor actor, GameMap map) {
		
		if (actor instanceof Farmer) {
			
			map.locationOf(actor).setGround(new Dirt());
			Food food = new Food();
			
			setItemAction placeItem = new setItemAction(food);
			placeItem.execute(actor, map);
			
		}
		
		else if (actor instanceof Player) {
		
			map.locationOf(actor).setGround(new Dirt());
			
			Food food = new Food();
			
			actor.addItemToInventory(food);
			
	
		}
		
		return "harvesting";
	}

	public String menuDescription(Actor actor) {
		return "Harvest";
	}

	
	

}
