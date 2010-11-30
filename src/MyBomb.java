import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class MyBomb {

	private int x;
	private int y;
	private int time = 3000;
	private Image image;

	
	public MyBomb(int x, int y, String img_path) {
		this.x = x;
		this.y = y;
		
		loadImage(img_path);

	}
	
	public MyBomb(String img_path) {
		loadImage(img_path);
		
	}
	
	private void loadImage(String img_path) {
		try {
			image = new Image("res/bomb.png");
		} catch (SlickException e) {
			System.out.println("Exception: e");
		}
	}
	
	public Image getImage(){
		return image;
	}
	
}
