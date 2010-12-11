package mybomberman.elements.powerups;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;


public class PowerUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
		p.setExplodeRange(p.getExplodeRange() + 1);
	}

	@Override
	public void takeEffect(Bomb b) {
	}

}
