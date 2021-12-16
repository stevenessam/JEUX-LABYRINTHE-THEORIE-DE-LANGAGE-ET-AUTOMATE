package gameObject;

import assets.Textures;

public class mob extends gameObject {
	private int speed = 5;
	private pattern pattern;
	public mob(int x, int y,pattern pattern) {
		super(x, y+.5);
		 this.pattern = pattern;
		setSprite(Textures.mob, 2, 1);
	}


	public void moves() {
		movement move_mob = pattern.next();
		switch (move_mob) {
		 	case DOWN:setY(getY()+speed);break;
		 	case LEFT:setX(getX()-speed);break;
		 	case RIGHT:setX(getX()+speed);break;
		 	case UP:setY(getY()-speed);break;
		 }
 	}
	@Override
	protected void update() {
		if(wait(50)){
			moves();
		}
		this.setFrame(((this.getTimer()/50)+1)%2);
	}
}

