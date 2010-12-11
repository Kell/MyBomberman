package elements.powerups;

import elements.Bomb;
import elements.Player;

public class BombUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
		p.addBombCount();
	}

	@Override
	public void takeEffect(Bomb b) {
	}

}
