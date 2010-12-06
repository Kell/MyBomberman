import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class MyBomb {

	private int x;
	private int y;
	private long setTime;
	private int range = 1;
	private int duration = 1200;
	private boolean bomb_set = false;
	private boolean explode = false;
	private SpriteSheet image;
	private Animation animation;
	private SpriteSheet explosion_center;
	private Animation exp_center;

	public MyBomb(String img_path) {
		loadImage(img_path);
		loadBombAnimation();
		loadExplosionAnimation();
	}

	public MyBomb(int x, int y, String img_path) {
		this.x = x;
		this.y = y;
		loadImage(img_path);
		loadBombAnimation();
		loadExplosionAnimation();
	}

	public long getSetTime() {
		return setTime;
	}

	public void setSetTime(long setTime) {
		this.setTime = setTime;
	}

	public int getDuration() {
		return duration;
	}

	private void loadImage(String img_path) {
		try {
			image = new SpriteSheet(img_path, 32, 32);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
	}

	private void loadBombAnimation() {
		animation = new Animation();
		for (int frame = 0; frame < 3; frame++) {
			animation.addFrame(image.getSprite(frame, 0), 250);
		}
	}

	private void loadExplosionAnimation() {
		try {
			explosion_center = new SpriteSheet("res/exp_center.png", 32, 32);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
		exp_center = new Animation();
		for (int yframe = 0; yframe < 2; yframe++) {
			for (int xframe = 0; xframe < 3; xframe++) {
				exp_center.addFrame(explosion_center.getSprite(xframe, yframe),
						150);
			}
		}
	}

	public SpriteSheet getImage() {
		return image;
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

	public Animation getAnimation() {
		return animation;
	}

	public Animation getExpAnimation() {
		return exp_center;
	}

	public boolean isBomb_set() {
		return bomb_set;
	}

	public void setBomb_set(boolean bomb_set) {
		this.bomb_set = bomb_set;
	}

	public boolean isExplode() {
		return explode;
	}

	public void setExplode(boolean explode) {
		this.explode = explode;
	}

}
