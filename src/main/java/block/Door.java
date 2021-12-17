package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import item.KeyDoor;
/**
 * Class Door qui extends la class gameObject
 */
public class Door extends gameObject{
	/**
	 * gameObjectType type permet d'activer les colision ave l'element
	 */
	gameObjectType type = gameObjectType.SOLID;

	/**
	 * key un variable de la class {@link KeyDoor}
	 */
	KeyDoor key;

	/**
	 * Constructeur de la class {@link Door} qui a comme parametre x , y et  key
	 * et setSprite c'est pour definir la textures et le nombre de column et ligne pour decouper ces tiles
	 * @param x
	 * @param y
	 * @param key
	 */
	public Door(int x, int y, KeyDoor key) {
		super(x,y);
		this.key = key;
		setSprite(Textures.Door,3,1);
	}

	/**
	 * Update Override la methode Update dans la class gameObject
	 * La méthode Update permet de mettre à jour le Door
	 */
	@Override
	protected void update() {
		if(this.key.isPickup()){
			this.setFrame(1+((this.getTimer()/50)+1)%2);
		}else{
			this.setFrame(0);
		}
		
	}

}
