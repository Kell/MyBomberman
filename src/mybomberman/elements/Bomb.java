package mybomberman.elements;

import java.util.ArrayList;

import mybomberman.Game;
import mybomberman.MapHelper;
import mybomberman.Tile;

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
	private int explodeRange = 1;
	private Player player = null;
	private ArrayList<Tile> explodedTiles = new ArrayList<Tile>();
	
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
		this.explodeRange = player.getExplodeRange();

		setBombLocation();
		
		super.addAnimation("Animation", getAnimation());
		super.addAnimation("Explosion", getExplosionAnimation());
		super.addAnimation("ExplosionLine", getExploLine("res/exp_line.png"));
	}

	public Bomb(Player player) {
		super("res/bomb_anim.png", player.getX(), player.getY());
		super.setImage();

		this.player = player;
		this.explodeRange = player.getExplodeRange();
		this.setTime = System.currentTimeMillis();
		
		setBombLocation();

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
	
	private void setBombLocation() {
		float x = (getX() / 64) - ((int) (getX() / 64) );
		float y = (getY() / 64) - ((int) (getY() / 64));
		
		if (x > 0.5) {
			setX(((int) (getX() / 64) +1 ) * 64);
		} else {
			setX(((int) (getX() / 64) * 64));
		}
		
		if (y > 0.5) {
			setY(((int) (getY() / 64) +1 ) * 64);
		} else {
			setY(((int) (getY() / 64) * 64));
		}
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getDuration() {
		return duration;
	}
	
	/**
	 * 
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	public ArrayList<Tile> getExplodedTiles() {
		return explodedTiles;
	}

	/**
	 * TODO: else should be done once
	 */
	public void drawExplosion(Graphics g, TiledMap map) {
		g.drawAnimation(getAnimation("Explosion"), getX(), getY());
		Animation explosion_anim = getAnimation("ExplosionLine");
		for (int a = 1; a <= explodeRange; a++) {
			// right
			boolean blocked = MapHelper.isTileBlocked((int) getX() + 32 + (64 * a), (int) getY() + 32);
			if (blocked) {
				boolean changed = MapHelper.changeBackgroundTile((int) getX() + (66 * a), (int) getY() + 32, 2, 1);
				if (changed)
					explodedTiles.add(new Tile((int) getX() + 32 + (66 * a), (int) getY() + 32, 64, 64));
			} else {
				explosion_anim.setCurrentFrame(1);
				g.drawAnimation(explosion_anim, getX() + (66 * a), getY());
			}
			
			// left
			blocked = MapHelper.isTileBlocked((int) getX() - (32 * a), (int) getY()+5);
			if (blocked) {
				boolean changed = MapHelper.changeBackgroundTile((int) getX() - (32 * a), (int) getY() + 32, 4, 1);
				if (changed)
					explodedTiles.add(new Tile((int) getX() - (32 * a), (int) getY() + 32, 64, 64));
			} else {
				explosion_anim.setCurrentFrame(2);
				g.drawAnimation(explosion_anim, getX() - (62 * a), getY());
			}
			
			// up
			blocked = MapHelper.isTileBlocked((int) getX() + 32, (int) getY() - (32 * a));
			if (blocked) {
				boolean changed = MapHelper.changeBackgroundTile((int) getX() + 32, (int) getY() - (62 * a), 1, 1);
				if (changed)
					explodedTiles.add(new Tile((int) getX() + 32, (int) getY() - (32 * a), 64, 64));
			} else {
				explosion_anim.setCurrentFrame(1);
				g.drawAnimation(explosion_anim, getX(), getY() - (64 * a));
			}
			
			// down
			blocked = MapHelper.isTileBlocked((int) getX() + 32, (int) getY() + 32 + (64 * a));
			if (blocked) {
				boolean changed = MapHelper.changeBackgroundTile((int) getX() + 32, (int) getY() + 32 + (64 * a), 3, 1);
				if (changed)
					explodedTiles.add(new Tile((int) getX() + 32, (int) getY() + 32 + (64 * a), 64, 64));
			} else {
				explosion_anim.setCurrentFrame(1);
				g.drawAnimation(explosion_anim, getX(), getY() + (64 * a));
			}
		}
	}
	
	public void setBlockedTilesFree() {
		for (Tile t: explodedTiles) {
			if (t != null) {
				Game.blocked[MapHelper.getTileNumber(t.getX())][MapHelper.getTileNumber(t.getY())] = false;
			}
		}
		explodedTiles.clear();
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		long time = System.currentTimeMillis();

		if (time < (setTime + duration)) {
			g.drawAnimation(getAnimation("Animation"), getX(), getY());
		} else if (time < (setTime + maxTime)) {
			g.drawAnimation(getAnimation("Explosion"), getX(), getY());
			drawExplosion(g, Game.map);
		} else {
			setBlockedTilesFree();
			player.removeBomb(this);
		}
	}
}


