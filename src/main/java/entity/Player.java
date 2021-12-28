package entity;

import java.util.HashMap;
import java.util.List;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;


/**
 * Class Player qui extends la class gameObject
 */
public class Player extends gameObject{

	private int speed = 5;
	private double life = 3.5f;
	private int stars = 0;

	/**
	 * lifeHUD est un variable de la class sprite
	 */
	private sprite lifeHUD = new sprite(Textures.Stats);

	/**
	 * addHealth prend comme parametre heart en double
	 * @param heart
	 */
	public void addHealth(double heart){
		life+=heart;
	}

	/**
	 * Methode add star permet de ajouter des stars quand le player prend un stars dans le jeux
	 */
	public void addStar(){
		stars++;
	}

	/**
	 * Methode sloweness qui permet de ralentir le player
	 * @param slowing
	 */
	public void slowness(int slowing){
		speed = slowing;
	}

	/**
	 * Constructeur de la class player prend comme parametre x et y
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		super(x,y);
		setType(gameObjectType.PLAYER);
		setSprite(Textures.Knight,2,2);
		this.setDeltaX(.5);
		this.setDeltaY(1);
		lifeHUD.setSprite(3, 2);
		lifeHUD.setRenderWidth(16);
		lifeHUD.setRenderHeight(16);
		setPositionGlobal(true);
	}
	private HashMap<KeyCode, String> playerControls;

	/**
	 * Methode setControls prend en parametre un hashmap et playerControls
	 * @param playerControls
	 */
	public void setControls(HashMap<KeyCode, String> playerControls) {
		this.playerControls=playerControls;
	}

	/**
	 * Update Override la methode Update dans la class gameObject
	 * La méthode Update permet de mettre à jour le player
	 */
	@Override
	protected void update() {
		if(life <= 0){
			this.getScene().gameOver();
			return;
		}
		
		double mx = dx*speed;
		double my = dy*speed;
		double x=getX()+mx;
		double y=getY()+my;
		flipH(dx<0);
		boolean canWalk = canWalkOn(x, y);
		if(canWalk){
			this.getScene().translate(-mx, -my);
			setX(x);
			setY(y);
		}
		if(canWalk && (mx!=0 || my != 0) ){
			this.setFrame(2+((this.getTimer()/10)+1)%2);
		}else{
			this.setFrame(((this.getTimer()/50)+1)%2);
		}
	}

	/**
	 * Methode flipH prend en parametre un boolean activated
	 * @param activated
	 */
	public void flipH(boolean activated){
		super.flipH(activated);
		setDeltaX(activated?-.25:.25);
		setDeltaY(activated?-1.2:1.2);
	}

	/**
	 * Methode render prend un parametre GraphicsContext graphic
	 * @param graphic
	 */
	@Override
	public void render(GraphicsContext graphic) {
		super.render(graphic);
		lifeHUD.setX(-this.getScene().getTX());
		lifeHUD.setY(-this.getScene().getTY()+10);
		lifeHUD.setFrame(0);
		double hearts = life;
		for (int i = 0; i < 5; i++) {
			lifeHUD.setX(10-this.getScene().getTX()+i*lifeHUD.getRenderWidth());
			if(hearts<1){
				if(hearts<0){
					lifeHUD.setFrame(2);
				}else{
					lifeHUD.setFrame(1);
				}
			}
			lifeHUD.render(graphic);
			hearts--;
		}
		lifeHUD.setY(-this.getScene().getTY()+10+lifeHUD.getRenderHeight());
		lifeHUD.setFrame(0);
		for (int i = 0; i < stars; i++) {
			lifeHUD.setX(10-this.getScene().getTX()+i*lifeHUD.getRenderWidth());
			lifeHUD.setFrame(3+((this.getTimer()/25)+1)%3);
			lifeHUD.render(graphic);
		}
	}


	int dx = 0;
	int dy = 0;

	/**
	 * Methode keypress a comme parametre KeyCode keycode,boolean pressed
	 * @param keycode
	 * @param pressed
	 */
	public void keypress(KeyCode keycode,boolean pressed){
		String function = playerControls.get(keycode);
		if(function == null)
			return;
		switch (function){
			case "DOWN":dy=pressed?1:0;break;
			case "LEFT":dx=pressed?-1:0;break;
			case "RIGHT":dx=pressed?1:0;break;
			case "UP":dy=pressed?-1:0;break;
		}
	}

	/**
	 * Methode canWalkOn verifie si le player peux marcher sur le block ou non
	 * @param x
	 * @param y
	 * @return
	 */
	private boolean canWalkOn(double x,double y){
		List<gameObject> objects = this.getScene().getObjects(x,y);
		// System.out.println(objects+" at "+x+" "+y);
		boolean floor = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PATH.equals(gO.getType());
		}).findFirst().isPresent();
		boolean solid = objects.stream().filter((gameObject gO)->{
			return gameObjectType.SOLID.equals(gO.getType());
		}).findFirst().isPresent();
		// System.out.println(floor);
		return floor&&!solid;
	}
}
