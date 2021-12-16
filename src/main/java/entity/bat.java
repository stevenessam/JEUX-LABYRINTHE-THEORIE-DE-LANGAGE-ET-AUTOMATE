package entity;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.pattern;

import java.util.List;
import java.util.Optional;

public class bat extends mob {


    private boolean canplayer = true;
	public bat(int x, int y) {
        super(x, y);
        setSprite(Textures.bat, 2, 1);
    }
    public bat(int x, int y,pattern pattern) {
        super(x, y, pattern);
        setSprite(Textures.bat, 2, 1);
    }

    @Override
    protected void update() {
        super.update();
        if(isplayerTouching() && canplayer){
            player.addHealth(-1);
            canplayer = false;
        }
        if(!canplayer && this.wait(50)){
            canplayer = true;
        }
    }
    player player;
    private boolean isplayerTouching(){
        List<gameObject> objects = this.getScene().getObjects(getX(),getY());
        Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
            return gameObjectType.PLAYER.equals(gO.getType());
        }).findFirst();
        // System.out.println(OgO);
        boolean ispresent = OgO.isPresent();
        if(ispresent){
            player = (player) OgO.get();
        }
        return ispresent;
    }

}
