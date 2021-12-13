package parameters;

import gameObject.gameObject;
import gameObject.wall;
import gameObject.enemies;
import gameObject.gameScene;
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
		scene.add(new wall(300,0));
		scene.add(new enemies(100,0));
		player a =new player(0,0);
		a.setControls(option.getPlayerControls(0));
		scene.add(a);
		return scene;
	}
}
