package mybomberman.states;

import java.util.ArrayList;

import mybomberman.Game;
import mybomberman.GameControls;
import mybomberman.elements.Player;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GamePlayState extends BasicGameState {
	public static TiledMap map;

	public static boolean blocked[][];
	private int id;
	private Player player, player2;
	private GameControls playerControls, playerControls2;

	private ArrayList<Player> players = null;

	public GamePlayState(int gameplaystate) {
		this.id = gameplaystate;
	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		container.setVSync(true);
		container.setTargetFrameRate(60);
		map = new TiledMap("res/map.tmx");

		players = new ArrayList<Player>();

		player = new Player("res/figure.png", 64, 64);
		players.add(player);

		player2 = new Player("res/figure2.png", 14 * 64, 64);
		players.add(player2);

		playerControls = new GameControls(player, Input.KEY_LEFT,
				Input.KEY_RIGHT, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_P,
				Input.KEY_O);
		playerControls2 = new GameControls(player2, Input.KEY_A, Input.KEY_D,
				Input.KEY_W, Input.KEY_S, Input.KEY_R, Input.KEY_E);

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
	public void render(GameContainer container, StateBasedGame arg1, Graphics g)
			throws SlickException {
		map.render(0, 0);

		for (Player player : players) {
			player.render(container, g);
		}
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {
		playerControls.handleInput(container.getInput());
		playerControls2.handleInput(container.getInput());

		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			arg1.enterState(Game.GAMEMENUSTATE);
		}

	}

	@Override
	public int getID() {
		return this.id;
	}
}