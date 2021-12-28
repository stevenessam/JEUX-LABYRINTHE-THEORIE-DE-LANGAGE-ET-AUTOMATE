package game;

import assets.Textures;
import gameObject.gameFont;
import gameObject.gameObject;
import gameObject.gameScene;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import parameters.levelConfig;
import parameters.optionConfig;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Game{

	final static int TILE_SIZE = 32;
	final static int BOARD_WIDTH = 20;//20
	final static int BOARD_HEIGHT = 15;//14
	public final static int BLOCK_WIDTH = 28;
	public final static int BLOCK_HEIGHT = 36;

	private final static boolean debug = true;

	private Scene scene;
	public Scene getScene(){
		return scene;
	}

	private BorderPane gamePane;
	private levelConfig levelconfig;
	optionConfig options;
	private gameScene gameScene;
	public Game(Scene scene){
		this.scene = scene;
		scene.setFill(Color.BLACK);
		options = new optionConfig("/config.json");
		levelconfig = new levelConfig(options);
		System.out.println(levelconfig);//evite l'erreur levelconfig == undefined
		gamePane = new BorderPane();
		scene.setRoot(gamePane);
		/**Main Menu */
		initMainMenu();
		/**Select Menu */
		initSelectLevel();
		/** Options */
		initOptions();
		/** aller au menu principale */
		setLocation(gameClass.Location.MAINMENU);
		// setLevel(levelconfig.getLevelScene(options.getLevel()));
		/* Action*/
		// scene.setOnMouseClicked(event -> {
		// 	gameScene.click(event.getX(),event.getY(),event.getButton());
		// });
		scene.setOnKeyPressed(event -> {
			gameScene.keypress(event.getCode(),true);
		});
		scene.setOnKeyReleased(event -> {
			gameScene.keypress(event.getCode(),false);
		});
	}

	public Game() {
		this(new Scene(new Pane(),debug?1000:1920,debug?660:1080,true,SceneAntialiasing.DISABLED));
	}

	public void setLocation(gameClass.Location location) {
		switch(location){
			case MAINMENU:
				MainMenu();
				break;
			case SELECTLEVEL:
				SelectLevel();
				break;
			case GAMEPLAY:
				// setLevel(levelconfig.getLevelScene());
				break;
		}
	}



	private Set<String> listeFilesMaps() {
		URL resource = this.getClass().getResource("/maps/");//level1.map
		if(resource==null)return new HashSet<String>();
		return Stream.of(new File(resource.getFile()).listFiles())
				.filter(file -> !file.isDirectory())
				.map(File::getName)
				.collect(Collectors.toSet());
	}

	private int level;
	public void setLevel(int level){
		this.level = level;
		renderScene(levelconfig.getLevelScene(level));
	}
	private void setLevel(String map) {
		int level = Integer.valueOf(map.split("level")[1].split(".map")[0]);
		setLevel(level);
	}
	public void restart(){
		setLevel(level);
	}
	public void renderScene(gameScene game){
		if(gameScene!=null)
			gameScene.stop();
		gamePane.setCenter(game);
		game.render();
		gameScene = game;
		game.start();
		game.setContainer(this);
		// double W = scene.getWidth();
		// double H = scene.getHeight();
		// game.setWidth(W);
		// game.setHeight(H);
		// game.setScale(W/H);
	}

	public static Scene create() {
		Game game = new Game();
		return game.getScene();
	}
	gameScene menu;
	public void initMainMenu(){
		menu = new gameScene(this.scene.getWidth(),this.scene.getHeight());
		menu.setBackdrop(Textures.DarkSky);

		var centerX = menu.getCanvasWidth()/2;
		var gapY = menu.getCanvasHeight()/8;
		gameObject title = new gameObject(centerX, 0, Textures.Title);
		title.setDeltaY(-.1);
		title.setDeltaX(1.5);
		title.setScale(6);
		menu.add(title);
		menu.add(gameFont.createButton(centerX, 2*gapY, "Continue",(button)->{setLevel(options.getLevel());}));
		menu.add(gameFont.createButton(centerX, 3*gapY, "Select Level",(button)->{SelectLevel();}));
		menu.add(gameFont.createButton(centerX, 4*gapY, "Options",(button)->{Options();}));
		menu.add(gameFont.createButton(centerX, 5*gapY, "Quit", (button)->{javafx.application.Platform.exit();}));

		menu.add(new gameFont(centerX, 7.5*gapY, "developped by \"Tostse\" gaming",true));
	}
	public void MainMenu(){
		renderScene(menu);
	}


	gameScene select_level;

	public void initSelectLevel(){
		Set<String> maps = listeFilesMaps();
		select_level = new gameScene(this.scene.getWidth(),this.scene.getHeight());
		select_level.setBackdrop(Textures.DarkSky);

		var centerX = select_level.getCanvasWidth()/2;
		var gapX = select_level.getCanvasWidth()/3;
		var gapY = select_level.getCanvasHeight()/8;
		gameObject title = new gameObject(centerX, 0, Textures.Title);
		title.setDeltaY(-.1);
		title.setDeltaX(1.5);
		title.setScale(6);
		select_level.add(title);

		int x = 1;
		int y = 2;
		for(String map : maps){
			System.out.println(map);
			select_level.add(gameFont.createButton(gapX*x, y*gapY, map.split(".map")[0],(button)->{
				setLevel(map);
			}));
			x++;
			if(x>2){
				x= 1;
				y++;
			}

		}
		// menu.add(gameFont.createButton(centerX, 2*gapY, "Continue",(button)->{setLevel(options.getLevel());}));
		// menu.add(gameFont.createButton(centerX, 2*gapY, "Select Level",(button)->{listeFilesMaps("D:/Scolarite/University/Licence_3/Projet/Labyrinthus/src/main/resources/maps");}));
		// menu.add(new gameFont(centerX, 3*gapY, "Options",true));
		select_level.add(gameFont.createButton(centerX, 5*gapY, "Back", (button)->{MainMenu();}));

		select_level.add(new gameFont(centerX, 7.5*gapY, "developped by \"Tostse\" gaming",true));
	}
	

	public void SelectLevel(){
		renderScene(select_level);
	}

	gameScene optionscene;

	public void initOptions(){
		optionscene = new gameScene(this.scene.getWidth(),this.scene.getHeight());
		optionscene.setBackdrop(Textures.DarkSky);

		var centerX = optionscene.getCanvasWidth()/2;
		var gapX = optionscene.getCanvasWidth()/3;
		var gapY = optionscene.getCanvasHeight()/8;
		gameObject title = new gameObject(centerX, 0, Textures.Title);
		title.setDeltaY(-.1);
		title.setDeltaX(1.5);
		title.setScale(6);
		optionscene.add(title);
		HashMap<KeyCode, String> controls = options.getPlayerControls(0);
		int y = 4;
		List<KeyCode> keys = options.bindedKeys;
		List<gameFont> btns = new ArrayList<gameFont>();
		for(KeyCode control : controls.keySet()){
			int idx = keys.indexOf(control);
			String text = controls.get(control) + " - "+ keys.get(idx).getName();
			gameFont btn = new gameFont(centerX, (y*gapY)/2, text,true);
			Consumer<MouseButton> callback = (button)->{
				Optional<KeyCode> _ok = keys.stream().filter((key)->{
					System.out.println(btn.getText().split("- ")[1]+" = "+key.getName().toLowerCase());
					return btn.getText().split("- ")[1].equals(key.getName().toLowerCase());
				}).findFirst();
				if(_ok.isPresent()){
					KeyCode _key = _ok.get();
					int _idx = keys.indexOf(_key)+1;
					if(_idx<0)_idx = keys.size()-1;
					if(_idx>=keys.size())_idx = 0;
					String name = btn.getText().split(" - ")[0].toUpperCase();
					String _text = name + " - "+ keys.get(_idx).getName();
					btn.setText(_text);
				}
			};
			btn.setAction(callback);
			btns.add(btn);
			optionscene.add(btn);
			y++;

		}
		optionscene.add(gameFont.createButton(gapX, 5*gapY, "Save", (button)->{
			controls.clear();
			btns.forEach((btn)->{
				Optional<KeyCode> _ok = keys.stream().filter((key)->{
					return btn.getText().split("- ")[1].equals(key.getName().toLowerCase());
				}).findFirst();
				if(_ok.isPresent()){
					KeyCode _key = _ok.get();
					String name = btn.getText().split(" - ")[0].toUpperCase();
					System.out.println(_key+" "+name);
					controls.put(_key, name);
				}
			});
			System.out.println(controls);
			MainMenu();
		}));
		optionscene.add(gameFont.createButton(gapX*2, 5*gapY, "Back", (button)->{MainMenu();}));

		optionscene.add(new gameFont(centerX, 7.5*gapY, "developped by \"Tostse\" gaming",true));
	
	}
	public void Options(){
		renderScene(optionscene);
	}
	

	public void next() {
		setLevel(level+1);
	}

}