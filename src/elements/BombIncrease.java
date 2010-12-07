package elements;

import org.newdawn.slick.Image;

public class BombIncrease extends PowerUp {

	public BombIncrease(Image img) {
		super(img);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void takeEffect(Player p) {
		int bombs = p.getPowerUps().get("bombs");
		p.getPowerUps().put("bombs", bombs++);
	}
}
