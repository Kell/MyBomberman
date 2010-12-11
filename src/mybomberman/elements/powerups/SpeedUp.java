package mybomberman.elements.powerups;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;

public class SpeedUp extends AbstractPowerUp {

	@Override
	public void takeEffect(Player p) {
		p.setSpeed(p.getSpeed() + 1);
	}

	@Override
	public void takeEffect(Bomb b) {
	}

}
