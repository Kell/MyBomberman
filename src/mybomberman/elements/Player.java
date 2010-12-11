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
	private int speed;
	private boolean left = false;
	private boolean up = false;
	private boolean right = false;
	private boolean down = false;

	public Player(String img, int x, int y) {
		super(img, x, y);
		powerUps = new ArrayList<AbstractPowerUp>();
		bombs = new ArrayList<Bomb>();
		super.setImage();
		super.addAnimation("Animation", getAni());
	}

	private Animation getAni() {
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

	public void addBombCount() {
		this.bombCount++;
	}

	public void subtractBombCount() {
		this.bombCount--;
	}

	public void addBomb(Bomb bomb) {
		if (this.bombs.size() < this.bombCount) {
			this.bombs.add(bomb);
		}
	}

	public void removeBomb(Bomb bomb) {
		this.bombs.remove(bomb);
	}

	public int getMaxBombs() {
		return this.bombCount;
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

	public void setMovement(boolean b, boolean c, boolean d, boolean e) {
		this.left = b;
		this.up = c;
		this.right = d;
		this.down = e;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		super.render(container, g);

		Animation test = getAnimation("Animation");
		if (left) {
			test.setCurrentFrame(2);
			boolean walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 4);
			if (walkable) {
				setX(getX() - 2);
			}
		} else if (up) {
			test.setCurrentFrame(1);
			boolean walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 1);
			if (walkable) {
				setY(getY() - 2);
			}
		} else if (right) {
			test.setCurrentFrame(3);
			boolean walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 2);
			if (walkable) {
				setX((int) ((getX() + 2)));
			}
		} else if (down) {
			test.setCurrentFrame(0);
			boolean walkable = CollisionDetection.IsTileWalkable(getX(),
					getY(), 64, 64, 3);
			if (walkable) {
				setY(getY() + 2);
			}
		}

		for (int i = 0; i < getBombs().size(); i++) {
			getBombs().get(i).render(container, g);
		}
	}
}