package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
/**
 * Class Floor qui extends la class gameObject
 */
public class Floor extends gameObject{
	/**
	 *Le constructeur de Floor prend les positions x et y comme paramètres
	 * et un setType( gameObjectType.PATH ) qui permet le player de passer sur le Floor
	 * @param x
	 * @param y
	 */
	public Floor(int x, int y) {
		super(x,y,Textures.Floor);
		setType(gameObjectType.PATH);
	}
}
