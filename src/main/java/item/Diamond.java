package item;

import java.util.List;
import java.util.Optional;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;

public class Diamond extends gameObject{
	/**
	 * placer une potion de Soin
	 * @param x
	 * @param y
	 */
	public Diamond(int x,int y) {
		super(x+.5,y+.5,Textures.Stats);
		this.setDeltaX(.5);
		this.setDeltaY(.5);
		setSprite(3, 2);
	}
	/**
	 * garde le player toucher pour lui affecter un effet
	 */
	Player player;
	/**
	 * test les elments avec les memes position que la potion et filtre ceux qui sont aussi joueur
	 * @return si un joueur touche la potion
	 */
	private boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX(),getY());
		Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst();
		boolean ispresent = OgO.isPresent();
		if(ispresent){
			player = (Player) OgO.get();
		}
		return ispresent;
	}
	/**
	 * ajoute une etoile au joueur
	 */
	public void effect(){
		player.addStar();
	}
	/**
	 * mise à jour de la potion
	 * quand le joueur est toucher 
	 * (et que la potion est dans un scene (pour povoir etre retire de cette scene))
	 * appliquer l'effect {@link effect}
	 * puis retire l'element de la scene {@link gameScene}
	 */
	@Override
	protected void update() {
		if(isplayerTouching() && this.getScene()!=null){
			effect();
			delete();
		}
		setFrame(2+((this.getTimer()/50)+1)%3);
	}
}
