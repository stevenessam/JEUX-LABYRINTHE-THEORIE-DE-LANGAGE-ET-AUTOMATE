package gameObject;

import javafx.scene.image.ImageView;
import ressourceManages.ressourcesManager;

public class gameObject {
	
	private ImageView imageView;

	public gameObject(){
		imageView = new ImageView(ressourcesManager.getImage("Empty"));
		imageView.setViewOrder(10);
	}
}
