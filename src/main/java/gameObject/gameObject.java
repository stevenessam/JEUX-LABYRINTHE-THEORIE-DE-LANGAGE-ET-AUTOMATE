package gameObject;

import java.util.List;

import game.Game;
import javafx.scene.input.KeyCode;

public class gameObject extends sprite{
	
	private gameObjectType type = gameObjectType.NOCLIP;
	public gameObjectType getType() {
		return type;
	}
	public void setType(gameObjectType type) {
		this.type = type;
	}

	private gameScene scene;
	public void setScene(gameScene scene) {
		this.scene = scene;
		this.x = x*Game.BLOCK_WIDTH;
		this.y = y*Game.BLOCK_HEIGHT;
	}
	protected gameScene getScene() {
		return scene;
	}

	public gameObject(){
		this(0,0);
	}
	public gameObject(int x,int y){
		this(x,y,"empty.png");
	}
	public gameObject(int x,int y,String sprite){
		super(sprite);
		this.x = x;
		this.y = y;
	}
	protected void update(){

	}
	public int getTimer(){
		return this.scene.getTimer();
	}
	public void keypress(KeyCode keycode,boolean pressed){}
	public boolean getNoCollision(double x,double y){
		List<gameObject> objects = this.scene.getObjects(x,y);
		return objects.stream().filter((gameObject gO)->{
			return gO.type == gameObjectType.SOLID;
		}).findFirst().isEmpty();
	}
	public boolean isOn(double x,double y){
		boolean onX = this.x<=x&&this.x+width>=x;
		boolean onY = this.y<=y&&this.y+height>=y;
		return onX&&onY;
	}
}
