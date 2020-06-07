package game;

/**
 * A ranged weapon that shoots up to 3 spaces ahead in a 90 degree cone.
 * 
 * @author Immanuel Andrew Christabel
 */
public class Shotgun extends RangedWeapon {
	
	/**
	 * Initializes a Shotgun weapon.
	 */
	public Shotgun() {
		super("Shotgun", 'S', 20);
	}
	
	/**
	 * Checks if the shotgun can be used this turn.
	 * 
	 * @return
	 */
	public boolean canExecute() {
		return super.hasAmmo();
	}	
}
