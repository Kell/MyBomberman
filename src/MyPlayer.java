import java.awt.Point;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MyPlayer {
	private int x;
	private int y;
	private SpriteSheet img;
	private Point ruc;
	private Point rlc;
	private Point luc;
	private Point llc;
	
	public MyPlayer(int x, int y, String img) {
		try {
			this.img = new SpriteSheet(img, 32, 32);
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		this.x = x;
		this.y = y;
		setCornerCoordinates(x, y);
	}
	
	public MyPlayer() {
		
	}
	
	private void setCornerCoordinates(int x, int y) {
		ruc = new Point(x + 32, y);
		rlc = new Point(x + 32, y + 32);
		luc = new Point(x, y);
		llc = new Point(x, y + 32);
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

	public Point getRuc() {
		return ruc;
	}

	public Point getRlc() {
		return rlc;
	}

	public Point getLuc() {
		return luc;
	}

	public Point getLlc() {
		return llc;
	}
	
	
	
}
