package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

//ADD standing next to crop and harvest and player 
public class Farmer extends Human{
	
	public Farmer(String name) {
		super(name, 'F', 50);
		
	}

	
	
	
		
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		Location location = map.locationOf(this);
		
		List<Exit> exits = new ArrayList<Exit>(map.locationOf(this).getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if (!(e.getDestination().getGround() instanceof Crop))
				continue;
			if (((Crop) e.getDestination().getGround()).isGrown()) {
				return (new harvestAction(e.getDestination()));
			}
		}
		
		if (location.getGround() instanceof Crop && !(lastAction instanceof fertilizeAction) && !(lastAction instanceof sowAction)) {
			return new fertilizeAction();
			
		}
		
		if (location.getGround() instanceof Dirt) {
			Random sowChance = new Random();
			if (sowChance.nextInt(3) == 0) {
				return new sowAction();
			}
		}
	
		
		return behaviour.getAction(this, map);
		
	}
	
}
		




	
