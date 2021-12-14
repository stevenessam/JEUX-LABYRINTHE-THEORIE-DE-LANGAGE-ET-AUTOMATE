package gameObject;

import ressourceManages.Textures;

public class door extends gameObject{

	gameObjectType type = gameObjectType.SOLID;

	keyDoor key;

	public door(int x,int y,keyDoor key) {
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
