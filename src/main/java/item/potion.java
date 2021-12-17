package item;

import java.util.List;
import java.util.Optional;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;

public class potion extends gameObject{
	public potion(int x,int y) {
		super(x+.5,y+.5,Textures.HeatlthPostion);
		this.setDeltaX(.5);
		this.setDeltaY(.5);
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
		System.out.println("apply effect");
		player.addHealth(2);
	}
	@Override
	protected void update() {
		if(isplayerTouching() && this.getScene()!=null){
			effect();
			delete();
		}
	}
}
