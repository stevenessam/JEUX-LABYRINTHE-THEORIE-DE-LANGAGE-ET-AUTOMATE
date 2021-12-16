package gameObject;

import java.util.List;
import java.util.Optional;

public class skeleton extends mob{
	public skeleton(int x, int y,pattern pattern) {
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
