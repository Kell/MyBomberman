import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MyPlayer {
	private int x;
	private int y;
	private SpriteSheet img;
	
	public MyPlayer(int x, int y, String img) {
		try {
			this.img = new SpriteSheet(img, 32, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
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
}
