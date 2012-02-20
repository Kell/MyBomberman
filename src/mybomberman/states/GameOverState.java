package mybomberman.states;

import mybomberman.Game;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOverState extends BasicGameState {

	private int id;
	Image gameOverLabel = null;

	public GameOverState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame state)
			throws SlickException {
		gameOverLabel = new Image("res/game_over_label.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame state, Graphics g)
			throws SlickException {
		
		gameOverLabel.draw(gc.getWidth()/2 - gameOverLabel.getWidth()/2, gc.getHeight() / 2 - gameOverLabel.getHeight()/2);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame state, int delta)
			throws SlickException {
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			state.enterState(Game.GAMEMENUSTATE);
		}
	}

	@Override
	public int getID() {
		return this.id;
	}

}
