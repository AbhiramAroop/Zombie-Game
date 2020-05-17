package game;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.Location;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.Weapon;
import edu.monash.fit2099.engine.WeaponItem;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 *
 */
public class Zombie extends ZombieActor {
	
	private Random successRate = new Random();
	private int ArmCount = 2;
	private int LegCount = 2;
	private boolean moved = true;
	private ArrayList<WeaponItem> availableLimbs = 
		{new ZombieArm(), new ZombieArm(), new ZombieLeg(), new ZombieLeg()};
	
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};

	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
	}
	
	public void loseLimb(GameMap map) {
		int availableLimbsSize = this.availableLimbs.size();
		if (availableLimbsSize > 0) {
			Integer lostLimbIdx = successRate.nextInt(availableLimbsSize);
			WeaponItem lostLimb = this.availableLimbs.get(lostLimbIdx);
			this.availableLimbs.remove(lostLimbIdx);
			
			if (lostLimb.getClass().getName() == "ZombieArm") {
				this.ArmCount -= 1;
			}
			else {
				this.LegCount -= 1;
			}
			
			if (lostLimb instanceof ZombieArm) {
				if ((successRate.nextBoolean() && this.ArmCount == 2) || this.ArmCount == 1) {
					for (Item item : this.inventory) {
						if (item instanceof WeaponItem) {
							this.removeItemFromInventory(item);
							break;
						}
					}
				}
			}
			setItemAction setLostLimb = new setItemAction(lostLimb);
			setLostLimb.execute(this, map);
		}
	}

	@Override
	public IntrinsicWeapon getIntrinsicWeapon() {
		
		if (this.ArmCount == 0) {
			return new IntrinsicWeapon(11, "bites");
		}
		
		else if (this.ArmCount == 1) {
			if (successRate.nextInt(4) == 0) {
				return new IntrinsicWeapon(10, "punches");
			}
			else {
				return new IntrinsicWeapon(11, "bites");
			}
		}
		else {
			if (successRate.nextBoolean()) {
				return new IntrinsicWeapon(10, "punches");
			}
			else {
				return new IntrinsicWeapon(11, "bites");
			}
		}
	}

	/**
	 * If a Zombie can attack, it will.  If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 */
	@Override
	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (successRate.nextInt(10) == 0) {
			System.out.println("Zombie: Braaaaains!");
		}
		
		for (Item item : map.locationOf(this).getItems()) {
			if (item instanceof Weapon) {
				return new PickUpItemAction(item);
			}
		}
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);
			if (action instanceof AttackAction) {
				if (successRate.nextInt(4) == 0) {
					this.loseLimb(map);
					return action;
				}
			}
			else if (action != null) {
				if ((LegCount == 0 && action.getClass().getName() != "MoveActorAction") ||
						(LegCount == 1 && !moved && action instanceof MoveActorAction) ||
						(LegCount == 2)) {
					if (LegCount == 1) {
						moved = !moved;
					}
					return action;
				}	
			}
		}
		return new DoNothingAction();	
	}
}
