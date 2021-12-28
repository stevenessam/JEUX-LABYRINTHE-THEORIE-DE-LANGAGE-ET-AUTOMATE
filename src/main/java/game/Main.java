package game;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	
	@Override
	public void start(Stage primaryStage) {
		System.setProperty("prism.lcdtext", "false");
		primaryStage.setTitle("Labyrinthus");

		primaryStage.setScene(Game.create());
		primaryStage.show();
	}

}
