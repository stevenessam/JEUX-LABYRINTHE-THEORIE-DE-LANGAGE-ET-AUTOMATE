package entity;

import gameObject.pattern;

public class Skeleton extends Mob{
	public Skeleton(int x, int y) {
		super(x,y);
	}
	public Skeleton(int x, int y,pattern pattern) {
		super(x, y, pattern);
	}
	private boolean canplayer = true;
	@Override
	protected void update() {
		super.update();
		if(isplayerTouching() && canplayer){
			player.addHealth(-1);
			canplayer = false;
		}
		if(!canplayer && this.wait(50)){
			canplayer = true;
		}
	}

}
