package gameObject;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.util.Duration;
import assets.Textures;

public class gameScene extends Canvas{
	List<gameObject> batch = new ArrayList<gameObject>();
	Image background;
	private int width;
	private int height;
	private double scale = 2;
	public double getScale() {
		return scale;
	}
	private double zoom = 1.5;
	public double getZoom() {
		return zoom;
	}
	public void setZoom(double zoom) {
		double lastzoom = this.zoom;
		lastzoom = 1/lastzoom;
		graphic.scale(lastzoom, lastzoom);
		this.zoom = zoom;
		graphic.scale(zoom, zoom);
	}
	public double getCanvasHeight() {
		return height/zoom;
	}
	public double getCanvasWidth() {
		return width/zoom;
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
		graphic.scale(zoom, zoom);
		Textures.setScale(scale);
		Textures.setQuality(1);
	}
	public gameScene(double width, double height) {
		this((int) width,(int) height);
	}
	public void add(gameObject gameobject){
		batch.add(gameobject);
		gameobject.setScene(this);
		gameobject.setScale(1/Textures.getQuality());
	}
	public void placeBlock(gameObject gO){
		gO.setX(Game.BLOCK_WIDTH*scale*gO.getX());
		gO.setY(Game.BLOCK_HEIGHT*scale*gO.getY());
		add(gO);
	}
	public void placeBlock(gameObject gO,int x, int y){
		gO.setX(Game.BLOCK_WIDTH*scale*x);
		gO.setY(Game.BLOCK_HEIGHT*scale*y);
		add(gO);
	}
	public void remove(gameObject gO){
		batch.remove(gO);
	}
	private int timer = 0;
	private void rendering(){
		timer++;
		graphic.clearRect(0, 0, width, height);
		if(background != null){
			graphic.drawImage(background,0,0,width/zoom,height/zoom);
		}
		try{
			batch.forEach((gameobject)->{
				gameobject.update();
				gameobject.render(graphic);
			});
		}catch(Exception e){
			System.out.println("NO SCENE");
		}
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
				// System.out.println(gO.getType()+" at "+x+" "+y);
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
		background = Textures.load(texture);
	}
	public void keypress(KeyCode keycode,boolean pressed){
		batch.forEach((gameobject)->{
			gameobject.keypress(keycode,pressed);
		});
	}
	public void click(double x,double y,MouseButton button){
		final double cx = x /zoom;
		final double cy = y /zoom;
		batch.forEach((gameobject)->{
			if(gameobject.isOn(cx, cy)){
				gameobject.click(x,y,button);
			}
		});
	}
}
