package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class sowAction extends Action{
	
	private Location location = new Location(null, 0, 0);

	public sowAction(Location location) {
		
		this.location = location;
		
	}
	
	public void setGroundCrop() {
		
		Crop crop = new Crop();
		location.setGround(crop);
		
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return null;
	}
}
