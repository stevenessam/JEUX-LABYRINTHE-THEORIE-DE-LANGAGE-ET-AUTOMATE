package gameObject;

import ressourceManages.Textures;

public class stairs extends gameObject {

    stairs_item itemStair;
    public stairs(int x, int y,stairs_item itemStair) {
        super(x, y);
        this.itemStair=itemStair;
        setSprite(Textures.stairs, 1, 1);

    }

    @Override
    protected void update() {
        if(this.itemStair.isPickup()){
            this.setFrame(1+((this.getTimer()/50)+1)%2);
            setType(gameObjectType.PATH);
        }else{
            this.setFrame(0);
        }

    }


}
