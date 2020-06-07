package game;

/**
 * An abstract class used to create weapons that can be used at a distance.
 * 
 * @author Immanuel Andrew Christabel
 */
public abstract class RangedWeapon extends PortableItem {
	protected int ammunition;
	protected int fullCap;
	
	/**
	 * Creates a ranged weapon.
	 * 
	 * @param name Name of ranged weapon
	 * @param displayChar Character display of ranged weapon
	 * @param ammo The number of bullets the ranged weapon starts off with
	 */
	public RangedWeapon(String name, char displayChar, int ammo) {
		super(name, displayChar);
		this.ammunition = ammo;
		this.fullCap = ammo;
	}
	
	/**
	 * Checks if the ranged weapon has ammo.
	 * 
	 * @return Boolean describing whether the ranged weapon has ammo or not.
	 */
	protected boolean hasAmmo() {
		return ammunition > 0;
	}
	
	/**
	 * Reduces the ranged weapon's ammunition by 1.
	 */
	protected void reduceAmmo() {
		ammunition -= 1;
		if (ammunition < 0) {
			ammunition = 0;
		}
	}
	
	/**
	 * Checks how many bullets the ranged weapon currently has.
	 * 
	 * @return An integer describing how many bullets the ranged weapon has.
	 */
	public int getAmmo() {
		return new Integer(this.ammunition);
	}
	
	/**
	 * Resets the ranged weapon's ammunition to full capacity.
	 */
	public void reload() {
		ammunition = fullCap;
	}
}
