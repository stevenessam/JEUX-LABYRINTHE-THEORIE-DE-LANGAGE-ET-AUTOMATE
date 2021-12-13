package gameObject;

import ressourceManages.Textures;

public class wall extends gameObject {

    public wall(int x, int y){
        super(x,y);
        setSprite(Textures.wall,2,2);
    }

}
