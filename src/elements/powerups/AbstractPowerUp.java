package elements.powerups;

import elements.Bomb;
import elements.Player;

public abstract class AbstractPowerUp {

	public AbstractPowerUp() {
	}

	public abstract void takeEffect(Player p);

	public abstract void takeEffect(Bomb b);

}
