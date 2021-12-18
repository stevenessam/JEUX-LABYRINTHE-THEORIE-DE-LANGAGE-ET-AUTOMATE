package item;

import java.util.List;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import javafx.scene.canvas.GraphicsContext;

/**
 * La class KeyDoor extends gameObject
 */
public class KeyDoor extends gameObject{

	boolean pickup = false;
	public boolean isPickup() {
		return pickup;
	}

	/**
	 * Constructeur de la class KeyDoor prend comme parametre x et y
	 * @param x
	 * @param y
	 */
	public KeyDoor(int x, int y) {
		super(x+.5,y+.5,Textures.KeyDoor);
		this.setDeltaX(.5);
		this.setDeltaY(.5);
	}

	/**
	 * Méthode render a comme paramétre graphic qui est un variable de la class {@link GraphicsContext}
	 * @param graphic
	 */
	@Override
	public void render(GraphicsContext graphic) {
		if(!pickup)
			super.render(graphic);
	}
	/**
	 * test les elments avec les memes position que la potion et filtre ceux qui sont aussi joueur
	 * @return si un joueur touche la potion
	 */
	private boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX(),getY());
		return objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst().isPresent();
	}
	/**
	 * Methode update permet de metre a jour le KeyDoor quand c'est toucher par le player
	 */
	@Override
	protected void update() {
		if(!pickup){
			pickup = isplayerTouching();
		}
	}
}
