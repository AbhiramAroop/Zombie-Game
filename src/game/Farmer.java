package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class Farmer extends Human{
	
	private Random sowing = new Random();
	private sowAction sow = new sowAction(null);
	
	public Farmer(String name) {
		super(name, 'F', 50);
		
	}
	
	public void trySow(Location location) {
		if (location.getGround() instanceof Dirt) {
			
			if (sowing.nextInt(3) == 1) {
				
				this.sow = new sowAction(location); //IMPLEMENT SOW ACTION
				sow.setGroundCrop();
			}
			
		}
	}
	
	public void fertilize(Location location) {
		
		if (location.getGround() instanceof Crop) {
			
			Crop crop = (Crop) location.getGround(); //NEED TESTING
			
			crop.addAge();
		}	
	}
	
	public void tryHarvest(Location location, GameMap map, Farmer farmer) {
		
		if (location.getGround() instanceof Crop) {
			Crop crop = (Crop) location.getGround();
			if (crop.isGrown()) {
				harvestAction harvest = new harvestAction();
				harvest.execute(farmer, map);
			}
		}
		
		
	}
	
		
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		tryHarvest(map.locationOf(this), map, this);
		fertilize(map.locationOf(this));
		trySow(map.locationOf(this));		
		
		return behaviour.getAction(this, map);
		
	}
		



}
