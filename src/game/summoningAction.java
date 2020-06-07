package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;

public class summoningAction extends Action{
	
	public boolean addZombie(GameMap map) {
		
		Random xVal = new Random();
		Random yVal = new Random();
		
		int xRange = map.getXRange().max();
		int yRange = map.getYRange().max();
		
		int x = xVal.nextInt(xRange);
		int y = yVal.nextInt(yRange);
		
		if (!(map.at(x, y).getGround() instanceof Fence) && (map.at(x, y).getActor() == null)) {
			
			map.at(x, y).addActor(new Zombie(null));
			
			return true;
		}
		
		return false;
	}

	@Override
	public String execute(Actor actor, GameMap map) {
		
		
		
		for (int i = 0; i < 5; i++) {
			
			boolean summoningStatus = addZombie(map);
			while (summoningStatus == false) {
				summoningStatus = addZombie(map);
			}
		}
		
		return menuDescription(actor);
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + " has Summoned some Zombies!";
	}

	

}
