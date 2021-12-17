package entity;

import java.util.List;
import java.util.Optional;

import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.pattern;

public class Skeleton extends Mob{
	public Skeleton(int x, int y) {
		super(x,y);
	}
	public Skeleton(int x, int y,pattern pattern) {
		super(x, y, pattern);
	}
	private boolean canplayer = true;
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
	Player player;
	private boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX(),getY());
		Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst();
		// System.out.println(OgO);
		boolean ispresent = OgO.isPresent();
		if(ispresent){
			player = (Player) OgO.get();
		}
		return ispresent;
	}
}
