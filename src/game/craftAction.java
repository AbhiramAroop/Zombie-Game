package game;

import java.util.HashMap;
import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.WeaponItem;
import edu.monash.fit2099.demo.mars.zombieArm;
import edu.monash.fit2099.demo.mars.zombieLeg;
import edu.monash.fit2099.demo.mars.zombieClub;
import edu.monash.fit2099.demo.mars.zombieMace;

/**
 * Special Action for attacking other Actors.
 */
public class craftAction extends Action {

	private HashMap<WeaponItem,WeaponItem> crafting = new HashMap<WeaponItem,WeaponItem>();

	
	public craftAction() {
		this.crafting.put(new zombieArm(),new zombieClub());
		this.crafting.put(new zombieLeg(),new zombieMace());
		
	}	
	
	//public WeaponItem craftWeapon(Actor actor, WeaponItem weapon) {
		
					
	//}

	@Override
	public String execute(Actor actor, GameMap map) {
		
		//Get full inven
		
		
		return null;
	}

	@Override
	public String menuDescription(Actor actor) {
		// TODO Auto-generated method stub
		return null;
	}
}


	
	