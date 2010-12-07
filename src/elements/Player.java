package elements;

import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import elements.powerups.AbstractPowerUp;

public class Player {
	private int x;
	private int y;
	private SpriteSheet img;
	private ArrayList<AbstractPowerUp> powerUps = null;
	private int bombCount = 1;
	private int speed;

	public Player(int x, int y, String img) {
		try {
			this.img = new SpriteSheet(img, 64, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		powerUps = new ArrayList<AbstractPowerUp>();
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public SpriteSheet getImg() {
		return img;
	}

	public void setImg(SpriteSheet img) {
		this.img = img;
	}

	public void addPowerUp(AbstractPowerUp powerup) {
		this.powerUps.add(powerup);
	}

	public void removePowerUps() {
		this.powerUps = new ArrayList<AbstractPowerUp>();
	}

	public ArrayList<AbstractPowerUp> getPowerUps() {
		return this.powerUps;
	}

	public void setSpeed(int i) {
		this.speed = i;
	}

	public int getSpeed() {
		return this.speed;
	}

	public void addBombCount() {
		this.bombCount++;
	}

	public void subtractBombCount() {
		this.bombCount--;
	}
}
