package mybomberman.elements.powerups;

import org.newdawn.slick.Image;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;


public abstract class AbstractPowerUp {

	
	public AbstractPowerUp() {
	}

	public abstract void takeEffect(Player p);

	public abstract void takeEffect(Bomb b);

}
