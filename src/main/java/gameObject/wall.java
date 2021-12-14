package gameObject;

import ressourceManages.Textures;

public class wall extends gameObject {

	private boolean isLighted = false;
	public wall(int x, int y){
		super(x,y,x%2==0?Textures.wall:Textures.wall_lighted);
		isLighted = x%2!=0;
		if(isLighted){
			setSprite(3, 1);
		}
	}
	@Override
	protected void update() {
		if(isLighted && (this.getTimer()/50)/3 == 1){
			int r = (int)(Math.random()*3);
			this.setFrame(((this.getTimer()/50)+r)%3);
		}
	}

}
