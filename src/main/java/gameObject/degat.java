package gameObject;

import assets.Textures;

import java.util.List;
import java.util.Optional;

public class degat extends gameObject {
    public degat(int x,int y) {
        super(x+.5,y+.5, Textures.roof);
        this.setDeltaX(.5);
        this.setDeltaY(1.5);
    }
    player player;
    private boolean isplayerTouching(){
        List<gameObject> objects = this.getScene().getObjects(getX(),getY());
        Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
            return gameObjectType.PLAYER.equals(gO.getType());
        }).findFirst();
        boolean ispresent = OgO.isPresent();
        if(ispresent){
            player = (player) OgO.get();
        }
        return ispresent;
    }
    public void effect(){
        System.out.println("apply effect");
        player.addHealth(-1);
    }
    @Override
    protected void update() {
        if(isplayerTouching() && this.getScene()!=null){
            setDeltaY(.5);
            effect();
            delete();
        }
    }
}
