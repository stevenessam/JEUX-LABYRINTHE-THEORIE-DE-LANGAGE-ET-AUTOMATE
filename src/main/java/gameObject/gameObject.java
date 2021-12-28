package gameObject;

import java.util.List;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;

public class gameObject extends sprite{
	private boolean positionGlobal = false;
	public boolean isPositionGlobal() {
		return positionGlobal;
	}
	public void setPositionGlobal(boolean positionGlobal) {
		this.positionGlobal = positionGlobal;
	}
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
	/**
 	* Creer un element empty en 0,0
	 * necessite une image /assets/empty.png
	 * revient à gameObject(0,0)
 	*/
	public gameObject(){
		this(0,0);
	}
	/**
	 * Creer un element empty en 0,0
	 * necessite une image /assets/empty.png
	 * revient à gameObject(x,y,"/assets/empty.png")
	 * @param x
	 * @param y
	 */
	public gameObject(double x,double y){
		this(x,y,"/assets/empty.png");
	}
	/**
	 * Creer un Object 2D ou position
	 * @param x
	 * @param y
	 * avec l'image
	 * @param sprite
	 */
	public gameObject(double x,double y,String sprite){
		super(sprite);
		this.setX(x);
		this.setY(y);
	}
	/** sert de methode interface */
	protected void update(){}
	/** recuper le nombre de cylcle sur la boucle de la scene */
	public int getTimer(){return this.scene.getTimer();}
	/**
	 * simule une attente avec un temps en nombre de cycle
	 * @param time
	 * @return
	 */
	public boolean wait(int time){
		return this.scene.getTimer() % time == 0;
	}
	/**EVENT */
	public void keypress(KeyCode keycode,boolean pressed){}
	public void click(double x,double y,MouseButton button){}

	/*UTILS*/
	/**
	 * verifie si l'element a de colision en commun avec un autre element 
	 * (condition : l'un des elements doit etre solid)
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean getNoCollision(double x,double y){
		List<gameObject> objects = this.scene.getObjects(x,y);
		return objects.stream().filter((gameObject gO)->{
			return gO.type == gameObjectType.SOLID;
		}).findFirst().isEmpty();
	}
	/**
	 * verifie si l'element est present au coordooné indique.
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOn(double x,double y){
		double dx = -getRenderWidth() * this.getDeltaX();
		double dy = -getRenderHeight() * this.getDeltaY();
		double sx = (this.getX()+dx);
		double ex = this.getX()+dx+getRenderWidth();
		double sy = (this.getY()+dy);
		double ey = this.getY()+dy+getRenderHeight();
		boolean onX = sx<=x&&ex>=x;
		boolean onY = sy<=y&&ey>=y;
		return onX&&onY;
	}
	// public static gameObject create(Object ...objects){
	// 	gO = new super();
	// 	return gO;
	// }
}
