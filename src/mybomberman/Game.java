package mybomberman;

import java.util.ArrayList;

import mybomberman.elements.Player;
import mybomberman.states.GameMPState;
import mybomberman.states.GameMenuState;
import mybomberman.states.GameMultiplayerLobbyState;
import mybomberman.states.GameOptionState;
import mybomberman.states.GamePlayState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {
	public static boolean blocked[][];
	private Player player, player2;
	private GameControls playerControls, playerControls2;
	private ArrayList<Player> players = null;
	public static final int GAMEMENUSTATE = 0;
	public static final int GAMEOPTIONSTATE = 1;
	public static final int GAMEMPLOBBY = 2;
	public static final int GAMEPLAYSTATE = 3;
	public static final int GAMEMP = 4;

	public Game() {
		super("MyBomberman");

		this.addState(new GameMenuState(GAMEMENUSTATE));
		this.addState(new GameOptionState(GAMEOPTIONSTATE));
		this.addState(new GameMultiplayerLobbyState(GAMEMPLOBBY));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
		this.addState(new GameMPState(GAMEMP));
		this.enterState(GAMEMENUSTATE);
		players = new ArrayList<Player>();
		player = new Player("res/figure.png", 64, 64);
		// test
		player.setExplodeRange(2);
		player.setBombCount(3);

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
	public void update(GameContainer container, int delta) {
		playerControls.handleInput(container.getInput());
		playerControls2.handleInput(container.getInput());
		for (Player player : players) {
			player.update(container, delta);
		}
	}

	@Override
	public void render(GameContainer container, Graphics g) {
		map.render(0, 0);

		for (Player player : players) {
			player.render(container, g);
		}
	}

	public static void main(String[] argv) throws SlickException {
		AppGameContainer container = new AppGameContainer(new Game(), 1024,
				768, false);
		container.start();
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.getState(GAMEMENUSTATE).init(container, this);
		this.getState(GAMEPLAYSTATE).init(container, this);
		this.getState(GAMEOPTIONSTATE).init(container, this);
		this.getState(GAMEMPLOBBY).init(container, this);
		this.getState(GAMEMP).init(container, this);
	}

}
