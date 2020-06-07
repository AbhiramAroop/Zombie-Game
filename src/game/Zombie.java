package game;

import edu.monash.fit2099.engine.Action;

import edu.monash.fit2099.engine.Actions;
import edu.monash.fit2099.engine.Display;
import edu.monash.fit2099.engine.DoNothingAction;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.IntrinsicWeapon;
import edu.monash.fit2099.engine.Item;
import edu.monash.fit2099.engine.MoveActorAction;
import edu.monash.fit2099.engine.PickUpItemAction;
import edu.monash.fit2099.engine.WeaponItem;
import java.util.ArrayList;
import java.util.Random;

/**
 * A Zombie.
 * 
 * This Zombie is pretty boring.  It needs to be made more interesting.
 * 
 * @author ram
 * @author Immanuel Andrew Christabel
 *
 */
public class Zombie extends ZombieActor {
	/**
	 * RNG variable
	 */ 
	private Random successRate = new Random();
	/**
	 * The number of arms the Zombie has
	 */ 
	private int ArmCount = 2;
	/**
	 * The number of legs the Zombie has
	 */ 
	private int LegCount = 2;
	/**
	 * A boolean that checks if a Zombie has lost a limb or not 
	 */ 
	private boolean lostLimb = false;
	/**
	 * A boolean that checks if a Zombie moved last turn 
	 */
	private boolean moved = true;
	/**
	 * An array list that holds all the available limbs the Zombie has
	 */
	private ArrayList<WeaponItem> availableLimbs = new ArrayList<WeaponItem>(4);
	/**
	 * Holds all behaviours the Zombie has
	 */
	private Behaviour[] behaviours = {
			new AttackBehaviour(ZombieCapability.ALIVE),
			new HuntBehaviour(Human.class, 10),
			new WanderBehaviour()
	};
	
	/**
	 * Constructor for Zombie class. It starts of with 2 Zombie arms and legs.
	 * 
	 * @param name the name of the Zombie
	 */ 
	public Zombie(String name) {
		super(name, 'Z', 100, ZombieCapability.UNDEAD);
		availableLimbs.add(new zombieArm());
		availableLimbs.add(new zombieArm());
		availableLimbs.add(new zombieLeg());
		availableLimbs.add(new zombieLeg());
	}
	
	/**
	 * @param map the game map
	 * 
	 * A method that drops a Zombie limb. If it has one arm, it has a 50% chance of dropping
	 * a weapon. If it has no arms, it's guaranteed to drop a weapon if it has one.
	 */ 
	private void loseLimb(GameMap map) {
		int availableLimbsSize = this.availableLimbs.size();
		if (availableLimbsSize > 0) {
			int lostLimbIdx = successRate.nextInt(availableLimbsSize);
			WeaponItem lostLimb = this.availableLimbs.get(lostLimbIdx);
			this.availableLimbs.remove(lostLimbIdx);
			
			if (lostLimb instanceof zombieLeg) {
				this.LegCount -= 1;
			}
			
			else {
				this.ArmCount -= 1;
				if ((successRate.nextBoolean() && this.ArmCount == 1) || this.ArmCount == 0) {
					for (Item item : this.inventory) {
						if (item instanceof WeaponItem) {
							this.removeItemFromInventory(item);
							break;
						}
					}
				}
			}
			setItemAction setLostLimb = new setItemAction(lostLimb);
			System.out.println(setLostLimb.execute(this, map));
		}
	}
	
	@Override
	/**
	 * If the Zombie is damaged, reduce its hit points by the designated amount. There's also a
	 * 25% chance that the Zombie will drop a limb if it's damaged.
	 * 
	 * @param points the number of life points it loses
	 */
	public void hurt(int points) {
		hitPoints -= points;

		if (successRate.nextInt(4) == 0) {
			this.lostLimb = true;
		}
	}
	
	@Override
	/**
	 * 
	 * If the Zombie has 2 arms, then it has a 50% chance of using bite. ELse, it will punch.
	 * If the Zombie has 1 arm it has a 25% chance of punching. Else it will bite.
	 * If the Zombie has no arms, it's guaranteed to bite.
	 * 
	 * @return the weapon the Zombie will use to attack
	 */
	public IntrinsicWeapon getIntrinsicWeapon() {
		
		if (this.ArmCount == 0) {
			return new Bite();
		}
		
		else if (this.ArmCount == 1) {
			if (successRate.nextInt(4) == 0) {
				return new Punch();
			}
			else {
				return new Bite();
			}
		}
		else {
			if (successRate.nextBoolean()) {
				return new Punch();
			}
			else {
				return new Bite();
			}
		}
	}
	
	@Override
	/**
	 * If a Zombie can attack, it will. 	If not, it will chase any human within 10 spaces.  
	 * If no humans are close enough it will wander randomly. 
	 * If the Zombie lost a limb due to damage, it will drop its limb. 
	 * If the Zombie is standing on top of a weapon, it will pick it up.
	 * If the Zombie only has 1 leg, it will only execute MoveActorAction every other turn.
	 * If the Zombie only has no legs, it will not move.
	 * 
	 * @param actions list of possible Actions
	 * @param lastAction previous Action, if it was a multiturn action
	 * @param map the map where the current Zombie is
	 * @param display the Display where the Zombie's utterances will be displayed
	 * 
	 * @return the action the Zombie will execute this turn.
	 */

	public Action playTurn(Actions actions, Action lastAction, GameMap map, Display display) {
		
		if (successRate.nextInt(10) == 0) {
			System.out.println(this.toString() + ": Braaaaains!");
		}
		
		if (lostLimb) {
			this.loseLimb(map);
			lostLimb = !lostLimb;
		}
		
		if (this.ArmCount > 0) {
			for (Item item : map.locationOf(this).getItems()) {
				if (item instanceof WeaponItem) {
					return new PickUpItemAction(item);
				}
			}
		}
		
		for (Behaviour behaviour : behaviours) {
			Action action = behaviour.getAction(this, map);

			if (action != null) {
				if ((LegCount == 0 && !(action instanceof MoveActorAction)) ||
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
