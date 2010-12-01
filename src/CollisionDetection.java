
public class CollisionDetection {
	
	// Collision with background
	public static boolean IsTileWalkable(int x, int y, int width, int height, int direction) {
		int firstPointX = 0;
		int firstPointY = 0;
		int secPointX = 0;
		int secPointY = 0;
		switch (direction) {
		// add 2 pixel to direction to get position of the next tile
		case 1:
			//up
			firstPointX = x + 3;	//  +3 for buffer
			firstPointY = y - 2; 	
			secPointX = x + width - 3;  // -3 for buffer
			secPointY = y - 2;		
			break;
		case 2:
			//right
			firstPointX = x + width + 2;
			firstPointY = y + 2;	// +2 for buffer
			secPointX = x + width + 2; 
			secPointY = y + height - 2;	// - 2 buffer
			break;
		case 3:
			//down
			firstPointX = x + 3;	// +3 buffer
			firstPointY = y + height + 2;
			secPointX = x + width - 3;	// -3 buffer	
			secPointY = y + height + 2;
			break;
		case 4:
			//left
			firstPointX = x - 2;
			firstPointY = y + 2;
			secPointX = x - 2;
			secPointY = y + height - 2;
			break;
		}
		// get tile number
		firstPointX = firstPointX / 32;		
		firstPointY = firstPointY / 32;
		secPointX =  secPointX / 32;
		secPointY = secPointY / 32;

		
		if (Game.blocked[firstPointX][firstPointY] || Game.blocked[secPointX][secPointY]) {
			return false;
		} else {
			return true;
		}
	}
}
