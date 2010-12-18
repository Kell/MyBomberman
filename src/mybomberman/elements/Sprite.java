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
	private float x;
	private float y;

	public Sprite(String img) {
		loadImage(img);
	}

	public Sprite(String img, float x, float y) {
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

	public float getX() {
		return this.x;
	}

	public float getY() {
		return this.y;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

}
