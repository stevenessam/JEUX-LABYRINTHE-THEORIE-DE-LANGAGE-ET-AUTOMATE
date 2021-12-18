package entity;

import gameObject.pattern;

public class Skeleton extends Mob{
	public Skeleton(int x, int y) {
		super(x,y);
	}
	public Skeleton(int x, int y,pattern pattern) {
		super(x, y, pattern);
	}
	protected double degat = 1;
	@Override
	protected void update() {
		super.update();
	}

}
