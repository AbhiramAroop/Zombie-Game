package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.GameMap;


public class MamboMarie extends ZombieActor{

	private Behaviour behaviour = new WanderBehaviour();
	private int summoningCounter = 1;
	
	public MamboMarie() {
		super("Mambo Marie", 'M', 100, ZombieCapability.UNDEAD);
	}

	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (this.summoningCounter == 10) {
			this.summoningCounter = 1;
			return new summoningAction();
		}
		
		if (!(lastAction instanceof summoningAction)) {
			this.summoningCounter ++;
		}
		
		
		return behaviour.getAction(this, map);
	}

	

}
