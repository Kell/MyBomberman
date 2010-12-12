package mybomberman.elements;

import mybomberman.GamePlayState;
import mybomberman.MapHelper;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Bomb extends Sprite {

	private long setTime;
	private long duration = 1500;
	private long maxTime = 2500;
	private int exploderange = 1;
	private Player player = null;

	/**
	 * dummy element - for testing purpose only
	 */
	public Bomb() {
		super("res/bomb_anim.png");
	}

	/**
	 * 
	 * @param x
	 * @param y
	 * @param player
	 */
	public Bomb(int x, int y, Player player) {
		super("res/bomb_anim.png", x, y);
		super.setImage();

		this.player = player;
		this.exploderange = player.getExplodeRange();

		super.addAnimation("Animation", getAnimation());
		super.addAnimation("Explosion", getExplosionAnimation());
		super.addAnimation("ExplosionLine", getExploLine("res/exp_line.png"));
	}

	public Bomb(Player player) {
		super("res/bomb_anim.png", player.getX(), player.getY());
		super.setImage();

		this.player = player;
		this.exploderange = player.getExplodeRange();
		this.setTime = System.currentTimeMillis();

		super.addAnimation("Animation", getAnimation());
		super.addAnimation("Explosion", getExplosionAnimation());
		super.addAnimation("ExplosionLine", getExploLine("res/exp_line.png"));
	}

	/**
	 * @TODO change name!
	 * @param img_path
	 * @return
	 */
	private Animation getExploLine(String img_path) {
		Animation exp_line = null;
		SpriteSheet explosion_line = null;
		try {
			explosion_line = new SpriteSheet(img_path, 32, 32);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
		exp_line = new Animation();
		exp_line.setAutoUpdate(false);
		for (int yframe = 0; yframe < 3; yframe++) {
			for (int xframe = 0; xframe < 2; xframe++) {
				exp_line.addFrame(explosion_line.getSprite(xframe, yframe), 150);
			}
		}
		return exp_line;
	}

	/**
	 * TODO change name!
	 * 
	 * @return
	 */
	private Animation getAnimation() {
		Animation animation = new Animation();
		for (int frame = 0; frame < 3; frame++) {
			animation.addFrame(getImage().getSprite(frame, 0), 250);
		}
		return animation;
	}

	/**
	 * TODO change name
	 * 
	 * @return
	 */
	private Animation getExplosionAnimation() {
		SpriteSheet explosion_center = null;
		try {
			explosion_center = new SpriteSheet("res/exp_center.png", 32, 32);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
		Animation exp_center = new Animation();
		for (int yframe = 0; yframe < 2; yframe++) {
			for (int xframe = 0; xframe < 3; xframe++) {
				exp_center.addFrame(explosion_center.getSprite(xframe, yframe),
						150);
			}
		}
		return exp_center;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}

	/**
	 * TODO: else should be done once
	 */
	public void drawExplosion(Graphics g, TiledMap map) {
		g.drawAnimation(getAnimation("Explosion"), getX(), getY());
		Animation explosion_anim = getAnimation("ExplosionLine");
		for (int a = 1; a <= exploderange; a++) {
			// right
			boolean blocked = MapHelper
					.isTileBlocked(getX() + (66 * a), getY());
			int blaX = getX() + (66 * a);
			int blaY = getY();
			System.out.println("\nblocked right:" + blocked + " x:" + blaX
					+ "|y:" + blaY);
			if (blocked) {
				MapHelper.changeBackgroundTile(getX() + (66 * a), getY(), 2, 1);
				explosion_anim.setCurrentFrame(1);
				System.out.println("breakable true :" + a);
			} else {
				System.out.println("breakable false :" + a);
				g.drawAnimation(explosion_anim, getX() + (64 * a), getY());
			}
			/*
			 * // left blocked = MapHelper.isTileBlocked(getX() - (64 * a),
			 * getY());
			 * System.out.println("blocked left:"+blocked+" x:"+blaX+"|y:"
			 * +blaY); if (blocked) { MapHelper.changeBackgroundTile(getX() -
			 * (64 * a), getY(), 4, 1); explosion_anim.setCurrentFrame(2); }
			 * else { g.drawAnimation(explosion_anim, getX() - (64 * a),
			 * getY()); }
			 * 
			 * // up blocked = MapHelper.isTileBlocked(getX(), getY() - (66 *
			 * a));
			 * System.out.println("blocked up:"+blocked+" x:"+blaX+"|y:"+blaY);
			 * if (blocked) { MapHelper.changeBackgroundTile(getX(), getY() -
			 * (66 * a), 1, 1); explosion_anim.setCurrentFrame(1); } else {
			 * g.drawAnimation(explosion_anim, getX(), getY() - (64 * a)); }
			 * 
			 * // down blocked = MapHelper.isTileBlocked(getX(), getY() + (66 *
			 * a));
			 * System.out.println("blocked down:"+blocked+" x:"+blaX+"|y:"+blaY
			 * ); if (blocked) { MapHelper.changeBackgroundTile(getX(), getY() +
			 * (66 * a), 3, 1); explosion_anim.setCurrentFrame(1); } else {
			 * g.drawAnimation(explosion_anim, getX(), getY() + (64 * a)); }
			 */
		}
	}

	/**
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		long time = System.currentTimeMillis();

		if (time < (setTime + duration)) {
			g.drawAnimation(getAnimation("Animation"), getX(), getY());
		} else if (time < (setTime + maxTime)) {
			g.drawAnimation(getAnimation("Explosion"), getX(), getY());
			drawExplosion(g, GamePlayState.map);
		} else {
			player.removeBomb(this);
		}
	}
}
