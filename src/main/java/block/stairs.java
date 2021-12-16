package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import item.stairs_item;
import javafx.scene.canvas.GraphicsContext;

public class stairs extends gameObject {
    boolean pickup = false;

    stairs_item itemStair;
    public stairs(int x, int y,stairs_item itemStair) {
        super(x, y+.5);
        this.itemStair=itemStair;
        setSprite(Textures.stairs, 1, 1);
		setDeltaY(.25);
    }

    @Override
    public void render(GraphicsContext graphic) {
        if(this.itemStair.isPickup()) {
            super.render(graphic);
            setType(gameObjectType.PATH);
        }
    }

    @Override
    protected void update() {
        if(this.itemStair.isPickup()){
            this.setFrame(1+((this.getTimer()/50)+1)%2);

        }else{
            this.setFrame(0);
        }

    }


}
