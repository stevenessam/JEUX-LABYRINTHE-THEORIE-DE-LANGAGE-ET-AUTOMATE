package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;

public class floor extends gameObject{
	public floor(int x,int y) {
		super(x,y,Textures.Floor);
		setType(gameObjectType.PATH);
	}
}