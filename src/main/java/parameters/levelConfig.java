package parameters;

import gameObject.gameObject;
import gameObject.wall;
import gameObject.mob;
import gameObject.gameScene;
import gameObject.keyDoor;
import gameObject.movement;
import gameObject.pattern;
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

		scene.add(new wall(2,0));
		scene.add(new wall(3,0));
		scene.add(new wall(4,0));
		scene.add(new door(5,0,2,0));
		scene.add(new wall(6,0));
		scene.add(new wall(7,0));
		scene.add(new wall(8,0));

		scene.add(new mob(4,0,
			new pattern(movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT)
		));

		
		scene.add(new keyDoor(2,1));

		player a =new player(0,0);
		a.setControls(option.getPlayerControls(0));
		scene.add(a);
		return scene;
	}
}
