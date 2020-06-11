package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

/**
 * An action that allows the player to use the sniper rifle.
 * 
 * @author Immanuel Andrew Christabel
 *
 */
public class SniperAction extends Action{
	
	private ArrayList<Actor> targets = new ArrayList<>();
	private int concentration;
	private int ammo;
	private Scanner userInput = new Scanner(System.in);
	private Random successRate = new Random();
	private String userChoice;	
	private String targetName = "unselected";
	private int targetIdx = -1;
	private String targetPos;
	
	/**
	 * Initializes a Sniper action by finding all the nearby Zombies from the current actor's 
	 * position. The Sniper action is only created if there are Zombies within the actor's 
	 * range (i.e. a 4x4 square with its center being the actor's current position).
	 * 
	 * @param player The actor that's using the Sniper (i.e. Player)
	 * @param map The Game map itself
	 */
	SniperAction(Actor player, GameMap map) {
		for (Item item : player.getInventory()) {
			if (item instanceof SniperRifle) {
				this.concentration = ((SniperRifle) item).getCon();
				this.ammo = ((SniperRifle) item).getAmmo();
				break;
			}
		}
		Location currentPos = map.locationOf(player);
		int x = currentPos.x();
		int y = currentPos.y();
		
		
		for (int bound1 = 0 ; bound1 <= 8 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 <= 8 ; bound2 ++) {
				if (inRange(x - 4 + bound1, y - 4 + bound2, map)) {
					Actor target = map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2));
					if (target instanceof Zombie || target instanceof MamboMarie) {
						targets.add(target);
					}
				}
			}
		}	
	}
	
	
	@Override
	/**
	 * Gives the actor the option to either switch targets, aim or shoot (based on where it's
	 * standing)
	 * 
	 * @param actor The actor using the Sniper Rifle.
	 * @param map The Game map.
	 * @return a description of what the actor did.
	 */
	public String execute(Actor player, GameMap map) {

		while (true) {
			boolean lockedOn = false;
			System.out.println("");
			System.out.println("Concentration level: " + concentration + "/2");
			System.out.println("Ammunition: " + ammo);
			System.out.println("Current target: " + targetName + " at " + targetPos);
			
			if (canLockOn()) {
				System.out.println("Press 0 to select/change target");
			}
			if (canAim()) {
				System.out.println("Press 1 to aim");
			}
			if (canShoot(player)) {
				System.out.println("Press 2 to shoot");
			}
			userChoice = userInput.next();
			
			if (userChoice.equals("0") && canLockOn()) {
				lockedOn = true;
				lockOn(player, map);
				System.out.println("Target is now: " + targetName + " at " + targetPos);
			}
			else if (userChoice.equals("1") && canAim()) {
				for (Item item : player.getInventory()) {
					if (item instanceof SniperRifle) {
						((SniperRifle) item).setCon(concentration + 1);
						break;
					}
				}
				return "Player aims";
			}
			else if (userChoice.equals("2") && canShoot(player)) {
				
				for (Item item : player.getInventory()) {
					if (item instanceof SniperRifle) {
						((SniperRifle) item).reduceAmmo();
						((SniperRifle) item).setCon(0);
						break;
					}
				}
				return shoot(targetIdx, map);
			}
			if (!lockedOn) {
				System.out.println("Invalid move. Choose a valid move: ");
			}
		}
	}
	
	/**
	 * Allows the actor to choose which Zombie it would like to shoot.
	 * 
	 * @param player The actor shooting
	 * @param map The Game map
	 */
	private void lockOn(Actor player, GameMap map) {

		System.out.println("Choose a target:");
		int xLocAct = map.locationOf(player).x();
		int yLocAct = map.locationOf(player).y();
		
		for (int i=0; i<targets.size(); i++) {
			Actor currTarget = targets.get(i);
			int xCo = map.locationOf(currTarget).x();
			int yCo = map.locationOf(currTarget).y();
			System.out.println("Press " + i + ": " + currTarget.toString() + coordConvert(xCo - xLocAct, yCo - yLocAct));
		}
		

		while (true) {
			targetIdx = userInput.nextInt();
			if (targetIdx < targets.size() && targetIdx >= 0) {
				break;
			}
			System.out.println("Invalid target. Choose a valid target: ");
		}
		
		int xLocTar = map.locationOf(targets.get(targetIdx)).x();
		int yLocTar = map.locationOf(targets.get(targetIdx)).y();
		
		targetName = targets.get(targetIdx).toString();
 		targetPos = coordConvert(xLocTar - xLocAct, yLocTar - yLocAct);
 		
	}
	
	/**
	 * Shoots a given target. 
	 * - 0/2 concentration: 75% hit rate, 30 dmg
	 * - 1/2 concentration: 90% hit rate, 60 dmg
	 * - 2/2 concentration: 100% hit rate, instant kill
	 * 
	 * @param targetIdx The target being shoot
	 */
	private String shoot(int targetIdx, GameMap map) {
		Actor target = targets.get(targetIdx);
		if (concentration == 0) {
			if (successRate.nextInt(4) != 0) {
				target.hurt(40);
				if (target.isConscious()) {
					return "Player snipes " + targetName + " for 40 damage";
				}
				else {
					map.removeActor(target);
					return "Player snipes " + targetName + " dead";
				}
			}
			
		}
		else if (this.concentration == 1) {
			if (successRate.nextInt(10) != 0) {
				targets.get(targetIdx).hurt(80);
				if (target.isConscious()) {
					return "Player snipes " + targetName + " for 80 damage";
				}
				else {
					map.removeActor(target);
					return "Player snipes " + targetName + " dead";
				}
			}
		}
		else {
			map.removeActor(target);
			return "Player snipes " + targetName + " dead";
		}
		return null;
	}
	
	/**
	 * Increase the actor's concentration level by 1. 
	 */
	
	/**
	 * Checks if the current coordinates are within the range of the Game map
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @param map The Game map
	 * @return true if the coordinate are within the Game map range. False otherwise.
	 */
	private boolean inRange(int x, int y, GameMap map) {
		return (map.getXRange().min() <= x && 
				x <= map.getXRange().max() && 
				map.getYRange().min() <= y && 
				y<= map.getYRange().max()); 
	}
	
	/**
	 * Checks it an actor can aim.
	 * 
	 * @return boolean describing the actor's ability to aim
	 */
	private boolean canAim() {
		return concentration < 2;
	}
	
	/**
	 * Checks it an actor can shoot.
	 * 
	 * @param player The actor shooting
	 * @return Boolean describing the actor's ability to shoot
	 */
	private boolean canShoot(Actor player) {
		for (Item item : player.getInventory()) {
			if (item instanceof SniperRifle) {
				return (((SniperRifle) item).hasAmmo() && targetIdx != -1);
			}
		}
		return false;
	}
	
	/**
	 * Checks if an actor can choose a target.
	 * 
	 * @return Boolean describing the actor's ability to choose a target.
	 */
	private boolean canLockOn() {
		return this.targets.size() != 0;
	}
	
	/**
	 * Converts a given coordinates into plain text. 
	 * Eg. (3, 2) = 3 units right, 2 units up.
	 * 
	 * @param x The x coordinate
	 * @param y The y coordinate
	 * @return A string representation of the given coordinates.
	 */
	private String coordConvert(int x, int y) {
		String xStr = "";
		String yStr = "";
		if (x < 0) {
			xStr = Math.abs(x) + " units left ";
		}
		else {
			xStr = x + " units right ";
		}
		if (y > 0) {
			yStr = y + " units down ";
		}
		else {
			yStr = Math.abs(y) + " units up ";
		}
		return xStr + ", " + yStr;
	}
	
	@Override
	/**
	 * Returns a description of what the actor did (either shoot or aim).
	 * 
	 * @param actor The actor that's using the SniperRifle
	 * @return a description of what the actor did (either shoot or aim).
	 */
	public String menuDescription(Actor player) {
		return player.toString() + " uses Sniper Rifle";
	}

}
