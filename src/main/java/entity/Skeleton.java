package entity;

import gameObject.pattern;

/**
 * Class Skeleton qui extends la class gameObject
 */
public class Skeleton extends Mob{
	public Skeleton(int x, int y) {
		super(x,y);
	}

	/**
	 * Constructeur de la class {@link Skeleton} prend comme parametre x , y et pattern
	 * @param x
	 * @param y
	 * @param pattern
	 */
	public Skeleton(int x, int y,pattern pattern) {
		super(x, y, pattern);
	}
	protected double degat = 1;

	/**
	 * Methode update permet de metre a jour le skeleton
	 */
	@Override
	protected void update() {
		super.update();
	}

}
