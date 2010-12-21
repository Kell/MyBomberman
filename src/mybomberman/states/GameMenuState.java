package mybomberman.states;

import mybomberman.Game;

import org.newdawn.slick.Color;
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
		background = new Image("res/main_background.png");
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g)
			throws SlickException {
		 background.draw(0, 0);

		g.setColor(Color.red);
		g.drawString("( 1 ) Start Game", 400, 350);
		g.drawString("( 2 ) Options", 400, 370);
		g.drawString("( 3 ) Multiplayer Lobby", 400, 390);
		g.drawString("( ESC ) Exit ", 400, 410);

	}

	@Override
	public void update(GameContainer container, StateBasedGame arg1, int delta)
			throws SlickException {

		if (container.getInput().isKeyPressed(Input.KEY_1)) {
			arg1.enterState(Game.GAMEPLAYSTATE);
		} else if (container.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			System.exit(0);
		} else if (container.getInput().isKeyPressed(Input.KEY_3)) {
			arg1.enterState(Game.GAMEMPLOBBY);
		} else if (container.getInput().isKeyPressed(Input.KEY_2)) {
			arg1.enterState(Game.GAMEOPTIONSTATE);
		}

	}

	@Override
	public int getID() {
		return this.id;
	}

}
