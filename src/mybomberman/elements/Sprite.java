package mybomberman.elements;

import java.util.HashMap;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public abstract class Sprite {

	private SpriteSheet image = null;
	private Image sprite = null;
	private HashMap<String, Animation> animations = new HashMap<String, Animation>();
	private int x;
	private int y;

	public Sprite(String img) {
		loadImage(img);
	}

	public Sprite(String img, int x, int y) {
		loadImage(img);
		this.x = x;
		this.y = y;
	}

	public void render(GameContainer container, Graphics g) {
		g.drawImage(sprite, x, y);
	}

	public void addAnimation(String name, Animation animation) {
		animations.put(name, animation);
	}

	public Animation getAnimation(String name) {
		return animations.get(name);
	}

	public SpriteSheet getImage() {
		return this.image;
	}

	public void setImage() {
		this.sprite = this.image.getSprite(0, 0);
	}

	private void loadImage(String img_path) {
		try {
			image = new SpriteSheet(img_path, 64, 64);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
	}

	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
