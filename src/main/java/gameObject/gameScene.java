package gameObject;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;
import ressourceManages.Textures;

public class gameScene extends Canvas{
	List<gameObject> batch = new ArrayList<gameObject>();
	Image background;
	private int width;
	private int height;
	private int scale = 2;
	public int getScale() {
		return scale;
	}
	public void setScale(int scale) {
		this.scale = scale;
	}
	public int getCanvasHeight() {
		return height;
	}
	public int getCanvasWidth() {
		return width;
	}
	private GraphicsContext graphic;
	public gameScene(int width, int height) {
		this.width = width;
		setWidth(this.width);
		this.height = height;
		setHeight(this.height);
		setScaleX(width/height);
		setScaleY(width/height);
		graphic = this.getGraphicsContext2D();
		graphic.scale(scale, scale);
		Textures.setScale(scale);
	}
	public void add(gameObject gameobject){
		batch.add(gameobject);
		gameobject.setScene(this);
	}
	private int timer = 0;
	private void rendering(){
		timer++;
		if(background != null){
			graphic.drawImage(background,0,0,width/scale,height/scale);
		}
		batch.forEach((gameobject)->{
			gameobject.update();
			gameobject.render(graphic);
		});
	}
	private boolean isrunning = false;
	private Timeline timeline;
	public void start(){
		assert !isrunning;
		isrunning = true;
		timeline = new Timeline(
			new KeyFrame(
				Duration.millis(10),
				event -> rendering()
			)
		);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	public int getTimer(){
		return timer;
	}
	public List<gameObject> getObjects(double x,double y){
		List<gameObject> area = new ArrayList<gameObject>();
		batch.forEach((gameObject gO)->{
			if(gO.isOn(x,y)){
				area.add(gO);
			}
		});
		return area;
	}
	public void stop(){
		assert isrunning;
		assert timeline != null;
		timeline.pause();
	}
	public void render(){
		rendering();
	}

	public void setBackdrop(String texture){
		background =  Textures.load(texture);
	}
	public void keypress(KeyCode keycode,boolean pressed){
		batch.forEach((gameobject)->{
			gameobject.keypress(keycode,pressed);
		});
	}
}
