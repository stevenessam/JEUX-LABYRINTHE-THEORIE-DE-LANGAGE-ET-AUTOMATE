package game;

import gameClass.Location;
import gameObject.gameScene;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import parameters.levelConfig;
import parameters.optionConfig;

public class Game{

	final static int TILE_SIZE = 32;
	final static int BOARD_WIDTH = 20;
	final static int BOARD_HEIGHT = 14;

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
		options = new optionConfig("/config.json");
		levelconfig = new levelConfig(options);
		gamePane = new BorderPane();
		setLocation(Location.GAMEPLAY);
		scene.setOnKeyPressed(event -> {
			gameScene.keypress(event.getCode());
		});
	}

	public Game() {
		this(new Scene(new BorderPane()));
	}

	public void setLocation(Location location) {
		switch(location){
			case MAINMENU:
				System.out.println("NO MAIN MENU FIND");
			break;
			case GAMEPLAY:
				scene.setRoot(gamePane);
				setLevel(levelconfig.getLevelScene());
			break;
		}
	}

	public void setLevel(gameScene game){
		gamePane.setCenter(game);
		game.render();
		gameScene = game;
		game.start();
	}

	public static Scene create() {
		Game game = new Game();
		return game.getScene();
	}
}