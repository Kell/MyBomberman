package mybomberman;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenuState extends BasicGameState {

	private int id;
	private Image background = null;

	public GameMenuState(int gamemenustate) {
		this.id = gamemenustate;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		background = new Image("res/bomb.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics arg2)
			throws SlickException {
		background.draw(0, 0);
	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int arg2)
			throws SlickException {

		if (container.getInput().isKeyPressed(Input.KEY_ENTER)) {
			arg1.enterState(Game.GAMEPLAYSTATE);
		} else if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		}

	}

	@Override
	public int getID() {
		return this.id;
	}

}
