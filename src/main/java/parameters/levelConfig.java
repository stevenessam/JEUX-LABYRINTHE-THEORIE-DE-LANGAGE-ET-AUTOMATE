package parameters;

import block.*;
import entity.*;
import gameObject.*;
import item.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import assets.Textures;

public class levelConfig{
	private optionConfig option;
	private levelMaker lM;
	public levelConfig(optionConfig option) {
		this.option = option;
		lM = new levelMaker();
		lM.setWidth(1000);
		lM.setHeight(660);
		lM.put("chest",(ls)->{
			lM.getScene().placeBlock(new Floor(0,0),(int)ls.get(0),(int)ls.get(1));
			return new Chest(0,0);
		});
		lM.put("floor",(ls)->{return new Floor(0,0);});
		lM.put("wall",(ls)->{return new Wall((int)ls.get(0),(int)ls.get(1));});
		lM.put("skeleton",(ls)->{return new Skeleton(0,0);});
		lM.put("bat",(ls)->{return new Bat(0,0);});
		lM.put("player",(ls)->{
			Player a = new Player(0,0);a.setControls(option.getPlayerControls(0));
			return a;
		});
	}
	public gameScene getLevelScene(){
		gameScene scene = new gameScene(1000,660);


		scene.setBackdrop(Textures.night_backdrop);
		// scene.add(new gameObject(0,0));

		KeyDoor key1 = new KeyDoor(2,1);
		stairs_item itemStair1 = new stairs_item(0,1);

		scene.placeBlock(new Floor(0,1));
		// scene.placeBlock(new floor(1,0));
		scene.placeBlock(new Floor(1,1),1,1);
		scene.placeBlock(new stairs_item(0,1));
		scene.placeBlock(new Floor(2,1));
		// scene.add(new floor(3,1));
		scene.placeBlock(new Ladder(3,1,itemStair1));
		scene.placeBlock(new Floor(4,1));
		scene.placeBlock(new Floor(5,1));
		scene.placeBlock(new Floor(6,1));
		scene.placeBlock(new Floor(7,1));
		scene.placeBlock(new Roof(2,1));


		scene.placeBlock(new SpiderWeb(4,1));
		scene.placeBlock(new SpiderWeb(5,1));

		scene.placeBlock(new Wall(3,0));
		scene.placeBlock(new Wall(4,0));
		scene.placeBlock(new Door(5,0,key1));
		scene.placeBlock(new Wall(6,0));
		scene.placeBlock(new Wall(7,0));
		scene.placeBlock(new Wall(8,0));

		scene.placeBlock(new potion(6,1));
		scene.placeBlock(new Bookshelf(2,2));
		scene.placeBlock(new Chest(8,1));



		scene.placeBlock(new Skeleton(4,0,
			new pattern(
				movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
				movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
				movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
				movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
				movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
				movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
				movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
				movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT
			)
		));


		scene.placeBlock(new Bat(2,0,
				new pattern(
						movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
						movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
						movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
						movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,movement.RIGHT,
						movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
						movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
						movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,
						movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT,movement.LEFT
				)
		));

		
		scene.placeBlock(key1);
		scene.placeBlock(itemStair1);

		Player a =new Player(1,1);
		a.setControls(option.getPlayerControls(0));
		scene.placeBlock(a);
		return scene;
	}
	public gameScene getLevelScene(int level) {
		String content = ReadeFile("/maps/level"+(level+1)+".map");
		// return getLevelScene();
		return lM.exec(content);
	}


	public String ReadeFile(String file) {

		InputStream config = optionConfig.class.getResourceAsStream(file);
		if (config != null) {
			StringBuilder sb = new StringBuilder();
			BufferedReader br = new BufferedReader(new InputStreamReader(config));
			String line;
			try {
				while ((line = br.readLine()) != null) {
					sb.append(line + System.lineSeparator());
				}
				return sb.toString();//.trim().replaceAll("[\n\t\r]", "");

			} catch (IOException e) {
				System.err.println("path Not find");
			}

		}else{
			System.err.println(file + "not existe");
		}
		return null;
	}
}

