package elements;

import org.newdawn.slick.Image;

public abstract class PowerUp {

	
	public PowerUp(Image img) {}
	
	public abstract void takeEffect(Player p);
}
