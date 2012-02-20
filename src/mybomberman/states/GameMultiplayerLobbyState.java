package mybomberman.states;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.Vector;

import mybomberman.Game;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMultiplayerLobbyState extends BasicGameState {

	private int id;

	String ANMELDUNG = "ANMELDUNG";
	String ENDE = "ENDE";
	int port = 1234;
	int length = 256; // Länge eines Pakets
	Vector<InetSocketAddress> clients;

	public GameMultiplayerLobbyState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame state)
			throws SlickException {
	}

	@Override
	public void render(GameContainer gc, StateBasedGame state, Graphics g)
			throws SlickException {
		g.setColor(Color.white);
		g.drawString("1 start server", 100, 75);
		g.drawString("2 join server", 100, 100);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame state, int arg2)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			state.enterState(Game.GAMEMENUSTATE);
		} else if (gc.getInput().isKeyPressed(Input.KEY_1)) {

			DatagramPacket paket = new DatagramPacket(new byte[length], length);
			clients = new Vector<InetSocketAddress>();
			try {
				DatagramSocket socket = new DatagramSocket(port);
				for (;;) {
					socket.receive(paket);
					InetSocketAddress add = (InetSocketAddress) paket
							.getSocketAddress();

					// Text aus Paket extrahieren
					String text = new String(paket.getData(), 0,
							paket.getLength());
					System.out.println(add + ">" + text);

					// Paket auswerten
					if (text.equals(ANMELDUNG)) {
						clients.add(add);
						System.out.println("Anzahl Clients: " + clients.size());
						for (int i = 0; i < clients.size(); i++) {
							InetSocketAddress dest = (InetSocketAddress) clients
									.get(i);
							if (!dest.equals(add)) {
								paket.setSocketAddress(dest);
								paket.setData("Test".getBytes());
								socket.send(paket);
								System.out.println("Kopie an " + dest);
							}
						}
					} else if (text.equals(ENDE)) {
						clients.remove(add);
						System.out.println("Anzahl Clients: " + clients.size());
					} else {
						// Versenden von Kopien an alle anderen Clients
						for (int i = 0; i < clients.size(); i++) {
							InetSocketAddress dest = (InetSocketAddress) clients
									.get(i);
							if (!dest.equals(add)) {
								paket.setSocketAddress(dest);
								socket.send(paket);
								System.out.println("Kopie an " + dest);
							}
						}
					}
				}
			} catch (IOException e) {
			}

		} else if (gc.getInput().isKeyPressed(Input.KEY_2)) {
			state.enterState(Game.GAMEMP);
		} else if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			state.enterState(Game.GAMEMENUSTATE);
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

}
