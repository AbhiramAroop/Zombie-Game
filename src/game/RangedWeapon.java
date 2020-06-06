package game;

public abstract class RangedWeapon extends PortableItem {
	protected int ammunition;
	protected int fullCap;
	
	public RangedWeapon(String name, char displayChar, int ammo) {
		super(name, displayChar);
		this.ammunition = ammo;
		this.fullCap = ammo;
	}
	
	public boolean hasAmmo() {
		return ammunition > 0;
	}
	
	protected void reduceAmmo() {
		ammunition = Math.max(ammunition - 1, 0);
	}
	
	public void addAmmo() {
		ammunition = fullCap;
	}
}
