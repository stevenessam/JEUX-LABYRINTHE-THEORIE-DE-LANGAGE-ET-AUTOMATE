package parameters;

import gameObject.gameObject;
import gameObject.wall;
import gameObject.stairs;
import gameObject.stairs_item;
import gameObject.mob;
import gameObject.gameScene;
import gameObject.keyDoor;
import gameObject.movement;
import gameObject.pattern;
import gameObject.door;
import gameObject.floor;
import gameObject.potion;
import gameObject.player;
import ressourceManages.Textures;

public class levelConfig {
	private optionConfig option;
	public levelConfig(optionConfig option) {
		this.option = option;
	}
	public gameScene getLevelScene(){
		gameScene scene = new gameScene(1000,660);
		scene.setBackdrop(Textures.night_backdrop);
		// scene.add(new gameObject(0,0));




		keyDoor key1 = new keyDoor(2,1);
		stairs_item itemStair1 = new stairs_item(0,1);

		scene.placeBlock(new floor(0,1));
		// scene.placeBlock(new floor(1,0));
		scene.placeBlock(new floor(1,1),1,1);
		scene.placeBlock(new stairs_item(0,1));
		scene.placeBlock(new floor(2,1));
		// scene.add(new floor(3,1));
		scene.placeBlock(new stairs(3,1,itemStair1));
		scene.placeBlock(new floor(4,1));
		scene.placeBlock(new floor(5,1));
		scene.placeBlock(new floor(6,1));
		scene.placeBlock(new floor(7,1));

		scene.placeBlock(new wall(3,0));
		scene.placeBlock(new wall(4,0));
		scene.placeBlock(new door(5,0,key1));
		scene.placeBlock(new wall(6,0));
		scene.placeBlock(new wall(7,0));
		scene.placeBlock(new wall(8,0));

		scene.placeBlock(new potion(6,1));



		scene.placeBlock(new mob(4,0,
			new pattern(movement.DOWN,movement.RIGHT,movement.UP,movement.LEFT)
		));

		
		scene.placeBlock(key1);
		scene.placeBlock(itemStair1);

		player a =new player(1,1);
		a.setControls(option.getPlayerControls(0));
		scene.placeBlock(a);
		return scene;
	}
	public gameScene getLevelScene(int level) {
		return getLevelScene();
	}
}
