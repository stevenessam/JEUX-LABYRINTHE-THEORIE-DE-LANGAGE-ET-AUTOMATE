package entity;

import assets.Textures;
import gameObject.pattern;

public class Bat extends Mob {

	protected int speed = 15;
    protected double degat = .5;
	
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
        super.update();
		this.setFrame(((this.getTimer()/15)+1)%3);
        
    }


}
