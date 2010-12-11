package elements.powerups;

import elements.Bomb;
import elements.Player;

public class SpeedUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
		p.setSpeed(3);
	}

	@Override
	public void takeEffect(Bomb b) {
	}

}
