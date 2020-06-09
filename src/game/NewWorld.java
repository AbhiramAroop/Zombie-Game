package game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.World;

/**
 * An extension of World, that adds additional features such as a Boss.
 * 
 * @author Abhiram Aroop
 *
 */
public class NewWorld extends World{
	/**
	 * A constructor method
	 * @param display the world that will be displayed
	 */
	public NewWorld(Display display) {
		super(display);
	}

	private MamboMarie mamboMarie = new MamboMarie();
	private int humanCounter;
	private int hostileCounter;
	private String endMessage = "Game Over!";	
	private int MamboTick = 0;
	private int DespawnTimer = 0;
	
	@Override
	/**
	 * Returns true if the game is still running.
	 *
	 * The game is considered to still be running if the player is still around.
	 * The player will get removed from the game if:
	 * All humans other than the player or the player is dead
	 * If all Zombies and Mambo Marie are killed
	 * If the player performs quitAction
	 *
	 * @return true if the player is still on the map.
	 */
	protected boolean stillRunning() {
		
		this.humanCounter = 0;
		this.hostileCounter = 0;
		
		
		if (lastActionMap.get(player) instanceof quitAction) actorLocations.remove(player);
		
		for (Actor key : lastActionMap.keySet()) {
			if ((key instanceof Human) && !(key instanceof Player) && (actorLocations.contains(key))) {
				this.humanCounter ++;
			}
		}
		
		if (this.humanCounter == 0) {
			actorLocations.remove(player);
			this.endMessage = "player loses";
		}
		
		for (Actor key : lastActionMap.keySet()) {
			if (((key instanceof Zombie) || (key instanceof MamboMarie)) && (actorLocations.contains(key))) {
				this.hostileCounter ++;
			}
		}	
		
		if (this.hostileCounter == 0) {
			actorLocations.remove(player);
			this.endMessage = "player wins";
		}
		
		int tickNumber = 0; 
		for (@SuppressWarnings("unused") Actor actor : actorLocations) {
			tickNumber++;
		}
		
		if ((this.MamboTick == tickNumber)) {
			if (!(actorLocations.contains(mamboMarie)) && (mamboMarie.isConscious())) {
				
				Random spawnMamboMarie = new Random();
				
				if (spawnMamboMarie.nextInt(19) == 0) {
					
					Location location = this.getMamboLocation(actorLocations.locationOf(player).map());
					
					gameMaps.get(0).addActor(mamboMarie, location);;
					System.out.println("Mambo Marie has spawned");
					
				}			
				
			}
			
			else if (!(mamboMarie.isConscious()) && actorLocations.contains(mamboMarie)) {
				actorLocations.remove(mamboMarie);
				}
			
			if ((mamboMarie.isConscious() && actorLocations.contains(mamboMarie))) {
				this.DespawnTimer++;
			}
			
			if (this.DespawnTimer == 30) {
				actorLocations.remove(mamboMarie);
				this.DespawnTimer = 0;
				System.out.println("Mambo Marie has despawned!");
			}
			
			
			
			this.MamboTick = 0;
			
		}
		else this.MamboTick++;
	
			
		return super.stillRunning();
	}
	
	@Override
	/**
	 * The appropriate message to end the game
	 */
	protected String endGameMessage() {
		
		return this.endMessage;

	}
	/**
	 * Randomised the spawn location for Mambo Marie
	 * 
	 * @param map the GameMap she should spawn on
	 * @return the Location of the given GameMap she should spawn at
	 */
	private Location getMamboLocation(GameMap map) {
		
		Random xVal = new Random();
		Random yVal = new Random();
		
		int xRange = map.getXRange().max();
		int yRange = map.getYRange().max();
		
		int x = xVal.nextInt(xRange);
		int y = yVal.nextInt(yRange); 
		
		if (map.at(x, y).getGround() instanceof Fence) {
			return getMamboLocation(map);
		}
		else return map.at(x, y);		
	}

	/**
	 * Gives an Actor its turn.
	 *
	 * The Actions an Actor can take include:
	 * <ul>
	 * <li>those conferred by items it is carrying</li>
	 * <li>movement actions for the current location and terrain</li>
	 * <li>actions that can be done to Actors in adjacent squares</li>
	 * <li>actions that can be done using items in the current location</li>
	 * <li>skipping a turn</li>
	 * </ul>
	 * 
	 * It also teleport player to next Map if player is standing on a Vehicle
	 * @param actor the Actor whose turn it is.
	 */
	public void processActorTurn(Actor actor) {
		
		
		if ((actorLocations.locationOf(player).map() == gameMaps.get(0)) && (actorLocations.locationOf(player).getGround() instanceof Vehicle)) {
						
			teleportPlayer(gameMaps.get(1).at(2, 10), gameMaps.get(0), gameMaps.get(1));
			System.out.println("player has entered Town!");
			
		}
		
		else if ((actorLocations.locationOf(player).map() == gameMaps.get(1)) && (actorLocations.locationOf(player).getGround() instanceof Vehicle)) {
			
			teleportPlayer(gameMaps.get(0).at(75, 1), gameMaps.get(1), gameMaps.get(0));
			System.out.println("player has left Town!");
		}
		
		else super.processActorTurn(actor);
		
		
		
	}
	
	/**
	 * Changes Map that player is in based on their current GameMap and Location
	 * 
	 * @param location The location of Vehicle in the GameMap to travel
	 * @param mapOut The Current GameMap
	 * @param mapIn The GameMap player should go to
	 */
		
	public void teleportPlayer(Location location, GameMap mapOut, GameMap mapIn) {
		
		List<Exit> exits = new ArrayList<Exit>(location.getExits());
		Collections.shuffle(exits);
		
		for (Exit e: exits) {
			if ((e.getDestination().canActorEnter(player))) {
					
				mapOut.removeActor(player);
				mapIn.addActor(player, e.getDestination());
				
				break;
				
			}
		}
		
		int x = 1;
		int y = 1;
		
		while (mapOut.contains(player)) {
			
			if (mapIn.at(x, y).canActorEnter(player)) {
				
				mapOut.removeActor(player);
				mapIn.addActor(player, mapIn.at(x, y));
				
			}
			else if (x < mapIn.getXRange().max()) {
				x++;
			}
			else {
				y++;
				x = 0;
			}
		
		}
		
	}
	
	

	

}
