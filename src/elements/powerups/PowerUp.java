package elements.powerups;

import elements.Bomb;
import elements.Player;

public class PowerUp extends AbstractPowerUp {

	@Override
	public void takeEffectOnPlayer(Player p) {
	}

	@Override
	public void takeEffectOnBomb(Bomb b) {
		b.setExplodeRange(3);
	}

}
