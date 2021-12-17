package block;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;

import java.util.List;
import java.util.Optional;

public class Roof extends gameObject {
    public Roof(int x, int y) {
        super(x+.5,y+.5, Textures.roof);
        this.setDeltaX(.5);
        this.setDeltaY(1.5);
		setSprite(1, 3);
    }
    Player player;
    private boolean isplayerTouching(){
        List<gameObject> objects = this.getScene().getObjects(getX(),getY());
        Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
            return gameObjectType.PLAYER.equals(gO.getType());
        }).findFirst();
        boolean ispresent = OgO.isPresent();
        if(ispresent){
            player = (Player) OgO.get();
        }
        return ispresent;
    }
    public void effect(){
        player.addHealth(-1);
		setType(gameObjectType.SOLID);
    }
	boolean onfloor = false;
	boolean isfalling = false;
    @Override
    protected void update() {
		if(!onfloor){
			if(isplayerTouching() && this.getScene()!=null){
				isfalling = true;
	        }
			if(isfalling){
				if(getDeltaY()>.5){
					this.setDeltaY(getDeltaY()-.05);
					this.setFrame(getDeltaY()>1?0:1);
				}else{
					effect();
					this.setFrame(2);
					onfloor = true;
				}
			}
		}
    }
}
