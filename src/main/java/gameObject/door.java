package gameObject;

import ressourceManages.Textures;

public class door extends gameObject{

	gameObjectType type = gameObjectType.SOLID;

	int key_x;
	int key_y;

	public door(int x,int y,int key_x,int key_y) {
		super(x,y);
		this.key_x = key_x;
		this.key_y = key_y;
		setSprite(Textures.Door,2,1);
	}

	@Override
	protected void update() {
		this.setFrame((this.frame+1)%2);
	}

}
