package elements;

import org.newdawn.slick.Image;

public class SpeedIncrease extends PowerUp{

	public SpeedIncrease(Image img) {
		super(img);
	}

	@Override
	public void takeEffect(Player p) {
		int cur_speed = p.getPowerUps().get("speed");
		p.getPowerUps().put("speed", cur_speed++);
	}
}
