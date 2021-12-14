package gameObject;

import ressourceManages.Textures;

public class wall extends gameObject {

	public wall(int x, int y){
		super(x,y,x%2==0?Textures.wall:Textures.wall_lighted);
	}

}
