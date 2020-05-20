package game;

import edu.monash.fit2099.engine.Ground;
import edu.monash.fit2099.engine.Location;

public class Crop extends Ground{

	public status state = status.UNRIPE;
	public int age = 0;
	
	public Crop() {
		super('c');
		// TODO Auto-generated constructor stub
	}
	
	public boolean isGrown() {
		
		if (state == status.RIPE) return true;
		
		return false;
		
	}
	
	public void tick(Location location) {
		super.tick(location);

		age++;
		
		if (this.age == 20) {
			state = status.RIPE;
			this.displayChar = 'C';
		}
		
		
	}
	
	public void addAge() {
		if (this.age < 20) this.age = Math.min(20,this.age + 10);
		
	}
	
	
	
	
	
	
	enum status {
		UNRIPE, RIPE
	}	
	
}
