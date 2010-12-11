package mybomberman;

import mybomberman.elements.Bomb;
import mybomberman.elements.Player;
import mybomberman.elements.powerups.AbstractPowerUp;

import org.newdawn.slick.Input;

public class GameControls {

	private int up, down, left, right, action, action2;
	private Player player = null;

	public GameControls(Player player, int keyLeft, int keyRight, int keyUp,
			int keyDown, int keySpace, int keyQ) {
		this.player = player;
		this.up = keyUp;
		this.down = keyDown;
		this.left = keyLeft;
		this.right = keyRight;
		this.action = keySpace;
		this.action2 = keyQ;
	}

	public void handleInput(Input input) {
		if (input.isKeyDown(getLeft())) {
			player.setMovementLEFT();
		} else if (input.isKeyDown(getRight())) {
			player.setMovementRIGHT();
		} else if (input.isKeyDown(getUp())) {
			player.setMovementUP();
		} else if (input.isKeyDown(getDown())) {
			player.setMovementDOWN();
		} else if (input.isKeyPressed(getAction())) {
			player.addBomb(new Bomb(player.getX(), player.getY(), player));
		} else if (input.isKeyPressed(getAction2())) {
			player.setBombCount(player.getBombCount() + 1);
			System.out.println(player.getBombCount());
			for (AbstractPowerUp item : player.getPowerUps()) {
				item.takeEffect(player);
			}
		}
	}

	public void setAction(int action) {
		this.action = action;
	}

	public void setAction2(int action2) {
		this.action2 = action2;
	}

	public void setDown(int down) {
		this.down = down;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public void setUp(int up) {
		this.up = up;
	}

	public int getAction() {
		return action;
	}

	public int getAction2() {
		return action2;
	}

	public int getDown() {
		return down;
	}

	public int getLeft() {
		return left;
	}

	public int getRight() {
		return right;
	}

	public int getUp() {
		return up;
	}

}
