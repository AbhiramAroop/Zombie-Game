package game;

import java.util.Random;

import edu.monash.fit2099.engine.Action;
import edu.monash.fit2099.engine.Actor;
import edu.monash.fit2099.engine.GameMap;
import edu.monash.fit2099.engine.Item;

public class setItemAction extends Action{
	protected Item item;
	private Random successRate = new Random();
	public setItemAction(Item item) {
		this.item = item;
	}
		
	
	
	public void setItemRandom(GameMap map,Actor actor) {
		
		int xCoorDrop = map.locationOf(actor).x();
		int yCoorDrop = map.locationOf(actor).y();
		
		int[] posMove = {-1,0,1};
		
		xCoorDrop += posMove[successRate.nextInt(3)];
		yCoorDrop += posMove[successRate.nextInt(3)];
		
		map.at(xCoorDrop, yCoorDrop).addItem(item);
		
		
	}
	

	@Override
	public String execute(Actor actor, GameMap map) {
		
		if (actor instanceof Zombie) {
			setItemRandom(map, actor);
		}
		
		
		return menuDescription(actor);
	}
	
	@Override
	public String menuDescription(Actor actor) {
		
		if (actor instanceof Zombie) {
			return actor.toString() + " loses its " + item;
		}
		else {
			return actor.toString() + " sets the " + item;
		}
	}
}
