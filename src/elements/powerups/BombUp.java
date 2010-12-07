package elements.powerups;

import elements.Bomb;
import elements.Player;

public class BombUp extends AbstractPowerUp {

	@Override
	public void takeEffectOnPlayer(Player p) {
		p.addBombCount();
	}

	@Override
	public void takeEffectOnBomb(Bomb b) {
	}

}
