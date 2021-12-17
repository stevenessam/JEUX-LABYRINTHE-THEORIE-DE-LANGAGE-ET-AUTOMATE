package block;

import assets.Textures;
import gameObject.gameObject;

public class Wall extends gameObject {
	private final static int light_distance = 4;
	private boolean isLighted = false;
	public Wall(int x, int y){
		super(x,y,x%light_distance==0&&y%2==0?Textures.wall_lighted:Textures.wall);
		isLighted = x%light_distance==0&&y%2==0;//x%light_distance==0;
		if(isLighted){
			setSprite(3, 1);
		}
	}
	@Override
	protected void update() {
		if(isLighted && wait(25)){
			int r = (int)(Math.random()*3);
			this.setFrame(((this.getTimer()/50)+r)%3);
		}
	}

}
