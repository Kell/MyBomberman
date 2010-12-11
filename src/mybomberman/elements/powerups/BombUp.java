package mybomberman.elements.powerups;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;


public class BombUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
		p.addBombCount();
	}

	@Override
	public void takeEffect(Bomb b) {
	}

}
