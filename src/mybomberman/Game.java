package mybomberman;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;
import mybomberman.elements.powerups.AbstractPowerUp;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Game extends BasicGame {
	public static boolean blocked[][];
	private TiledMap map;
	private Player player;
	private Camera camera;
	private int cur_tile;
	private int l_tile;
	private int r_tile;
	private int u_tile;
	private int lo_tile;

	public Game() {
		super("Slick bomberman");
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		container.setVSync(true);
		container.setTargetFrameRate(60);
		player = new Player("res/figure.png", 64, 64);
		map = new TiledMap("res/map.tmx");
		camera = new Camera(container, map);

		blocked = new boolean[map.getWidth()][map.getHeight()];
		for (int xAxis = 0; xAxis < map.getWidth(); xAxis++) {
			for (int yAxis = 0; yAxis < map.getHeight(); yAxis++) {
				int tileID = map.getTileId(xAxis, yAxis, 0);
				String value = map.getTileProperty(tileID, "blocked", "false");
				if (value.equals("1")) {
					blocked[xAxis][yAxis] = true;
				}
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta) {
		cur_tile = (player.getX() + 32) / 64;
		cur_tile = map.getTileId(cur_tile, (player.getY() + 32) / 64, 0);
		l_tile = (player.getX() - 32) / 64;
		l_tile = map.getTileId(l_tile, (player.getY() + 32) / 64, 0);
		r_tile = (player.getX() + 64) / 64;
		r_tile = map.getTileId(r_tile, (player.getY() + 32) / 32, 0);
		u_tile = (player.getY() - 2) / 64;
		u_tile = map.getTileId(player.getX() / 64, u_tile, 0);
		lo_tile = (player.getY() + 66) / 64;
		lo_tile = map.getTileId(player.getX() / 64, lo_tile, 0);

		if (container.getInput().isKeyDown(Input.KEY_LEFT)) {
			player.setMovement(true, false, false, false);
		} else if (container.getInput().isKeyDown(Input.KEY_RIGHT)) {
			player.setMovement(false, false, true, false);
		} else if (container.getInput().isKeyDown(Input.KEY_UP)) {
			player.setMovement(false, true, false, false);
		} else if (container.getInput().isKeyDown(Input.KEY_DOWN)) {
			player.setMovement(false, false, false, true);
		}

		if (container.getInput().isKeyPressed(Input.KEY_H)) {
			player.addBomb(new Bomb(player.getX(), player.getY(), player));
		}
		if (container.getInput().isKeyPressed(Input.KEY_G)) {
			player.addBombCount();
		}

		if (container.getInput().isKeyPressed(Input.KEY_J)) {
			System.out.println(player.getMaxBombs());
			for (AbstractPowerUp item : player.getPowerUps()) {
				item.takeEffect(player);
			}
		}

		/*
		 * if (container.getInput().isKeyPressed(Input.KEY_SPACE) &&
		 * bomb.isBomb_set() == false) { bomb.setX(player.getX());
		 * bomb.setY(player.getY()); bomb.setBomb_set(true);
		 * bomb.setSetTime(Sys.getTime()); }
		 */

		camera.centerOn(player.getX(), player.getY());
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		map.render(0, 0);
		player.render(container, g);
		// g.drawString("cur TILE: " + cur_tile, 100, 10);
		// g.drawString("left TILE: " + l_tile, 250, 10);
		// g.drawString("right TILE: " + r_tile, 400, 10);
		// g.drawString("upper TILE: " + u_tile, 550, 10);
		// g.drawString("lower TILE: " + lo_tile, 700, 10);
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Game(), 1024,
				768, false);
		container.start();
	}
}