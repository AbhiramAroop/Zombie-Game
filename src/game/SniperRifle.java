package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

public class SniperRifle extends RangedWeapon{
	
	/**
	 * Initializes a Sniper Rifle weapon
	 */
	public SniperRifle() {
		super("Sniper rifle", 'R');
	}
	
	/**
	 * Checks if the current coordinates are within the range of the Game map
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param map The Game map
	 * @return true if the coordinate are within the Game map range. Else otherwise.
	 */
	private boolean inRange(int x, int y, GameMap map) {
		return (map.getXRange().min() <= x && 
				x <= map.getXRange().max() && 
				map.getYRange().min() <= y && 
				y<= map.getYRange().max()); 
	}
	
	/**
	 * Checks if the Sniper Rifle can be used at a specific turn (i.e. if there are any Zombies
	 * within the range of a 4x4 square with its center at the actor's current position).
	 * 
	 * @param actor The actor using the Sniper Rifle
	 * @param map The Game map
	 * @return true of the actor can use the Sniper Rifle (i.e. shoot a Zombie). False otherwise.
	 */
	public boolean canExecute(Actor actor, GameMap map) {
		ArrayList<Actor> Targets = new ArrayList<>();
		Location currentPos = map.locationOf(actor);
		int x = currentPos.x();
		int y = currentPos.y();
		
		
		for (int bound1 = 0 ; bound1 <= 8 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 <= 8 ; bound2 ++) {
				if (inRange(x - 4 + bound1, y + 4 + bound2, map)) {
					if (map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)) instanceof Zombie) {
						Targets.add(map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)));
					}
				}
			}
		}	
		return Targets.size() != 0;
	}

}
