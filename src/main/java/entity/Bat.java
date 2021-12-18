package entity;

import assets.Textures;
import gameObject.pattern;

public class Bat extends Mob {

	protected int speed = 15;
    private boolean canplayer = true;
	public Bat(int x, int y) {
		super(x, y);
        setSprite(Textures.bat, 1, 3);
    }
    public Bat(int x, int y,pattern pattern) {
		super(x, y, pattern);
        setSprite(Textures.bat, 1, 3);
    }

    @Override
    protected void update() {
        if(wait(15)){
			moves();
		}
		this.setFrame(((this.getTimer()/15)+1)%3);
        if(isplayerTouching() && canplayer){
            player.addHealth(-1);
            canplayer = false;
        }
        if(!canplayer && this.wait(50)){
            canplayer = true;
        }
    }


}
