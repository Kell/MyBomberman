import java.util.ArrayList;

import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MyPlayer {
	private int x;
	private int y;
	private SpriteSheet img;
	private int countBomb = 1;
	private ArrayList<MyBomb> bombs = null;

	public MyPlayer(int x, int y, String img) {
		try {
			this.img = new SpriteSheet(img, 64, 64);
		} catch (SlickException e) {
			e.printStackTrace();
		}

		this.x = x;
		this.y = y;
		this.bombs = new ArrayList<MyBomb>();
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

	public void addBomb() {
		this.countBomb++;
	}

	public void subtractBomb() {
		this.countBomb--;
	}

	public ArrayList<MyBomb> getBombs() {
		return this.bombs;
	}

	public int getBombCount() {
		return this.countBomb;
	}

	public void addBomb(MyBomb bomb) {
		this.bombs.add(bomb);
	}
}
