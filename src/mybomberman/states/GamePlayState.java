package mybomberman.states;

import java.util.ArrayList;

import mybomberman.Game;
import mybomberman.GameControls;
import mybomberman.elements.Player;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GamePlayState extends BasicGameState {
	public static TiledMap map;

	public static boolean blocked[][];
	private int id;
	private Player player, player2;
	private GameControls playerControls, playerControls2;
	public boolean isPaused = false;
	public boolean drawItemBar = false;
	
	
	private Image itemBar = null;
	private Image cursor = null;
	private Image pauseLabelImg = null;
	private Image resumeLabelImg = null;
	private Image restartLabelImg = null;
	private Image exitLabelImg = null;
	
	private int cursor_y = 260;
	private int pauseMenuEntry = 1;

	public static ArrayList<Player> players = null;

	public GamePlayState(int gameplaystate) {
		this.id = gameplaystate;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame state)
			throws SlickException {
		gc.setVSync(true);
		gc.setTargetFrameRate(60);
		map = new TiledMap("res/map.tmx");
		
		itemBar = new Image("res/item_bar.png");
		cursor = new Image("res/bomb_cursor.png");
		pauseLabelImg = new Image("res/pause_label.png");
		resumeLabelImg = new Image("res/resume_label.png");
		restartLabelImg = new Image("res/restart_label.png");
		exitLabelImg = new Image("res/exit_label.png");

		isPaused = false;
		players = new ArrayList<Player>();

		player = new Player("res/figure.png", 64, 64, 1);
		players.add(player);
		
		player2 = new Player("res/figure2.png", 14 * 64, 64, 2);
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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		map.render(0, 0);

		g.setColor(Color.white);
		for (Player player : players) {
			player.render(gc, g);
			
			if(player.getID() == 1)
				g.drawString("P1 LIVES: "+player.getLives(), 100, 40);
			else if(player.getID() == 2)
				g.drawString("P2 LIVES: "+player.getLives(), 600, 40);
			
			if (player.getLives() == 0)
				sbg.enterState(Game.GAMEOVERSTATE);
		}
		
		
		
		if(drawItemBar)
			drawItemBar(gc, sbg, g);
		
		
		if(isPaused)
			drawPauseMenu(gc, sbg, g);
			
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		

		if(!isPaused)
		{
			playerControls.handleInput(gc.getInput());
			playerControls2.handleInput(gc.getInput());
			
			for (Player player : players) {
				player.update(gc, delta);
			}
		}
		
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			if(!isPaused)
				isPaused = true;
			else
				isPaused = false;
		}
		
		if (gc.getInput().isKeyPressed(Input.KEY_I)) {
			if(!drawItemBar)
				drawItemBar = true;
			else
				drawItemBar = false;
		}
		
		if(isPaused) {
			
			if (gc.getInput().isKeyPressed(Input.KEY_ENTER)) {
				checkPauseMenuEntry(gc, sbg);
			} else if (gc.getInput().isKeyPressed(Input.KEY_UP)) {
				if (pauseMenuEntry > 1) {
					pauseMenuEntry--;
					cursor_y -= 60; 
				}
			} else if (gc.getInput().isKeyPressed(Input.KEY_DOWN)) {
				if (pauseMenuEntry < 3 && pauseMenuEntry >= 1) { 
					pauseMenuEntry++;
					cursor_y += 60;
				}
			} 
		}

	}

	@Override
	public int getID() {
		return this.id;
	}
	
	
	
	public void drawPauseMenu(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		int gcWidth = gc.getWidth(); 
		int gcHeight = gc.getHeight(); 
		
		Color trans = new Color(0f,0f,0f,0.5f);
        g.setColor(trans);
        g.fillRect(0,0, gcWidth, gcHeight);
        
        cursor.draw(230, cursor_y);
        
        pauseLabelImg.draw((gcWidth / 2)-(pauseLabelImg.getWidth()/2), 160);
        resumeLabelImg.draw((gcWidth / 2)-(resumeLabelImg.getWidth()/2), 280);
        restartLabelImg.draw((gcWidth / 2)-(restartLabelImg.getWidth()/2), 340);
        exitLabelImg.draw((gcWidth / 2)-(exitLabelImg.getWidth()/2), 400);
	}
	
	
	public void checkPauseMenuEntry(GameContainer gc, StateBasedGame sbg) {
		switch (pauseMenuEntry) {
		case 1:
			isPaused = false;
			break;
		case 2:
			try {
				sbg.init(gc);
			} catch (SlickException e) {
				e.printStackTrace();
			}
			sbg.enterState(Game.GAMEPLAYSTATE);
			break;
		case 3:
			sbg.enterState(Game.GAMEMENUSTATE);
			break;
		}
	}
	
	public void drawItemBar(GameContainer gc, StateBasedGame sbg, Graphics g) {
		int itemBarX = gc.getWidth() / 2 - itemBar.getWidth() / 2;
		int itemBarY = gc.getHeight() - 64;
		
		itemBar.draw( itemBarX, itemBarY );
		
		int slotCounterX = itemBarX + 11;
		for(int i = 1; i<=10; i++) {
			g.drawString(String.valueOf(i), slotCounterX, itemBarY + 7);
			slotCounterX += 62;
		}
	}
	
}