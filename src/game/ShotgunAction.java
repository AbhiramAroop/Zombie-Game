package game;

import java.util.Random;
import java.util.Scanner;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

/**
 * An action that allows the player to use the shotgun.
 * 
 * @author Immanuel Andrew Christabel
 */
public class ShotgunAction extends Action{
	
	private String[] shootDir = {"N: North", "E: East", "S: South", "W: West",
			"NE: North-East","NW: North-West", "SE: South-East", "SW: South-West"};
	private Scanner userInput = new Scanner(System.in);
	private Random successRate = new Random();
	private String shotAlive = "";
	private String shotDead = "";
	
	/**
	 * Allows the actor to shoot either North or South with the shotgun.
	 * 
	 * @param x The x coordinate of the actor's current position.
	 * @param y The y coordinate of the actor's current position.
	 * @param map The Game map.
	 * @param dir The direction the actor is shooting (i.e. N or S).
	 */
	private void shootNtoS(Actor player, GameMap map, String dir) {
		int x = map.locationOf(player).x();
		int y = map.locationOf(player).y();
		
		int z = 1;
		
		if (dir.equals("N")) {
			z = -1;
		}
		for (int bound = 1 ; bound <= 3 ; bound ++) {
			for (int i = x - bound ; i <= x + bound ; i++) {
				if (inRange(i, y+z*bound, map)) {
					Actor target = map.getActorAt(map.at(i, y + z*bound));
					if (target instanceof Zombie || target instanceof MamboMarie) {
						shoot(target, player, map);
					}
				}
			}
		}
	}
	
	
	/**
	 * Allows the actor to shoot either East or West with the shotgun.
	 * 
	 * @param x The x coordinate of the actor's current position.
	 * @param y The y coordinate of the actor's current position.
	 * @param map The Game map.
	 * @param dir The direction the actor is shooting (i.e. E or W).
	 */
	private void shootEtoW(Actor player, GameMap map, String dir) {
		int x = map.locationOf(player).x();
		int y = map.locationOf(player).y();
		
		int z = 1;
		
		if (dir.equals("W")) {
			z = -1;
		}
		for (int bound = 1 ; bound <= 3 ; bound ++) {
			for (int i = y - bound ; i <= y + bound ; i++) {
				if (inRange(x + z*bound, i, map)) {
					Actor target = map.getActorAt(map.at(x + z*bound, i));
					if (target instanceof Zombie || target instanceof MamboMarie) {
						shoot(player, target, map);
					}
				}
			}
		}
	}
	
	/**
	 * Allows the actor to shoot either North-West or South-East with the shotgun.
	 * 
	 * @param x The x coordinate of the actor's current position.
	 * @param y The y coordinate of the actor's current position.
	 * @param map The Game map.
	 * @param dir The direction the actor is shooting (i.e. NW or SE).
	 */
	private void shootNWtoSE(Actor player, GameMap map, String dir) {
		int x = map.locationOf(player).x();
		int y = map.locationOf(player).y();
		
		int z = 0;
		
		if (dir.equals("NW")) {
			z = 3;
		}
		
		for (int bound1 = 0 ; bound1 <= 3 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 >= -3 ; bound2 -= 1) {
				if (inRange(x - z + bound1, y - z + bound2, map)) {
					Actor target = map.getActorAt(map.at(x - z + bound1, y - z + bound2));
					if (target instanceof Zombie || target instanceof MamboMarie) {
						shoot(player, target, map);
					}
				}
			}
		}
	}
	
	/**
	 * Allows the actor to shoot either North-East or South-West with the shotgun.
	 * 
	 * @param x The x coordinate of the actor's current position.
	 * @param y The y coordinate of the actor's current position.
	 * @param map The Game map.
	 * @param dir The direction the actor is shooting (i.e. NE or SW).
	 */
	private void shootNEtoSW(Actor player, GameMap map, String dir) {
		int x = map.locationOf(player).x();
		int y = map.locationOf(player).y();
		
		int z = 0;
		
		if (dir.equals("SW")) {
			z = 3;
		}
		
		for (int bound1 = 0 ; bound1 <= 3 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 <= 3 ; bound2 ++) {
				if (inRange(x - z + bound1, y + z + bound2, map)) {
					Actor target = map.getActorAt(map.at(x - z + bound1, y + z + bound2));
					if (target instanceof Zombie || target instanceof MamboMarie) {
						shoot(player, target, map);
					}
				}
			}
		}
	}
	
	/**
	 * Checks if the current coordinates are within the range of the Game map
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param map The Game map.
	 * @return true if the coordinate are within the Game map range. Else otherwise.
	 */
	private boolean inRange(int x, int y, GameMap map) {
		return (map.getXRange().min() <= x && 
				x <= map.getXRange().max() && 
				map.getYRange().min() <= y && 
				y<= map.getYRange().max()); 
	}
	
	@Override
	/**
	 * Allows the actor to shoot in one of eight directions:
	 * North, East, South, West, North-East, South-East, North-West, South-West.
	 * 
	 * @param actor The actor using the Shotgun.
	 * @param map The Game map.
	 * @return a description of what the actor did.
	 */
	public String execute(Actor player, GameMap map) {
		int ammo = 0;
		for (Item item : player.getInventory()) {
			if (item instanceof Shotgun) {
				ammo = ((Shotgun) item).getAmmo();
				break;
			}
		}
		
		while (true) {
			System.out.println("");
			System.out.println("Ammunition: " + ammo);
			System.out.println("Shoot direction options: ");
			for (String direction : shootDir) {
				System.out.println(direction);
			}	
			String shootDir = userInput.next();
			
			if (shootDir.equals("N")) {
				shootNtoS(player, map, "N");
				break;
			}
			else if (shootDir.equals("S")) {
				shootNtoS(player, map, "S");
				break;
			}
			else if (shootDir.equals("E")) {
				shootEtoW(player, map, "E");
				break;
			}
			else if (shootDir.equals("W")) {
				shootEtoW(player, map, "W");
				break;
			}
			else if (shootDir.equals("NW")) {
				shootNWtoSE(player, map, "NW");
				break;
			}
			else if (shootDir.equals("SE")) {
				shootNWtoSE(player, map, "SE");
				break;
			}
			else if (shootDir.equals("NE")) {
				shootNEtoSW(player, map, "NE");
				break;
			}
			else if (shootDir.equals("SW")) {
				shootNEtoSW(player, map, "SW");
				break;
			}
			else {
				System.out.println("Invalid move. Choose a valid move: ");
			}
		}
		for (Item item : player.getInventory()) {
			if (item instanceof Shotgun) {
				((Shotgun) item).reduceAmmo();
				break;
			}
		}
		return shotOutcome();
	}
	

	/**
	 * 75% chance to shoot a zombie for 30 damage.
	 * 
	 * @param player The player shooting.
	 * @param target The zombie being shot.
	 * @param map The game map.
	 */
	private void shoot(Actor player, Actor target, GameMap map) {
		if (successRate.nextInt(4) != 0) {
			target.hurt(35);
			if (target.isConscious()) {
				if (shotAlive.length() == 0) {
					shotAlive = shotAlive.concat(target.toString());
				}
				else {
					shotAlive = shotAlive.concat(", " + target.toString());
				}
			}
			else {
				if (shotDead.length() == 0) {
					shotDead = target.toString();
				}
				else {
					shotDead = shotDead.concat(", " + target.toString());
				}
				map.removeActor(target);
			}
		}
		
	}
	
	/**
	 * Returns the outcome after a player has used the shotgun.
	 * 
	 * @return A string denoting all the zombies the player has killed/hurt.
	 */
	private String shotOutcome() {
		if (shotAlive.length() > 0) {
			shotAlive = "Player shot: " + shotAlive + " for 35 damage.";
		}
		if (shotDead.length() > 0) {
			shotDead = "Player shot: " + shotDead + " dead.";
		}
		if (shotAlive.length() == 0 && shotDead.length() == 0) {
			return "Player does not shoot any zombies.";
		}
		else if (shotAlive.length() == 0 && shotDead.length() > 0) {
			return shotDead;
		}
		else if (shotAlive.length() > 0 && shotDead.length() == 0) {
			return shotAlive;
		}
		return shotAlive + "\r\n" + shotDead;
	}
	
	@Override
	/**
	 * Returns a description of what the actor did.
	 * 
	 * @param actor The actor that's using the Shotgun.
	 * @return a description of what the actor did.
	 */
	public String menuDescription(Actor player) {
		return player.toString() + " uses Shotgun";
	}

}
