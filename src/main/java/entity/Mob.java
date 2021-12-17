package entity;

import assets.Textures;
import game.Game;
import gameObject.gameObject;
import gameObject.movement;
import gameObject.pattern;

public class Mob extends gameObject {
	protected int speed = 5;
	protected int init_X = 0;
	protected int init_Y = 0;
	public void setInitX(int init_X) {
		this.init_X = (int)(init_X*Game.BLOCK_WIDTH*this.getScene().getScale());
	}
	public void setInitY(int init_Y) {
		this.init_Y = (int)(init_Y*Game.BLOCK_HEIGHT*this.getScene().getScale());
	}
	private pattern pattern;
	public void setPattern(pattern pattern) {
		this.pattern = pattern;
	}
	public Mob(int x, int y) {
		super(x,y+.5);
		init_X = x;
		init_Y = y;
		this.pattern = new pattern();
		setSprite(Textures.mob, 2, 1);
	}
	public Mob(int x, int y,pattern pattern) {
		super(x, y+.5);
		this.pattern = pattern;
		init_X = x;
		init_Y = y;
		setSprite(Textures.mob, 2, 1);
	}

	movement move_mob = movement.RESTART;
	public void moves() {
		if(this.wait(50))
			move_mob =pattern.next();
		switch (move_mob) {
		 	case DOWN:setY(getY()+speed);break;
		 	case LEFT:
				setX(getX()-speed);
				flipH(true);
				break;
		 	case RIGHT:
				flipH(false);
				setX(getX()+speed);
				break;
		 	case UP:setY(getY()-speed);break;
		 	case RESTART:
				setX(init_X);
				setY(init_Y);
				break;
		 }
 	}
	@Override
	protected void update() {
		if(wait(50)){
			moves();
		}
	}
}

