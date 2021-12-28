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
	/**
	 * recupere la class Game parent de la scene
	 * @param container
	 */
	public void setContainer(Game container) {
		this.container = container;
	}
	private GraphicsContext graphic;
	/**
	 * creer une scene apartir d'entier
	 * @param width
	 * @param height
	 */
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
	/**
	 * creer un scene avec des doubles
	 * @param width
	 * @param height
	 */
	public gameScene(double width, double height) {
		this((int) width,(int) height);
	}
	/**
	 * ajouter un object a la scene
	 * @param gameobject
	 */
	public void add(gameObject gameobject){
		batch.add(gameobject);
		gameobject.setScene(this);
		gameobject.setScale(1/Textures.getQuality());
	}
	/**
	 * ajouter un object a la scene en suivant les block de la class Game
	 * @param gameobject
	 */
	public void placeBlock(gameObject gO){
		gO.setX(Game.BLOCK_WIDTH*scale*gO.getX());
		gO.setY(Game.BLOCK_HEIGHT*scale*gO.getY());
		add(gO);
	}
	/**
	 * ajouter un object a la scene en suivant les block de la class Game
	 * @param gameobject
	 * @param x
	 * @param y
	 */
	public void placeBlock(gameObject gO,int x, int y){
		gO.setX(Game.BLOCK_WIDTH*scale*x);
		gO.setY(Game.BLOCK_HEIGHT*scale*y);
		add(gO);
	}
	private List<gameObject> toberemoved = new ArrayList<gameObject>();
	/**
	 * retirer un object de la scene
	 * @param gO
	 */
	public void remove(gameObject gO){
		// toberemoved.add(gO);
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
			if(batch == null)return;
			batch.forEach((gameobject)->{
				if(toberemoved.contains(gameobject))return;
				if(gameobject != null){
					gameobject.update();
					gameobject.render(graphic);
				}
				// if(!gameobject.isPositionGlobal())
			});
		}catch(Exception e){
			System.out.println(e);
		}
		// eviter une java.util.ConcurrentModificationException
		// if(toberemoved.size()>1){
		// 	toberemoved.forEach((gameObject)->{
		// 		batch.remove(gameObject);
		// 	});
		// }
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
	/**
	 * demarer la boucle de la scene
	 */
	public void start(){
		assert !isrunning;
		isrunning = true;
		this.batch.sort((o1,o2)->{
			Integer s1 = o1.getType()==gameObjectType.PATH?-1:1;
			Integer s2 = o2.getType()==gameObjectType.PATH?-1:1;
			return Integer.compare(s1, s2);
		});
		// batch.forEach(System.out::println);
		timeline = new Timeline(
			new KeyFrame(
				Duration.millis(15),
				event -> rendering()
			)
		);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	/**
	 * recupere le nombre de cycle
	 * @return
	 */
	public int getTimer(){
		return timer;
	}
	/**
	 * list tout les objects au coordonées indique pour les trier plsu tard
	 * @param x
	 * @param y
	 * @return
	 */
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
	/**
	 * stop la boucle de la scene
	 */
	public void stop(){
		assert isrunning;
		assert timeline != null;
		timeline.pause();
	}
	/**
	 * effectue une seule iteration de la boucle de la scene
	 */
	public void render(){
		rendering();
	}

	/**
	 * definie un fond pour la scene
	 * @param texture
	 */
	public void setBackdrop(String texture){
		background = Textures.load(texture);
	}
	/**
	 * quand on press une touche
	 * @param keycode
	 * @param pressed
	 */
	public void keypress(KeyCode keycode,boolean pressed){
		batch.forEach((gameobject)->{
			gameobject.keypress(keycode,pressed);
		});
	}
	/**
	 * quand on click dans la scene
	 * @param x
	 * @param y
	 * @param button
	 */
	public void click(double x,double y,MouseButton button){
		final double cx = x /zoom - tx;
		final double cy = y /zoom - ty;
		batch.forEach((gameobject)->{
			if(gameobject.isOn(cx, cy)){
				gameobject.click(x,y,button);
			}
		});
	}
	/**
	 * ecran game Over
	 */
	public void gameOver(){
		stop();
		graphic.setFill(new Color(0,0,0,.5));
		graphic.fillRect(-tx,-ty,width/zoom,height/zoom);
		// graphic.fillRect(-tx,-ty,width/zoom,height/zoom);
		double gapX = width/zoom/4;
		double gapY = height/zoom/4;
		gameFont gameOverText = new gameFont(gapX-tx, gapY-ty, "Game Over",true);
		gameFont btn_retry = gameFont.createButton(gapX-tx, gapY*2-ty, "Retry", (button)->{
			System.out.println("retry");
			container.restart();
		});
		gameFont btn_quit = gameFont.createButton(gapX*2-tx, gapY*2-ty, "Main Menu", (button)->{
			System.out.println("main menu");
			container.setLocation(Location.MAINMENU);
		});

		add(btn_retry);
		add(btn_quit);

		gameOverText.render(graphic);
		btn_retry.render(graphic);
		btn_quit.render(graphic);
	}
	/**
	 * deplacer la scene quand le contenu est trop grand pour etre vu normalement
	 * @param x
	 * @param y
	 */
	public void translate(double x,double y){
		tx += x;
		ty += y;
		graphic.translate(x,y);
	}
	/**
	 * recupere la tranlation sur X
	 * @return
	 */
	public double getTX() {
		return tx;
	}
	/**
	 * recupere la tranlation sur Y
	 * @return
	 */
	public double getTY() {
		return ty;
	}
	/**
	 * centre la scene sur les coordonnée passer en parametre
	 * @param x
	 * @param y
	 */
	public void center(double x,double y){
		x = -Game.BLOCK_WIDTH*scale*x;
		y = -Game.BLOCK_HEIGHT*scale*y;
		x += getCanvasWidth()/2;
		y += getCanvasHeight()/2;
		translate(x, y);
	}
	/**
	 * ecran passer au niveau suivant
	 */
	public void nextLevel(){
		stop();
		graphic.setFill(new Color(0,0,0,.5));
		graphic.fillRect(-tx,-ty,width/zoom,height/zoom);
		// graphic.fillRect(-tx,-ty,width/zoom,height/zoom);
		double gapX = width/zoom/4;
		double gapY = height/zoom/4;
		gameFont gameOverText = new gameFont(gapX*2-tx, gapY-ty, "You escape the mansoir",true);
		gameFont btn_retry = gameFont.createButton(gapX-tx, gapY*2-ty, "Continue", (button)->{
			System.out.println("Continue");
			container.next();
		});
		gameFont btn_quit = gameFont.createButton(gapX*3-tx, gapY*2-ty, "Main Menu", (button)->{
			System.out.println("main menu");
			container.setLocation(Location.MAINMENU);
		});

		add(btn_retry);
		add(btn_quit);

		gameOverText.render(graphic);
		btn_retry.render(graphic);
		btn_quit.render(graphic);
	}
}
