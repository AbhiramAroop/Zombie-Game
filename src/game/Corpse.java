package game;

import java.util.Random;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Exit;
import edu.monash.fit2099.engine.Location;

public class Corpse extends PortableItem {
	private Random successRate = new Random();
	private Integer toZombieCounter = new Integer(0);

	public Corpse(String name) {
		super(name, '%');
	}
	
	private void addZombie(Location currentLocation) {
		
		
		for (Exit posDrop : currentLocation.getExits()) {
			if (!posDrop.getDestination().containsAnActor()) {
				posDrop.getDestination().addActor(new Zombie(this.toString()));
				break;
			}
		}
		System.out.println(this.toString() + " comes back fromt the dead!");
		currentLocation.removeItem(this);
	}
	
	
	@Override
	public void tick(Location currentLocation, Actor actor) {
		if (toZombieCounter < 10) {
			toZombieCounter += 1;
		}
		
		if ((toZombieCounter > 4 && toZombieCounter < 11 && successRate.nextBoolean()) ||
				(toZombieCounter.equals(10))) {
			actor.removeItemFromInventory(this);
			this.addZombie(currentLocation);
		}
	}
	
	@Override
	public void tick(Location currentLocation) {
		if (toZombieCounter < 10) {
			toZombieCounter += 1;
		}
		
		if ((toZombieCounter > 4 && toZombieCounter < 11 && successRate.nextBoolean()) ||
				(toZombieCounter.equals(10))) {
			
			if (currentLocation.containsAnActor()) {
				this.addZombie(currentLocation);
			}
			
			else {
				currentLocation.addActor(new Zombie(this.toString()));
				System.out.println(this.toString() + " comes back fromt the dead!");
				currentLocation.removeItem(this);
			}
		
		}
	}

}
