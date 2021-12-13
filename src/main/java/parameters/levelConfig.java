package parameters;

import gameObject.gameObject;
import gameObject.gameScene;
import gameObject.keyDoor;
import gameObject.door;
import gameObject.player;
import ressourceManages.Textures;

public class levelConfig {
	private optionConfig option;
	public levelConfig(optionConfig option) {
		this.option = option;
	}
	public gameScene getLevelScene(){
		gameScene scene = new gameScene(480,360);
		scene.setBackdrop(Textures.night_backdrop);
		scene.add(new gameObject(0,0));
		scene.add(new door(1,0,2,0));
		scene.add(new keyDoor(2,0));
		player a =new player(0,0);
		a.setControls(option.getPlayerControls(0));
		scene.add(a);
		return scene;
	}
}
