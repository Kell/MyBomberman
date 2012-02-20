package mybomberman.states;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

public class GameMPState extends BasicGameState implements Runnable {
	public static TiledMap map;

	public static boolean blocked[][];
	private int id;
	private Player player = null;
	private Player otherPlayer = null;
	private GameControls playerControls;
	private String servername = "localhost";
	private int port = 1234;
	private DatagramSocket verbindung = null;
	private DatagramPacket packet = null;
	private ArrayList<Player> players = null;
	private boolean first = true;
	private boolean isRunning = true;
	Thread test = null;

	public GameMPState(int gameplaystate) {
		this.id = gameplaystate;
	}

	@Override
	public void init(GameContainer container, StateBasedGame arg1)
			throws SlickException {
		container.setVSync(true);
		container.setTargetFrameRate(60);
		map = new TiledMap("res/map.tmx");

		players = new ArrayList<Player>();

		player = new Player("res/figure.png", 64, 64, 1);
		players.add(player);

		playerControls = new GameControls(player, Input.KEY_LEFT,
				Input.KEY_RIGHT, Input.KEY_UP, Input.KEY_DOWN, Input.KEY_P,
				Input.KEY_O);

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
		test = new Thread(this);
		test.start();
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
		if (first) {
			senden("ANMELDUNG");
			first = false;
		}
		playerControls.handleInput(container.getInput());

		if (System.nanoTime() % 10 == 0) {
			sendePosition();
		}

		/*
		 * if (container.getInput().isKeyPressed(Input.KEY_Z)) { if (first) {
		 * senden("ANMELDUNG"); first = false; } else { sendePosition(); } //
		 * empfang(); }
		 */

		/*
		 * if (container.getInput().isKeyPressed(Input.KEY_T)) { test = new
		 * Thread(this); test.start(); }
		 */

		if (container.getInput().isKeyPressed(Input.KEY_W)) {
			sendeChat("HALLO");
		}

		if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			senden("ENDE");
			isRunning = false;
			arg1.enterState(Game.GAMEMENUSTATE);
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

	private void empfang() {
		if (verbindung == null) {
			try {
				verbindung = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		byte[] buf = new byte[256];
		DatagramPacket packet = new DatagramPacket(buf, buf.length);
		try {
			verbindung.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}

		validate(new String(packet.getData(), 0, packet.getLength()));
	}

	private void validate(String command) {
		System.out.println("COMMAND:\t" + command);
		if (command.startsWith("ACCEPTED")) {
			String[] split = command.split(" ");
			int id = Integer.parseInt(split[1]);
			player.setID(id);
			System.out.println("Meine ID ist: " + player.getID());
		} else if (command.startsWith("OKAY")) {
			System.out.println("OKAY");
		} else if (command.startsWith("NEUERPLAYER")) {
			String[] split = command.split(" ");
			int id = Integer.parseInt(split[1]);
			int x = Integer.parseInt(split[2]);
			int y = Integer.parseInt(split[3]);

			otherPlayer = new Player(id);
			otherPlayer.setX(x);
			otherPlayer.setY(y);
			players.add(otherPlayer);
		} else if (command.startsWith("POSITION")) {
			String[] split = command.split(" ");

			int id = Integer.parseInt(split[1]);
			int x = Integer.parseInt(split[2]);
			int y = Integer.parseInt(split[3]);

			System.out.println(id + " " + x + " " + y);

			for (Player player : players) {
				if (player.getID() == id) {
					otherPlayer.setX(x);
					otherPlayer.setY(y);
				}
			}
		} else if (command.startsWith("QUIT")) {
			String[] split = command.split(" ");
			int id = Integer.parseInt(split[1]);
			System.out.println(id);

			for (Player player : players) {
				if (player.getID() == id) {
					players.remove(player);
				}
			}
		} else if (command.startsWith("ENDE")) {
			System.out.println("BEENDET");
		} else if (command.startsWith("CHAT")) {
			System.out.println("JEMAND HAT ETWAS GESCHRIEBEN");
		}
	}

	private void sendePosition() {
		senden("POSITION " + player.getID() + " " + player.getX() + " "
				+ player.getY());
	}

	private void sendeChat(String message) {
		senden("CHAT " + message);
	}

	void senden(String message) {
		if (verbindung == null) {
			try {
				verbindung = new DatagramSocket();
				System.out.println("Verbindung erstellt!");
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		byte[] buf = new byte[256];
		InetAddress address = null;
		try {
			address = InetAddress.getByName(servername);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		buf = message.getBytes();
		packet = new DatagramPacket(buf, buf.length, address, port);
		try {
			verbindung.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (isRunning) {
			empfang();
		}
	}

}