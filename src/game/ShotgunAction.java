package game;

import java.util.Random;
import java.util.Scanner;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;

public class ShotgunAction extends Action{
	
	private String[] ShootDir = {"N: North", "E: East", "S: South", "W: West",
			"NE: North-East","NW: North-West", "SE: South-East", "SW: South-West"};
	private Scanner UserInput = new Scanner(System.in);
	private Random successRate = new Random();
	private String Targets = "\r\n";
	
	/**
	 * Allows the actor to shoot either North or South with the shotgun.
	 * 
	 * @param x The x coordinate of the actor's current position.
	 * @param y The y coordinate of the actor's current position.
	 * @param map The Game map.
	 * @param dir The direction the actor is shooting (i.e. N or S).
	 */
	private void shootNtoS(Integer x, Integer y, GameMap map, String dir) {
		int z = 1;
		
		if (dir.equals("S")) {
			z = -1;
		}
		for (int bound = 1 ; bound <= 3 ; bound ++) {
			for (int i = x - bound ; i <= x + bound ; i++) {
				if (inRange(i, y+z*bound, map)) {
					if (map.getActorAt(map.at(i, y + z*bound)) instanceof Zombie) {
						if (successRate.nextInt(4) != 0) {
							map.getActorAt(map.at(x, y)).hurt(30);
							Targets += map.getActorAt(map.at(x, y)).toString() + "\r\n" ;
							}
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
	private void shootEtoW(Integer x, Integer y, GameMap map, String dir) {
		int z = 1;
		
		if (dir.equals("W")) {
			z = -1;
		}
		for (int bound = 1 ; bound <= 3 ; bound ++) {
			for (int i = y - bound ; i <= y + bound ; i++) {
				if (inRange(x + z*bound, i, map)) {
					if (map.getActorAt(map.at(x + z*bound, i)) instanceof Zombie) {
						if (successRate.nextInt(4) != 0) {
							map.getActorAt(map.at(x, y)).hurt(30);	
							Targets += map.getActorAt(map.at(x, y)).toString() + "\r\n" ;
							}
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
	private void shootNWtoSE(Integer x, Integer y, GameMap map, String dir) {
		int z = 0;
		
		if (dir.equals("SE")) {
			z = 3;
		}
		
		for (int bound1 = 0 ; bound1 <= 3 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 >= -3 ; bound2 -= 1) {
				if (inRange(x - z + bound1, y + z + bound2, map)) {
					if (map.getActorAt(map.at(x - z + bound1, y + z + bound2)) instanceof Zombie) {
						if (successRate.nextInt(4) != 0) {
							map.getActorAt(map.at(x, y)).hurt(30);	
							Targets += map.getActorAt(map.at(x, y)).toString() + "\r\n" ;
							}
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
	private void shootNEtoSW(Integer x, Integer y, GameMap map, String dir) {
		int z = 0;
		
		if (dir.equals("SW")) {
			z = 3;
		}
		
		for (int bound1 = 0 ; bound1 <= 3 ; bound1 ++) {
			for (int bound2 = 0 ; bound2 <= 3 ; bound2 ++) {
				if (inRange(x - z + bound1, y - z + bound2, map)) {
					if (map.getActorAt(map.at(x - z + bound1, y - z + bound2)) instanceof Zombie) {
						if (successRate.nextInt(4) != 0) {
							map.getActorAt(map.at(x, y)).hurt(30);	
							Targets += map.getActorAt(map.at(x, y)).toString() + "\r\n" ;
							}
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
	public String execute(Actor actor, GameMap map) {
		System.out.println("Shoot direction options: ");
		for (String direction : this.ShootDir) {
			System.out.println(direction);
		}	

		Location currentPos = map.locationOf(actor);
		
		boolean reducedAmmo = false;
		boolean validMove = false;
		while (!validMove) {
			System.out.println("Select shooting direction: ");
			String shoot = UserInput.nextLine();
			if (shoot.equals("N")) {
				shootNtoS(currentPos.x(), currentPos.y(), map, "N");
				validMove = true;
			}
			else if (shoot.equals("S")) {
				shootNtoS(currentPos.x(), currentPos.y(), map, "S");
				validMove = true;
			}
			else if (shoot.equals("E")) {
				shootEtoW(currentPos.x(), currentPos.y(), map, "E");
				validMove = true;
			}
			else if (shoot.equals("W")) {
				shootEtoW(currentPos.x(), currentPos.y(), map, "W");
				validMove = true;
			}
			else if (shoot.equals("NW")) {
				shootNWtoSE(currentPos.x(), currentPos.y(), map, "NW");
				validMove = true;
			}
			else if (shoot.equals("SE")) {
				shootNWtoSE(currentPos.x(), currentPos.y(), map, "SE");
				validMove = true;
			}
			else if (shoot.equals("NE")) {
				shootNEtoSW(currentPos.x(), currentPos.y(), map, "NE");
				validMove = true;
			}
			else if (shoot.equals("NW")) {
				shootNEtoSW(currentPos.x(), currentPos.y(), map, "SW");
				validMove = true;
			}
			else {
				System.out.println("Invalid move. Choose a valid move: ");
			}
			if (!(reducedAmmo)) {
				reduceAmmo(actor);
				reducedAmmo = true;
			}
		}
		return menuDescription(actor);
	}
	
	private void reduceAmmo(Actor actor) {
		for (Item item : actor.getInventory()) {
			((Shotgun) item).reduceAmmo();
			break;
		}
	}
	
	@Override
	/**
	 * Returns a description of what the actor did.
	 * 
	 * @param actor The actor that's using the Shotgun.
	 * @return a description of what the actor did.
	 */
	public String menuDescription(Actor actor) {
		return actor.toString() + " shoots " + Targets;
	}

}
