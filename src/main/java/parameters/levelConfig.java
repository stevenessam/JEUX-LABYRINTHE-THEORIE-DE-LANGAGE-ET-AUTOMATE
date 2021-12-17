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
			return new chest(0,0);
		});
		lM.put("floor",(ls)->{return new floor(0,0);});
		lM.put("wall",(ls)->{return new wall((int)ls.get(0),(int)ls.get(1));});
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
		scene.placeBlock(new roof(2,1));


		scene.placeBlock(new spiderWeb(4,1));
		scene.placeBlock(new spiderWeb(5,1));

		scene.placeBlock(new wall(3,0));
		scene.placeBlock(new wall(4,0));
		scene.placeBlock(new door(5,0,key1));
		scene.placeBlock(new wall(6,0));
		scene.placeBlock(new wall(7,0));
		scene.placeBlock(new wall(8,0));

		scene.placeBlock(new potion(6,1));
		scene.placeBlock(new chest(8,1));



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
		// String content = ReadeFile("/maps/level"+(level+1)+".map");
		return getLevelScene();
		// return lM.exec(content);
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

