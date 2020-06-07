package game;

import java.util.ArrayList;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;

/**
 * A ranged weapon that can shoot a zombie up to 4 spaces away. It does more damage based on the
 * player's concentration level.
 * 
 * @author Immanuel Andrew Christabel
 *
 */
public class SniperRifle extends RangedWeapon{
	private int concentration = 0;
	/**
	 * Initializes a sniper rifle weapon. A sniper rifle stats:
	 * - 12 ammo capacity
	 * - 4 x 4 area range where
	 */
	public SniperRifle() {
		super("Sniper rifle", 'R', 12);
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
	 * Checks if the Sniper Rifle can be used this turn (i.e. if there are any Zombies
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
				if (inRange(x - 4 + bound1, y - 4 + bound2, map)) {
					if (map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)) instanceof Zombie) {
						Targets.add(map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)));
					}
				}
			}
		}	
		return (Targets.size() != 0 && super.hasAmmo()) || this.concentration < 2;
	}
	
	/**
	 * Set the sniper rifle's concentration level.
	 * 
	 * @param x The sniper rifle's new concentration level.
	 */
	public void setCon(int x) {
		assert (-1 < x && x < 3);
		concentration = x;
	}
	
	/**
	 * Get the concentration level of the sniper rifle.
	 * 
	 * @return An integer of the concentration's current level.
	 */
	public int getCon() {
		return new Integer(concentration);
	}

}
