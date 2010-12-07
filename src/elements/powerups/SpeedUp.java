package elements.powerups;

import elements.Bomb;
import elements.Player;

public class SpeedUp extends AbstractPowerUp {

	@Override
	public void takeEffectOnPlayer(Player p) {
		p.setSpeed(3);
	}

	@Override
	public void takeEffectOnBomb(Bomb b) {
	}

}
