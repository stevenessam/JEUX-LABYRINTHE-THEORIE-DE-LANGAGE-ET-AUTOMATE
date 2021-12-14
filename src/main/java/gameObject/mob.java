package gameObject;

import ressourceManages.Textures;

public class mob extends gameObject {
	private int speed = 5;
	private pattern pattern;
	public mob(int x, int y,pattern pattern) {
		super(x, y);
		// this.pattern = pattern;
		setSprite(Textures.mob, 2, 1);
	}
	// public void DOWN() {
	// 	if (y < this.getScene().getCanvasHeight() - 1 && getNoCollision(x, y + 1)) {
	// 		y += speed;
	// 	}
	// }
	// public void LEFT() {
	// 	if (x > 0 && getNoCollision(x - 1, y)) {
	// 		x -= speed;
	// 	}
	// }
	// public void RIGHT() {
	// 	if (x < this.getScene().getCanvasWidth() - 1 && getNoCollision(x + 1, y)) {
	// 		x += speed;
	// 	}
	// }
	// public void UP() {
	// 	if (y > 0 && getNoCollision(x, y - 1)) {
	// 		y -= speed;
	// 	}
	// }
	public void moves() {
		// switch (pattern.next()) {
		// 	case DOWN:DOWN();break;
		// 	case LEFT:LEFT();break;
		// 	case RIGHT:RIGHT();break;
		// 	case UP:UP();break;
		// }
	}
	@Override
	protected void update() {
		// moves();
		this.setFrame(((this.getTimer()/50)+1)%2);
	}
}

