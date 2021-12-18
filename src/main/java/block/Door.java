package block;

import java.util.List;
import java.util.Optional;

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
	 * 
	 * @return clé de la porte
	 */
	public KeyDoor getKey() {
		return key;
	}

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
		setSprite(Textures.Door,3,2);
		setType(gameObjectType.SOLID);
	}

	public Door(int x, int y, int kx, int ky) {
		this(x, y, new KeyDoor(kx, ky));
	}
	/** */
	private boolean isopen = false;
	/**
	 * Update Override la methode Update dans la class gameObject
	 * La méthode Update permet de mettre à jour le Door
	 */
	@Override
	protected void update() {
		if(this.key.isPickup()){
			if(isopen){
				if(isendDoor && isplayerTouching()){
					System.out.println("change level");
					this.getScene().nextLevel();
					return;
				}else{
					setType(gameObjectType.PATH);
				}
			}else{
				this.setFrame(1+((this.getTimer()/50)+1)%6);
				if(this.frame==5){
					isopen = true;
				}
			}
			
		}else{
			this.setFrame(0);
		}
		
	}
	private boolean isendDoor = false;
	public void setEndDoor(){
		isendDoor = true;
	}
	private boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX()+getRenderWidth()/2,getY());
		Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst();
		boolean ispresent = OgO.isPresent();
		if(ispresent)
			System.out.println("touche player");
		return ispresent;
	}
}
