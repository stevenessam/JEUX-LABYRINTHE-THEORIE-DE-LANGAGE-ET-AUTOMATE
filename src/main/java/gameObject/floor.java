package gameObject;

import ressourceManages.Textures;

public class floor extends gameObject{
	public floor(int x,int y) {
		super(x,y,Textures.Floor);
		setType(gameObjectType.PATH);
	}
}
