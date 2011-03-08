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
	private Image logo = null;
	private Image cursor = null;
	private Image singleplayer = null;
	private Image multiplayer = null;
	private Image options = null;
	private Image exit = null;
	private int cursor_y = 290;
	private int entry = 1;

	public GameMenuState(int gamemenustate) {
		this.id = gamemenustate;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		logo = new Image("res/logo.png");
		cursor = new Image("res/bomb_cursor.png");
		singleplayer = new Image("res/single_player.png");
		multiplayer = new Image("res/multiplayer.png");
		options = new Image("res/options.png");
		exit = new Image("res/exit.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame state, Graphics g)
			throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 1024, 768);
		logo.draw(290, 100);
		cursor.draw(230, cursor_y);
		// TODO: check which state called this class
		// ------ 
		singleplayer.draw(300, 285);
		multiplayer.draw(320, 360);
		// ------
		//TODO: resume game option 
		options.draw(380, 435);
		exit.draw(430, 515);	// different exit while playing
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame state, int delta)
			throws SlickException {

		if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
			checkMenuEntry(state);
		} else if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
			if (entry > 1) {
				entry--;
				cursor_y -= 80; 
			}
		} else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
			if (entry < 4 && entry >= 1) { 
				entry++;
				cursor_y += 80;
			}
		}  
	}

	@Override
	public int getID() {
		return this.id;
	}
	
	private  void checkMenuEntry(StateBasedGame state) {
		switch (entry) {
		case 1:
			state.enterState(Game.GAMEPLAYSTATE);
			break;
		case 2:
			state.enterState(Game.GAMEMPLOBBY);
			break;
		case 3:
			state.enterState(Game.GAMEOPTIONSTATE);
			break;
		case 4:
			System.exit(0);
			break;
		}
	}

}
