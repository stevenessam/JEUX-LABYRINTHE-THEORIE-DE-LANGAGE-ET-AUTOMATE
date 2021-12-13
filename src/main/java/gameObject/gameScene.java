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

public class gameScene extends Canvas{
	List<gameObject> batch = new ArrayList<gameObject>();
	Image background;
	private int width;
	private int height;
	private int scale = 16;
	public int getScale() {
		return scale;
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
		setWidth(width);
		this.height = height;
		setHeight(height);
		setScaleX(width/height);
		setScaleY(width/height);
		graphic = this.getGraphicsContext2D();
	}
	public void add(gameObject gameobject){
		batch.add(gameobject);
		gameobject.setScene(this);
	}
	private void rendering(){
		if(background != null){
			graphic.drawImage(background,0,0,width,height);
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
				Duration.millis(500),
				event -> rendering()
			)
		);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
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
		background = new Image(texture);
	}
	public void keypress(KeyCode keycode){
		batch.forEach((gameobject)->{
			gameobject.keypress(keycode);
		});
	}
}
