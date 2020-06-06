package game;

public class Shotgun extends RangedWeapon {
	
	/**
	 * Initializes a Shotgun weapon.
	 */
	public Shotgun() {
		super("Shotgun", 'S', 20);

	}
	
	public boolean canExecute() {
		return super.hasAmmo();
	}
}
