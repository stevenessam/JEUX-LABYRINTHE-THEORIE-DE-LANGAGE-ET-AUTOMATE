package entity;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.pattern;

import java.util.List;
import java.util.Optional;

public class bat extends mob {

	protected int speed = 15;
    private boolean canplayer = true;
	public bat(int x, int y) {
        super(x, y);
        setSprite(Textures.bat, 1, 3);
    }
    public bat(int x, int y,pattern pattern) {
        super(x, y, pattern);
        setSprite(Textures.bat, 1, 3);
    }

    @Override
    protected void update() {
        if(wait(15)){
			moves();
		}
		this.setFrame(((this.getTimer()/15)+1)%3);
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
