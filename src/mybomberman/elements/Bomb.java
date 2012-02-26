package mybomberman.elements;

import mybomberman.MapHelper;
import mybomberman.states.GamePlayState;
import java.util.ArrayList;

import mybomberman.Tile;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.tiled.TiledMap;

public class Bomb extends Sprite {

	private long setTime;
	private long duration = 1650;
	private long maxTime = 2500;
	private int explodeRange = 2;
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

		super.addAnimation("Animation", getBombAnimation());
		super.addAnimation("Explosion", getExplosionAnimation());
		super.addAnimation("ExplosionLine", getExplosionLineAnimation("res/exp_line64.png"));
	}

	public Bomb(Player player) {
		super("res/bomb_anim.png", player.getX(), player.getY());
		super.setImage();

		this.player = player;
		this.explodeRange = player.getExplodeRange();
		this.setTime = System.currentTimeMillis();

		setBombLocation();

		super.addAnimation("Animation", getBombAnimation());
		super.addAnimation("Explosion", getExplosionAnimation());
		super.addAnimation("ExplosionLine", getExplosionLineAnimation("res/exp_line64.png"));
	}

	/**
	 * @param img_path
	 * @return
	 */
	private Animation getExplosionLineAnimation(String img_path) {
		Animation exp_line = null;
		SpriteSheet explosion_line = null;
		try {
			explosion_line = new SpriteSheet(img_path, 64, 64);
		} catch (SlickException e) {
			System.out.println("Exception: " + e);
		}
		exp_line = new Animation();
		// exp_line.setAutoUpdate(false);
		for (int xframe = 0; xframe < 3; xframe++) {
			exp_line.addFrame(explosion_line.getSprite(xframe, 0), 150);
		}
		return exp_line;
	}

	/**
	 * 
	 * 
	 * @return
	 */
	private Animation getBombAnimation() {
		Animation animation = new Animation();
		for (int frame = 0; frame < 3; frame++) {
			animation.addFrame(getImage().getSprite(frame, 0), 250);
		}
		return animation;
	}

	/**
	 * 
	 * @return
	 */
	private Animation getExplosionAnimation() {
		SpriteSheet explosion_center = null;
		try {
			explosion_center = new SpriteSheet("res/explo_center64.png", 64, 64);
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

	/**
	 * Set the bomb location - bomb can only be set on a tile and not between them.
	 **/
	private void setBombLocation() {
		float x = (getX() / 64) - ((int) (getX() / 64));
		float y = (getY() / 64) - ((int) (getY() / 64));

		if (x > 0.5) {
			setX(((int) (getX() / 64) + 1) * 64);
		} else {
			setX(((int) (getX() / 64) * 64));
		}

		if (y > 0.5) {
			setY(((int) (getY() / 64) + 1) * 64);
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
	 * 
	 * Draws the explosion lines
	 * 
	 * @param g
	 *            - Graphics object for drawing
	 * @param map
	 *            - the map
	 */
	public void drawExplosion(Graphics g, TiledMap map, ArrayList<Player> players) {
		
		g.drawAnimation(getAnimation("Explosion"), getX(), getY());
		Animation explosion_anim = getAnimation("ExplosionLine");

		boolean r_blocked = false;
		boolean l_blocked = false;
		boolean u_blocked = false;
		boolean d_blocked = false;
		int explosionXCorrdinate = 0;
		int explosionYCorrdinate = 0;
		int changeTileID = 3;
		int changeLowerTileID = 3;
		
		for (int a = 1; a <= explodeRange; a++) {
			
			// ---------- RIGHT ----------
			explosionXCorrdinate = (int) getX() + (64 * a);
			explosionYCorrdinate = (int) getY() + 32;
			boolean blocked = MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate);
			boolean breakable = MapHelper.isTileBreakable(explosionXCorrdinate, explosionYCorrdinate);
			
			
			//check if tile with upper shadow is needed
			if(MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate-64))
				changeTileID = 4;
			else
				changeTileID = 3;
			
			if (breakable) {
				boolean changed = false;
				if (!r_blocked)
				{
					changed = MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate, 2, changeTileID);
					if(GamePlayState.map.getTileId(explosionXCorrdinate/64, (explosionYCorrdinate/64)+1, 0) == 4)
						MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate+64, 2, changeLowerTileID);
				}

				r_blocked = true;
				if (changed)
					explodedTiles.add(new Tile(explosionXCorrdinate, explosionYCorrdinate, 64, 64));
			} else if (!blocked) {
				explosion_anim.getCurrentFrame().setRotation(90);
				if (!r_blocked) 
					g.drawAnimation(explosion_anim, explosionXCorrdinate, getY());
				
				//CHECK WHETHER PLAYER GET BURNED BY EXPLOSION
				isPlayerBurned(players, explosionXCorrdinate, (int) getY(), 2);
				
			}

			// ---------- LEFT ----------
			explosionXCorrdinate = (int) getX() - (64 * a);
			explosionYCorrdinate = (int) getY() + 32;
			blocked = MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate);
			breakable = MapHelper.isTileBreakable(explosionXCorrdinate, explosionYCorrdinate);
			
			
			//check if tile with upper shadow is needed
			if(MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate-64))
				changeTileID = 4;
			else
				changeTileID = 3;
			
			
			if (breakable) {
				boolean changed = false;
				if (!l_blocked)
				{
					changed = MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate, 4, changeTileID);
					if(GamePlayState.map.getTileId(explosionXCorrdinate/64, (explosionYCorrdinate/64)+1, 0) == 4)
						MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate+64, 2, changeLowerTileID);
				}

				l_blocked = true;
				if (changed)
					explodedTiles.add(new Tile(explosionXCorrdinate, explosionYCorrdinate, 64, 64));
			} else if (!blocked) {
				explosion_anim.getCurrentFrame().setRotation(270);
				if (!l_blocked)
					g.drawAnimation(explosion_anim, explosionXCorrdinate, getY());
				
				//CHECK WHETHER PLAYER GET BURNED BY EXPLOSION
				isPlayerBurned(players, explosionXCorrdinate, (int) getY(), 4);
			}

			// ---------- UP ----------
			explosionXCorrdinate = (int) getX() + 32;
			explosionYCorrdinate = (int) getY() - (64 * a);
			blocked = MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate);
			breakable = MapHelper.isTileBreakable(explosionXCorrdinate, explosionYCorrdinate);
			
			//check if tile with upper shadow is needed
			if(MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate-64))
				changeTileID = 4;
			else
				changeTileID = 3;
			
			if (breakable) {
				boolean changed = false;
				if (!u_blocked)
				{
					changed = MapHelper.changeBackgroundTile(explosionXCorrdinate,	explosionYCorrdinate, 1, changeTileID);
					if(GamePlayState.map.getTileId(explosionXCorrdinate/64, (explosionYCorrdinate/64)+1, 0) == 4)
						MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate+64, 2, changeLowerTileID);
				}

				u_blocked = true;
				if (changed)
					explodedTiles.add(new Tile(explosionXCorrdinate, explosionYCorrdinate, 64, 64));
			} else if (!blocked) {
				explosion_anim.getCurrentFrame().setRotation(0);
				if (!u_blocked)
					g.drawAnimation(explosion_anim, getX(), explosionYCorrdinate);
				
				//CHECK WHETHER PLAYER GET BURNED BY EXPLOSION
				isPlayerBurned(players, (int) getX(), explosionYCorrdinate, 1);
			}

			// ---------- DOWN ----------
			explosionXCorrdinate = (int) getX() + 32;
			explosionYCorrdinate = (int) getY() + (64 * a);
			blocked = MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate);
			breakable = MapHelper.isTileBreakable(explosionXCorrdinate, explosionYCorrdinate);
			
			//check if tile with upper shadow is needed
			if(MapHelper.isTileBlocked(explosionXCorrdinate, explosionYCorrdinate-64))
				changeTileID = 4;
			else
				changeTileID = 3;
			
			if (breakable) {

				boolean changed = false;
				if (!d_blocked)
				{
					changed = MapHelper.changeBackgroundTile(explosionXCorrdinate,explosionYCorrdinate, 3, changeTileID);
					if(GamePlayState.map.getTileId(explosionXCorrdinate/64, (explosionYCorrdinate/64)+1, 0) == 4) 
						MapHelper.changeBackgroundTile(explosionXCorrdinate, explosionYCorrdinate+64, 2, changeLowerTileID);
				}

				d_blocked = true;
				if (changed)
					explodedTiles.add(new Tile(explosionXCorrdinate, explosionYCorrdinate, 64, 64));
			} else if (!blocked){
				explosion_anim.getCurrentFrame().setRotation(180);
				if (!d_blocked)
					g.drawAnimation(explosion_anim, getX(), explosionYCorrdinate);
				
				//CHECK WHETHER PLAYER GET BURNED BY EXPLOSION
				isPlayerBurned(players, (int) getX(), explosionYCorrdinate, 3);
			}
		}
	}

	
	
	public void isPlayerBurned(ArrayList<Player> players, int x, int y, int direction) {
		
		int explosionX = x;
		int explosionXEnd = 0;
		int explosionY = y;
		int explosionYEnd = 0;
		
		// 62 some Player Buffer
		switch (direction) {
			case 1:
				explosionXEnd = explosionX + 62;
				explosionYEnd = explosionY - 62;
				break;
			case 2:
				explosionXEnd = explosionX + 62;
				explosionYEnd = explosionY + 62;
				break;
			case 3:
				explosionXEnd = explosionX + 62;
				explosionYEnd = explosionY + 62;
				break;
			case 4:
				explosionXEnd = explosionX + 62;
				explosionYEnd = explosionY + 62;
				break;
		}
			
		for (Player player: players) {
			//DEBUG
			if (player.getID() == 1) {
//				System.out.println("player x:"+player.getX()+" Exp X:"+explosionX);
//				System.out.println("player y:"+player.getX()+" Exp Y:"+explosionY);
			}
//			System.out.println("player:"+player.getID());
		
			//TODO: check whether player  got the exact position as the explosion tile 
			if (
				(player.getX() >= explosionX && player.getX() <= explosionXEnd)
						||
				(player.getX() + 64 >= explosionX  && player.getX() + 64 <= explosionXEnd)
				&&
				(
					(player.getY() >= explosionY) && (player.getY() <= explosionYEnd)
					|| 
					(player.getY() + 64 >= explosionY) && (player.getY() <= explosionYEnd)
				)
			)
			{
				if(!player.isBurned()) {
					player.setLives(player.getLives()-1);
					player.setBurned(true);
				}
			}
		}
	}
	
	
	public void setBlockedTilesFree() {
		for (Tile t : explodedTiles) {
			if (t != null) {
				GamePlayState.blocked[MapHelper.getTileNumber(t.getX())][MapHelper.getTileNumber(t.getY())] = false;
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
			drawExplosion(g, GamePlayState.map, GamePlayState.players);
		} else {
			for (Player player: GamePlayState.players )
			{
				if(player.isBurned())
					player.setBurned(false);
			}
			setBlockedTilesFree();
			player.removeBomb(this);
		}
	}
}
