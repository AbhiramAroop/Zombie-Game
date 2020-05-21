package game;

import java.util.ArrayList;
import java.util.List;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import game.Food;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class eatAction extends Action{
	
	protected Item food;

	public eatAction(Food food) {
		
		this.food = food;
		
		}
	
	@Override
	public String execute(Actor actor, GameMap map) {	
		
		if (this.food != null) {
			actor.heal(10);
			actor.removeItemFromInventory(this.food);
			
		}
		
		return "Food replenished some health!";
	}

	@Override
	public String menuDescription(Actor actor) {
		return actor + "eat food";
	}


}
