package elements.powerups;

import elements.Bomb;
import elements.Player;

public abstract class AbstractPowerUp {

	public AbstractPowerUp() {
	}

	public abstract void takeEffectOnPlayer(Player p);

	public abstract void takeEffectOnBomb(Bomb b);

}
