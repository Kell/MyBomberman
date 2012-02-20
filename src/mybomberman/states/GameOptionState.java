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

public class GameOptionState extends BasicGameState {

	private int id;
	private Image cursor = null;
	private Image sfx_label = null;
	private Image music_label = null;
	private Image cls_label = null;
	private Image mainmenu_label = null;
	private Image save_label = null;
	private Image background = null;
	private int cursor_y = 70;

	public GameOptionState(int id) {
		this.id = id;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame state)
			throws SlickException {
		
		background = new Image("res/main_background.png");
		sfx_label = new Image("res/sfx_label.png");
		music_label = new Image("res/music_label.png");
		cls_label = new Image("res/controls_label.png");
		mainmenu_label = new Image("res/mainmenu_label.png");
		save_label = new Image("res/save_label.png");
		cursor = new Image("res/bomb_cursor.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame state, Graphics g)
			throws SlickException {

		background.draw(0, 0);
		cursor.draw(20, cursor_y);
		
		sfx_label.draw(100, 100);
		music_label.draw(100, 150);
		cls_label.draw(100, 200);
		mainmenu_label.draw(100, 500);
		save_label.draw(800, 500);
		
//		g.setColor(Color.white);
//		g.drawString("SFX", 100, 100);
//		g.drawString("MUSIC", 100, 150);
//		g.drawString("CONTROLLS", 100, 200);
//		g.drawString("BACK TO MAIN MENU", 100, 500);
//		g.drawString("SAVE", 400, 500);
		
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
