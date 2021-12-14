package gameObject;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import ressourceManages.Textures;

public class keyDoor extends gameObject{

	boolean pickup = false;
	public boolean isPickup() {
		return pickup;
	}

	public keyDoor(int x,int y) {
		super(x+.5,y+.5,Textures.KeyDoor);
		this.setDeltaX(.5);
		this.setDeltaY(.5);
	}

	@Override
	public void render(GraphicsContext graphic) {
		if(!pickup)
			super.render(graphic);
	}
	private boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX(),getY());
		return objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst().isPresent();
	}
	@Override
	protected void update() {
		if(!pickup){
			pickup = isplayerTouching();
		}
	}
}
