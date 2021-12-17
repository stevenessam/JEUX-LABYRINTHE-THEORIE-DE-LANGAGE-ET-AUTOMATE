package block;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;

import java.util.List;
import java.util.Optional;

public class chest extends gameObject {
	public chest(int x,int y) {
		super(x+.5,y+.5, Textures.chest);
		this.setDeltaX(.5);
		this.setDeltaY(1.5);
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
	@Override
	protected void update() {
		if(isplayerTouching() && this.getScene()!=null){
			setDeltaY(.5);
			setFrame(((this.getTimer()/15)+1)%3);
		}
	}
}
