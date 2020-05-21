package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class harvestAction extends Action{
	
	private Location location;
	
	public harvestAction(Location location) {
		
		this.location = location;
		
	}
	
	public String execute(Actor actor, GameMap map) {
		
		if (actor instanceof Farmer) {
			
			location.setGround(new Dirt());
			Food food = new Food();
			
			location.addItem(food);
			
		}
		
		else if (actor instanceof Player) {
		
			location.setGround(new Dirt());
			
			Food food = new Food();
			
			actor.addItemToInventory(food);
			
	
		}
		
		return "harvesting";
	}

	public String menuDescription(Actor actor) {
		return "Harvest";
	}

	
	

}
