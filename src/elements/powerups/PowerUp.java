package elements.powerups;

import elements.Bomb;
import elements.Player;

public class PowerUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
	}

	@Override
	public void takeEffect(Bomb b) {
		b.setExplodeRange(b.getExplodeRange() + 1);
	}

}
