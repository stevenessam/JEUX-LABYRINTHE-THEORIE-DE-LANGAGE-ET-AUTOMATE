package ressourceManages;

import java.util.HashMap;
import java.util.List;

import javafx.scene.image.Image;

public class ressourcesManager {
	private static HashMap<String,Image> ressources = new HashMap<String,Image>();

	public static void addRessources(List<String> ressouces){
		ressouces.forEach((file)->{
			Image img = ressourcesManager.loadImage(file);
			ressourcesManager.ressources.put(file, img);
		});
	}
	public static void addRessource(String ressouce){
		Image img = ressourcesManager.loadImage(ressouce);
		ressourcesManager.ressources.put(ressouce, img);
	}
	public static Image loadImage(String file){
		return new Image(ressourcesManager.class.getResourceAsStream(file));
	}
	public static Image loadImage(String file,int width,int height){
		return new Image(ressourcesManager.class.getResourceAsStream(file), width, height, true, false);
	}
	public static Image getImage(String file){
		return ressourcesManager.ressources.get(file);
	}
}
