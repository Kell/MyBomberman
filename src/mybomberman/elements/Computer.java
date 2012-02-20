package mybomberman.elements;


public class Computer extends Player {

	public Computer(int id) {
		super(id);
	}

	public Computer(String img, int x, int y) {
		super(img, x, y, 5);
	}
	
	private void checkAmbit() {
	}
}
