package gameObject;

import java.util.ArrayList;
import java.util.List;

import game.Game;
import gameClass.Location;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
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
	public void setScale(double scale) {
		this.scale = scale;
	}
	private double zoom = 1.4;

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
	private Game container;
	public void setContainer(Game container) {
		this.container = container;
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
		this.setOnMouseClicked(event -> {
			this.click(event.getX(),event.getY(),event.getButton());
		});
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
		// graphic.translate(tx, ty);
		graphic.clearRect(-tx, -ty, graphic.getCanvas().getWidth(), graphic.getCanvas().getHeight());
		if(background != null){
			graphic.drawImage(background,0,0,width/zoom,height/zoom);
		}
		try{
			batch.forEach((gameobject)->{
				gameobject.update();
				// if(!gameobject.isPositionGlobal())
					gameobject.render(graphic);
			});
		}catch(Exception e){
			System.out.println("NO SCENE");
		}
		// graphic.translate(-tx, -ty);
		// try{
		// 	batch.forEach((gameobject)->{
		// 		if(gameobject.isPositionGlobal())
		// 			gameobject.render(graphic);
		// 	});
		// }catch(Exception e){
		// 	System.out.println("NO SCENE");
		// }
	}
	private boolean isrunning = false;
	private Timeline timeline;
	private double ty = 0;
	private double tx = 0;
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
	public void gameOver(){
		stop();
		graphic.setFill(new Color(0,0,0,.5));
		graphic.fillRect(-tx,-ty,width/zoom,height/zoom);
		double gapX = width/zoom/4;
		double gapY = height/zoom/4;
		gameFont gameOverText = new gameFont(gapX, gapY, "Game Over",true);
		gameFont btn_retry = gameFont.createButton(gapX, gapY*2, "Retry", (button)->{
			System.out.println("retry");
			container.restart();
		});
		gameFont btn_quit = gameFont.createButton(gapX*2, gapY*2, "Main Menu", (button)->{
			System.out.println("main menu");
			container.setLocation(Location.MAINMENU);
		});

		add(btn_retry);
		add(btn_quit);

		gameOverText.render(graphic);
		btn_retry.render(graphic);
		btn_quit.render(graphic);
	}
	public void translate(double x,double y){
		tx += x;
		ty += y;
		graphic.translate(x,y);
	}
	public double getTX() {
		return tx;
	}
	public double getTY() {
		return ty;
	}
	public void center(double x,double y){
		x = -Game.BLOCK_WIDTH*scale*x;
		y = -Game.BLOCK_HEIGHT*scale*y;
		x += getCanvasWidth()/2;
		y += getCanvasHeight()/2;
		translate(x, y);
	}
}
