package mybomberman;

import mybomberman.states.GameMPState;
import mybomberman.states.GameMenuState;
import mybomberman.states.GameMultiplayerLobbyState;
import mybomberman.states.GameOptionState;
import mybomberman.states.GamePlayState;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

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
