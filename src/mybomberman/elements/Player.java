package mybomberman.elements;

import java.util.ArrayList;

import mybomberman.CollisionDetection;
import mybomberman.elements.powerups.AbstractPowerUp;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class Player extends Sprite {
	private ArrayList<AbstractPowerUp> powerUps = null;
	private ArrayList<Bomb> bombs = null;
	private int bombCount = 1;
	private int exploderange = 1;
	private int speed = 2;
	private boolean left = false;
	private boolean up = false;
	private boolean right = false;
	private boolean down = false;

	public Player(String img, int x, int y) {
		super(img, x, y);
		powerUps = new ArrayList<AbstractPowerUp>();
		bombs = new ArrayList<Bomb>();
		super.setImage();
		super.addAnimation("Animation", getAnimation());
	}

	private Animation getAnimation() {
		Animation player_anim = new Animation();
		player_anim.setAutoUpdate(false);
		for (int frame = 0; frame < 4; frame++) {
			player_anim.addFrame(getImage().getSprite(frame, 0), 150);
		}
		return player_anim;
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

	public int getBombCount() {
		return this.bombCount;
	}

	public void setBombCount(int count) {
		this.bombCount = count;
	}

	public void addBomb(Bomb bomb) {
		if (this.bombs.size() < this.bombCount) {
			this.bombs.add(bomb);
		}
	}

	public void removeBomb(Bomb bomb) {
		this.bombs.remove(bomb);
	}

	public ArrayList<Bomb> getBombs() {
		return this.bombs;
	}

	public int getExplodeRange() {
		return this.exploderange;
	}

	public void setExplodeRange(int range) {
		this.exploderange = range;
	}

	public void setMovementUP() {
		this.up = true;
	}

	public void setMovementDOWN() {
		this.down = true;
	}

	public void setMovementLEFT() {
		this.left = true;
	}

	public void setMovementRIGHT() {
		this.right = true;
	}

	public void setMovementACTION() {
	}

	public void setMovementACTION2() {
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		super.render(container, g);

		Animation anim = getAnimation("Animation");
		boolean walkable;
		if (left) {
			anim.setCurrentFrame(2);
			walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 4);
			if (walkable) {
				setX(getX() - speed);
			}
		} else if (up) {
			anim.setCurrentFrame(1);
			walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 1);
			if (walkable) {
				setY(getY() - speed);
			}
		} else if (right) {

			anim.setCurrentFrame(3);
			walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 2);

			if (walkable) {
				setX((int) ((getX() + speed)));
			}
		} else if (down) {

			anim.setCurrentFrame(0);
			walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 3);
			if (walkable) {
				setY(getY() + speed);
			}
		}

		for (int i = 0; i < getBombs().size(); i++) {
			getBombs().get(i).render(container, g);
		}

		left = false;
		up = false;
		down = false;
		right = false;
	}
}