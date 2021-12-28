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

/**
 * La class levelConfig permet de paramétre les niveaux
 */

public class levelConfig{
	private optionConfig option;
	private levelMaker lM;

	/**
	 *
	 * @param option
	 */
	public levelConfig(optionConfig option) {
		this.option = option;
		System.out.println(option);//evite l'erreur option == undefined
		lM = new levelMaker();
		lM.setWidth(1000);
		lM.setHeight(660);
		lM.put("end",(ls)->{
			Door door;
			if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
				door = new Door((int)ls.get(0),(int)ls.get(1),(int)ls.get(2),(int)ls.get(3));
				lM.getScene().placeBlock(door);
				lM.getScene().placeBlock(door.getKey());
			}else{
				door = new Door(0,0,(int)ls.get(0),(int)ls.get(1));
				lM.getScene().placeBlock(door,(int)ls.get(0),(int)ls.get(1)-1);
				lM.getScene().placeBlock(door.getKey());
			}
			door.setEndDoor();
			return new Floor(0,0);
		});
		lM.put("bookshelf",(ls)->{
			if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
				Bookshelf bookshelf = new Bookshelf((int)ls.get(0),(int)ls.get(1),(int)ls.get(2),(int)ls.get(3));
				lM.getScene().placeBlock(bookshelf);
			}
			return new Floor(0,0);
		});
		lM.put("deadend",(ls)->{
			if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
				Roof deadend = new Roof((int)ls.get(0),(int)ls.get(1),(int)ls.get(2),(int)ls.get(3));
				lM.getScene().placeBlock(deadend);
			}else{
				lM.getScene().placeBlock(new Roof((int)ls.get(0),(int)ls.get(1)));
			}
			return new Floor(0,0);
		});
		lM.put("spider",(ls)->{
			// if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
			// 	SpiderWeb spiderWeb = new SpiderWeb((int)ls.get(0),(int)ls.get(1),(int)ls.get(2),(int)ls.get(3));
			// 	lM.getScene().placeBlock(spiderWeb);
			// }else{
			// 	lM.getScene().placeBlock((int)ls.get(0),(int)ls.get(1)));
			// }
			return new SpiderWeb(0,0);
		});
		lM.put("ladder",(ls)->{
			Ladder_item ladder = new Ladder_item((int)ls.get(0),(int)ls.get(1));
			if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
				ladder = new Ladder_item((int)ls.get(2),(int)ls.get(3));
			}
			lM.getScene().placeBlock(ladder);
			lM.getScene().placeBlock(new Ladder((int)ls.get(0),(int)ls.get(1),ladder));
			return null;
		});
		lM.put("chest",(ls)->{
			lM.getScene().placeBlock(new Chest((int)ls.get(0),(int)ls.get(1),(int)ls.get(2)));
			return new Floor(0,0);
		});
		lM.put("door",(ls)->{
			if(ls.size()>2 && ls.get(2)!=null && ls.get(3)!=null){
				Door door = new Door((int)ls.get(0),(int)ls.get(1),(int)ls.get(2),(int)ls.get(3));
				lM.getScene().placeBlock(door);
				lM.getScene().placeBlock(door.getKey());
			}else{
				Door door = new Door(0,0,(int)ls.get(0),(int)ls.get(1));
				lM.getScene().placeBlock(door,(int)ls.get(0),(int)ls.get(1)-1);
				lM.getScene().placeBlock(door.getKey());
			}
			//new Chest(0,0);
			return new Floor(0,0);
		});
		lM.put("floor",(ls)->{
			return new Floor(0,0);
		});
		lM.put("wall",(ls)->{return new Wall((int)ls.get(0),(int)ls.get(1));});
		lM.put("skeleton",(ls)->{return new Skeleton(0,0);});
		lM.put("bat",(ls)->{return new Bat(0,0);});
		lM.put("player",(ls)->{
			Player a = new Player(0,0);a.setControls(option.getPlayerControls(0));
			return a;
		});
	}

	/**
	 *
	 * @return
	 */
	public gameScene getLevelScene(){
		gameScene scene = new gameScene(1000,660);


		scene.setBackdrop(Textures.night_backdrop);
		// scene.add(new gameObject(0,0));

		KeyDoor key1 = new KeyDoor(2,1);
		Ladder_item itemStair1 = new Ladder_item(0,1);

		scene.placeBlock(new Floor(0,1));
		// scene.placeBlock(new floor(1,0));
		scene.placeBlock(new Floor(1,1),1,1);
		scene.placeBlock(new Ladder_item(0,1));
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
		scene.placeBlock(new SpiderWeb(1,2));
		scene.placeBlock(new SpiderWeb(1,3));
		scene.placeBlock(new SpiderWeb(1,4));
		scene.placeBlock(new SpiderWeb(1,5));

		scene.placeBlock(new Wall(3,0));
		scene.placeBlock(new Wall(4,0));
		scene.placeBlock(new Door(5,0,key1));
		scene.placeBlock(new Wall(6,0));
		scene.placeBlock(new Wall(7,0));
		scene.placeBlock(new Wall(8,0));

		scene.placeBlock(new HealthPotion(6,1));
		scene.placeBlock(new Bookshelf(2,0));
		scene.placeBlock(new Chest(8,1,1));



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
		scene.center(1, 1);
		return scene;
	}
	public gameScene getLevelScene(int level) {
		String content = ReadeFile("/maps/level"+(level)+".map");
		// return getLevelScene();
		return lM.exec(content);
	}

	/**
	 * La methode String ReadeFile(String file)permet de lire le ficher avec
	 * InputStream config = optionConfig.class.getResourceAsStream(file);
	 * @param file
	 * @return
	 */
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

