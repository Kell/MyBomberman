package elements;
import java.util.HashMap;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Player {
	private int x;
	private int y;
	private SpriteSheet img;
	private HashMap<String, Integer> powerUps;

	public Player(int x, int y, String img) {
		try {
			this.img = new SpriteSheet(img, 64, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;

		InitializePowerUps();
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

	private void InitializePowerUps() {
		powerUps = new HashMap<String, Integer>();
		
		powerUps.put("bombs", 1);
		powerUps.put("speed", 1);
		powerUps.put("range", 1);
	}

	public HashMap<String, Integer> getPowerUps() {
		return powerUps;
	}
}
