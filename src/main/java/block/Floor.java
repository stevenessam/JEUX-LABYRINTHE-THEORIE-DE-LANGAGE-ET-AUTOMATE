package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;

public class Floor extends gameObject{
	public Floor(int x, int y) {
		super(x,y,Textures.Floor);
		setType(gameObjectType.PATH);
	}
}
