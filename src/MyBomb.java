import org.newdawn.slick.Animation;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;


public class MyBomb {

	private int x;
	private int y;
	private long setTime;
	private int duration = 2000;
	public long getSetTime() {
		return setTime;
	}

	public void setSetTime(long setTime) {
		this.setTime = setTime;
	}

	private SpriteSheet image;
	private Animation animation;

	
	public MyBomb(int x, int y, String img_path) {
		this.x = x;
		this.y = y;
		
		loadImage(img_path);
		animation = new Animation();
		for (int frame=0;frame<3; frame++) {
			animation.addFrame(image.getSprite(frame,0), 150);
		}
	}
	
	public int getDuration() {
		return duration;
	}

	public MyBomb(String img_path) {
		loadImage(img_path);
		animation = new Animation();
		for (int frame=0;frame<3; frame++) {
			animation.addFrame(image.getSprite(frame,0), 250);
		}
		
	}
	
	private void loadImage(String img_path) {
		try {
			image = new SpriteSheet(img_path, 32, 32);
		} catch (SlickException e) {
			System.out.println("Exception: "+e);
		}
	}
	
	public SpriteSheet getImage(){
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
	
}
