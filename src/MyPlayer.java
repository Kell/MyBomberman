import java.awt.Point;


import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class MyPlayer {
	private int x;
	private int y;
	private SpriteSheet img;
	
	private Point ruc;	// right upper corner
	private Point rlc;	// right lower corner
	private Point luc;	// left upper corner
	private Point llc;	// left lower corner
	
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
		ruc.x = x+32;
		ruc.y = y;
		return ruc;
	}

	public Point getRlc() {
		rlc.x = x +32;
		rlc.y = y+32;
		return rlc;
	}

	public Point getLuc() {
		luc.x = x;
		luc.y = y;
		return luc;
	}

	public Point getLlc() {
		llc.x = x;
		llc.y = y +32;
		return llc;
	}
}
