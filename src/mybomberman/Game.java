package mybomberman;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Game extends StateBasedGame {

	public static final int GAMEMENUSTATE = 0;
	public static final int GAMEPLAYSTATE = 1;

	public Game() {
		super("MyBomberman");

		this.addState(new GameMenuState(GAMEMENUSTATE));
		this.addState(new GamePlayState(GAMEPLAYSTATE));
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
	}

}
