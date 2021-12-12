package gameObject;

import java.util.HashMap;

import javafx.scene.input.KeyCode;
import ressourceManages.Textures;

public class player extends gameObject{

	private int speed = 5;

	public player(int x, int y) {
		super(x,y);
		setSprite(Textures.Knight,2,1);
	}
	public void DOWN(){
		if (y<this.getScene().getCanvasHeight()-1 && getNoCollision(x, y + 1)) {
			y+=speed;
		}
	}
	public void LEFT(){
		if (x>0 && getNoCollision(x - 1, y)) {
			x-=speed;
		}
	}
	public void RIGHT(){
		if (x<this.getScene().getCanvasWidth()-1 && getNoCollision(x + 1, y)) {
			x+=speed;
		}
	}
	public void UP(){
		if (y>0 && getNoCollision(x, y - 1)) {
			y-=speed;
		}
	}
	private HashMap<KeyCode, String> playerControls;
	public void setControls(HashMap<KeyCode, String> playerControls) {
		this.playerControls=playerControls;
	}
	@Override
	protected void update() {
		this.setFrame((this.frame+1)%2);
	}
	public void keypress(KeyCode keycode){
		String function = playerControls.get(keycode);
		if(function == null)
			return;
		switch (function){
			case "DOWN":DOWN();break;
			case "LEFT":LEFT();break;
			case "RIGHT":RIGHT();break;
			case "UP":UP();break;
		}
	}
}
