package block;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import item.Ladder_item;
import javafx.scene.canvas.GraphicsContext;

/**
 * Class Ladder qui extends la class gameObject
 */
public class Ladder extends gameObject {
    /**
     * pickup est un Variable boolean = false
     * itemStair est Variable du class stairs_item
     */
    boolean pickup = false;
    Ladder_item itemStair;

    /**
     * Le costucteur Ladder prend les positions x , y  et itemStair comme paramètres
     * dans le constructeur il y a un setSprite c'est pour definir la textures et
     * le nombre de column et ligne pour decouper ces tiles
     * @param x
     * @param y
     * @param itemStair
     */
    public Ladder(int x, int y, Ladder_item itemStair) {
        super(x, y+.5);
        this.itemStair=itemStair;
        setSprite(Textures.stairs, 1, 1);
		setDeltaY(.25);
    }

    /**
     * La methode render prend graphic comme paramètres qui est un varianble du class GraphicsContext
      * @param graphic
     */
    @Override
    public void render(GraphicsContext graphic) {
        if(this.itemStair.isPickup()) {
            super.render(graphic);
            setType(gameObjectType.PATH);
        }
    }

    /**
     * Update Override la methode Update dans la class gameObject
     * La méthode Update permet de mettre à jour le Ladder
     */
    @Override
    protected void update() {
        if(this.itemStair.isPickup()){
            this.setFrame(1+((this.getTimer()/50)+1)%2);

        }else{
            this.setFrame(0);
        }

    }


}
