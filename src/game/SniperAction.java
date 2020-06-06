package game;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class SniperAction extends Action{
	
	private ArrayList<Actor> Targets = new ArrayList<>();
	private int concentration;
	private Scanner UserInput = new Scanner(System.in);
	private Random successRate = new Random();
	private String UserChoice;
	private String TargetChoice;
	private int TargetIdx = -1;
	private String TargetLoc;
	
	/**
	 * Initializes a Sniper action by finding all the nearby Zombies from the current actor's 
	 * position. The Sniper action is only created if there are Zombies within the actor's 
	 * range (i.e. a 4x4 square with its center being the actor's current position).
	 * 
	 * @param actor The actor that's using the Sniper (i.e. Player)
	 * @param map The Game map itself
	 */
	SniperAction(Actor actor, GameMap map) {
		for (Item item : actor.getInventory()) {
			this.concentration = ((SniperRifle) item).getCon();
			break;
		}
		Location currentPos = map.locationOf(actor);
		int x = currentPos.x();
		int y = currentPos.y();
		
		
		for (int bound1 = 0 ; bound1 <= 8 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 <= 8 ; bound2 ++) {
				if (inRange(x - 4 + bound1, y + 4 + bound2, map)) {
					if (!(map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)) instanceof Human)) {
						Targets.add(map.getActorAt(map.at(x - 4 + bound1, y - 4 + bound2)));
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
	public String execute(Actor actor, GameMap map) {
		System.out.println("Concentration level: " + concentration + "/2");
		if (TargetIdx == -1) {
			TargetChoice = "unselected";
		}
		System.out.println("Current target: " + TargetChoice + TargetLoc);
		
		while (true) {
			System.out.println("Press 0 to select/change target");
			if (canAim()) {
				System.out.println("Press 1 to aim");
			}
			if (canShoot(actor)) {
				System.out.println("Press 2 to shoot");
			}

			UserChoice = UserInput.nextLine();
			if (UserChoice.equals("0")) {
				concentration = 0;
				lockOn(actor, map);
				System.out.println("Target is now: " + TargetChoice + TargetLoc);
				if (canAim()) {
					System.out.println("Press 1 to aim");
					for (Item item : actor.getInventory()) {
						((SniperRifle) item).setCon(concentration + 1);
						break;
					}
				}
				if (canShoot(actor))  {
					System.out.println("Press 2 to shoot");
					for (Item item : actor.getInventory()) {
						((SniperRifle) item).reduceAmmo();
						break;
					}
				}
			}
			else if (UserInput.nextLine().equals("1") && canAim()) {
				aim();
				break;
			}
			else if (UserChoice.equals("2") && canShoot(actor)) {
				shoot(TargetIdx, map);
				
				break;
			}
			else {
				System.out.println("Invalid move. Choose a valid move: ");
			}
		}
		return menuDescription(actor);
	}
	
	/**
	 * Allows the actor to choose which Zombie it would like to shoot.
	 * 
	 * @param actor The actor shooting
	 * @param map The Game map
	 */
	private void lockOn(Actor actor, GameMap map) {
		System.out.println("Choose a target:");
		for (int i=0; i<=Targets.size(); i++) {
			Actor currTarget = Targets.get(i);
			int xCo = map.locationOf(currTarget).x();
			int yCo = map.locationOf(currTarget).y();
			System.out.println(i + ": " + currTarget.toString() + coordConvert(xCo, yCo));
		}
		
		boolean state = false;
		while (!state) {
			TargetIdx = UserInput.nextInt();
			if (TargetIdx <= Targets.size() && TargetIdx >= 0) {
				state = true;
			}
			System.out.println("Invalid target. Choose a valid target: ");
		}
		
		TargetChoice = Targets.get(TargetIdx).toString();
		int xLocTar = map.locationOf(Targets.get(TargetIdx)).x();
		int yLocTar = map.locationOf(Targets.get(TargetIdx)).y();
		int xLocAct = map.locationOf(actor).x();
		int yLocAct = map.locationOf(actor).y();
		
 		TargetLoc = coordConvert(xLocTar - xLocAct, yLocTar - yLocAct);
	}
	
	/**
	 * Shoots a given target. 
	 * - 0/2 concentration: 75% hit rate, 30 dmg
	 * - 1/2 concentration: 90% hit rate, 60 dmg
	 * - 2/2 concentration: 100% hit rate, instant kill
	 * 
	 * @param target The target being shoot
	 */
	private void shoot(int target, GameMap map) {
		if (concentration == 0) {
			if (successRate.nextInt(4) != 0) {
				Targets.get(target).hurt(30);
			}
		}
		else if (concentration == 1 ) {
			if (successRate.nextInt(10) != 0) {
				Targets.get(target).hurt(60);
			}
		}
		else {
			map.removeActor(Targets.get(target));
		}
	}
	
	/**
	 * Increase the actor's concentration level by 1. 
	 */
	private void aim() {
		concentration = Math.min(concentration + 1, 2);
	}
	
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
	
	private boolean canAim() {
		return concentration < 2;
	}
	
	private boolean canShoot(Actor actor) {
		for (Item item : actor.getInventory()) {
			if (((SniperRifle) item).hasAmmo() && TargetIdx != -1) {
				return true;
			}
		}
		return false;
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
			xStr = x + " units left ";
		}
		else {
			xStr = x + " units right ";
		}
		if (y < 0) {
			yStr = y + " units down ";
		}
		else {
			yStr = y + " units up ";
		}
		return " at " + xStr + ", " + yStr + ".";
	}
	
	@Override
	/**
	 * Returns a description of what the actor did (either shoot or aim).
	 * 
	 * @param actor The actor that's using the SniperRifle
	 * @return a description of what the actor did (either shoot or aim).
	 */
	public String menuDescription(Actor actor) {
		if (UserChoice.equals("1")) {
			return actor.toString() + " aims.";
		}
		return actor.toString() + "shoots " + TargetChoice + " at " + TargetLoc + ".";
	}

}
