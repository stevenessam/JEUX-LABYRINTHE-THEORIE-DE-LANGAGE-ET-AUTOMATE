package item;

import javafx.scene.canvas.GraphicsContext;

import java.util.List;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;

/**
 * Class Ladder_item qui extends la class gameObject
 */

public class Ladder_item extends gameObject{


    boolean pickup = false;
    public boolean isPickup() {
        return pickup;
    }

    /**
     * Ladder_item est le Constructeur  qui a comme parametre x et y
     * @param x
     * @param y
     */
    public Ladder_item(int x,int y) {
        super(x+.5,y+.5,Textures.stairs_item);
        setSprite(Textures.stairs_item, 1, 1);
        this.setDeltaX(.5);
        this.setDeltaY(.5);

    }

    /**
     * Methode render a comme parametre graphic qui est un variable de la class {@link GraphicsContext}
     * @param graphic
     */
    @Override
    public void render(GraphicsContext graphic) {
        if(!pickup)
            super.render(graphic);
    }

    /**
     * La méthode isplayerTouching permet de verifier si le player a toucher le Ladder_item
     * @return
     */
    private boolean isplayerTouching(){
        List<gameObject> objects = this.getScene().getObjects(getX(),getY());
        return objects.stream().filter((gameObject gO)->{
            return gameObjectType.PLAYER.equals(gO.getType());
        }).findFirst().isPresent();
    }

    /**
     * Methode update permet de metre a jour le Ladder_item quand c'est toucher par le player
     */
    @Override
    protected void update() {
        if(!pickup){
            pickup = isplayerTouching();
        }
    }

}
