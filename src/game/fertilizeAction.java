package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class fertilizeAction extends Action {

	@Override
	public String execute(Actor actor, GameMap map) {
		
		Location location = map.locationOf(actor);
		Crop crop;
		crop = (Crop) location.getGround();
		crop.addAge();
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
	
		return actor + " fertilized a Crop.";
	}


}
