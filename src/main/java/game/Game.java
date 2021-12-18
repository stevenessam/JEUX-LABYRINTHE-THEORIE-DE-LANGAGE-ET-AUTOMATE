package game;

import assets.Textures;
import gameObject.gameFont;
import gameObject.gameObject;
import gameObject.gameScene;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import parameters.levelConfig;
import parameters.optionConfig;

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
		gamePane = new BorderPane();
		scene.setRoot(gamePane);
		/**Main Menu */
		initMainMenu();

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
			case GAMEPLAY:
				// setLevel(levelconfig.getLevelScene());
			break;
		}
	}
	private int level;
	public void setLevel(int level){
		this.level = level;
		renderScene(levelconfig.getLevelScene(level));
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
		menu.add(new gameFont(centerX, 2.5*gapY, "Select Level",true));
		menu.add(new gameFont(centerX, 3*gapY, "Options",true));
		menu.add(gameFont.createButton(centerX, 4.5*gapY, "Quit", (button)->{javafx.application.Platform.exit();}));

		menu.add(new gameFont(centerX, 7.5*gapY, "developper par Tostse gaming",true));
	}
	public void MainMenu(){
		renderScene(menu);
	}

	public void next() {
		setLevel(level+1);
	}

}