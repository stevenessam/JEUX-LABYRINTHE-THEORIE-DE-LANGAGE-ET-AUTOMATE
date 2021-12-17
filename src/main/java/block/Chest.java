package block;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;
import item.Diamond;
import item.HealthPotion;

import java.util.List;
import java.util.Optional;

public class Chest extends gameObject {
	public Chest(int x, int y) {
		super(x+.5,y+.5, Textures.chest);
		setDeltaX(.5);
		setDeltaY(.5);
		setSprite(1, 4);
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
	private boolean isopen = false;
	@Override
	protected void update() {
		if(isplayerTouching() && this.getScene()!=null){
			isopen = true;
		}
		if(isopen){
			if(this.frame<3){
				setFrame(((this.getTimer()/15)+1)%4);
			}else{
				popRandomItem();
				delete();
			}
		}
	}
	private void popRandomItem() {
		gameObject gO = null;
		int random = (int)(Math.random()*2);
		switch (random){
			case 0:gO = new HealthPotion((int)getX(), (int)getY());break;
			case 1:gO = new Diamond((int)getX(), (int)getY());break;
		}
		if(gO!=null){
			this.getScene().add(gO);
		}
	}
}
