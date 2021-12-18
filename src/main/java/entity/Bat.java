package entity;

import assets.Textures;
import gameObject.pattern;

/**
 * Class Bat qui extends la class gameObject
 */

public class Bat extends Mob {

	protected int speed = 15;
    protected double degat = .5;

    /**
     * La méthode public Bat (int X, int Y); setSprite(col,row)
     * le nombre de column et ligne pour decouper ces tiles
     * @param x
     * @param y
     */
	public Bat(int x, int y) {
		super(x, y);
        setSprite(Textures.bat, 1, 3);
    }

    /**
     * public Bat(int x, int y,pattern pattern);
     * setSprite c'est pour definir la textures et le nombre de column et ligne pour decouper ces tiles
     * @param x
     * @param y
     * @param pattern
     */
    public Bat(int x, int y,pattern pattern) {
		super(x, y, pattern);
        setSprite(Textures.bat, 1, 3);
    }

    /**
     *  Update Override la methode Update dans la class Mob
     *  La méthode Update permet de mettre à jour le Bat
     */
    @Override
    protected void update() {
        super.update();
		this.setFrame(((this.getTimer()/15)+1)%3);
        
    }


}
