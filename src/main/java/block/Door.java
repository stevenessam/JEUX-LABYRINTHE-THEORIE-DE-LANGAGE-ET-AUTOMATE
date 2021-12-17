package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import item.KeyDoor;

public class Door extends gameObject{

	gameObjectType type = gameObjectType.SOLID;

	KeyDoor key;

	public Door(int x, int y, KeyDoor key) {
		super(x,y);
		this.key = key;
		setSprite(Textures.Door,3,1);
	}

	@Override
	protected void update() {
		if(this.key.isPickup()){
			this.setFrame(1+((this.getTimer()/50)+1)%2);
		}else{
			this.setFrame(0);
		}
		
	}

}
