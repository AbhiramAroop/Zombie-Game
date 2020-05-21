package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class sowAction extends Action{
	
	public int getRandomGroundCrop() {
		
		Random sowPos = new Random();		
		
		int[] posMove = {-1,0,1};
		
		return posMove[sowPos.nextInt(3)];
		
		
		
	}
	
	public Location findRandomGround(Actor actor, GameMap map) {
		
		int xCoordSow = map.locationOf(actor).x();
		int yCoordSow = map.locationOf(actor).y();
		
		int xAdd = getRandomGroundCrop();
		int yAdd = getRandomGroundCrop();
		
		while (xAdd == 0 && yAdd == 0) {
			xAdd = getRandomGroundCrop();
			yAdd = getRandomGroundCrop();
	}
		
		xCoordSow += xAdd;
		yCoordSow += yAdd;
		
		return map.at(xCoordSow, yCoordSow);
		
	}
	public void setRandomGroundCrop(Actor actor, GameMap map) {
		
		int xCoordSow = findRandomGround(actor, map).x();
		int yCoordSow = findRandomGround(actor, map).y();
		
		while (!(map.at(xCoordSow, yCoordSow).getGround() instanceof Dirt)) {
			
			xCoordSow = findRandomGround(actor, map).x();
			yCoordSow = findRandomGround(actor, map).y();
			
		}
		map.at(xCoordSow, yCoordSow).setGround(new Crop());
		
	}
		
		
	

	@Override
	public String execute(Actor actor, GameMap map) {
		
		setRandomGroundCrop(actor, map);		
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		
		return actor + " sowed a Crop";
	}
}
