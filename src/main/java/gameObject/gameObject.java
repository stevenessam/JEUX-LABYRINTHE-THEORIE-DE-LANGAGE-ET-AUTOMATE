package gameObject;

import java.util.List;

import game.Game;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

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
		// this.setX(getX()*Game.BLOCK_WIDTH*this.scene.getScale());
		// this.setY(getY()*);
	}
	protected gameScene getScene() {
		return scene;
	}
	protected void delete(){
		this.scene.remove(this);
		// this.scene = null;
	}

	public gameObject(){
		this(0,0);
	}
	public gameObject(double x,double y){
		this(x,y,"empty.png");
	}
	public gameObject(double x,double y,String sprite){
		super(sprite);
		this.setX(x);
		this.setY(y);
	}
	protected void update(){

	}
	public int getTimer(){
		return this.scene.getTimer();
	}
	public boolean wait(int time){
		return false;
	}
	/**EVENT */
	public void keypress(KeyCode keycode,boolean pressed){}
	public void click(double x,double y,MouseButton button){}

	/*UTILS*/

	public boolean getNoCollision(double x,double y){
		List<gameObject> objects = this.scene.getObjects(x,y);
		return objects.stream().filter((gameObject gO)->{
			return gO.type == gameObjectType.SOLID;
		}).findFirst().isEmpty();
	}
	public boolean isOn(double x,double y){
		double dx = -getWidth() * this.getDeltaX();
		double dy = -getHeight() * this.getDeltaY();
		boolean onX = this.getX()+dx<=x&&this.getX()+dx+getWidth()>=x;
		boolean onY = this.getY()+dy<=y&&this.getY()+dy+getHeight()>=y;
		return onX&&onY;
	}
}
