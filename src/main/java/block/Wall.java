package block;

import assets.Textures;
import gameObject.gameObject;

/**
 * Class wall qui extends la class gameObject
 */
public class Wall extends gameObject {

	/**
	 *  int light_distance est la distance entre deux lumiere
	 */
	private final static int light_distance = 4;
	private boolean isLighted = false;

	/**
	 * Le constructeur de wall prend les positions x et y comme paramètres
	 * setSprite(col,row) le nombre de column et ligne pour decouper ces tiles
	 * @param x
	 * @param y
	 */
	public Wall(int x, int y){
		super(x,y,x%light_distance==0&&y%2==0?Textures.wall_lighted:Textures.wall);
		isLighted = x%light_distance==0&&y%2==0;//x%light_distance==0;
		if(isLighted){
			setSprite(3, 1);
		}
	}

	/**
	 * Update Override la methode Update dans la class gameObject
	 * La méthode Update permet de mettre à jour le mur
	 */
	@Override
	protected void update() {
		if(isLighted && wait(25)){
			int r = (int)(Math.random()*3);
			this.setFrame(((this.getTimer()/50)+r)%3);
		}
	}

}
