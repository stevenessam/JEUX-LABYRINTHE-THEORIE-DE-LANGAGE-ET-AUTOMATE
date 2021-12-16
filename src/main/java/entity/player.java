package entity;

import java.util.HashMap;
import java.util.List;

import assets.Textures;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.sprite;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;

public class player extends gameObject{

	private int speed = 5;
	private double life = 3.5f;

	private sprite lifeHUD = new sprite(Textures.Stats);

	public void addHealth(double heart){
		life+=heart;
	}
	public void slowness(int slowing){
		speed = slowing;
	}

	public player(int x, int y) {
		super(x,y);
		setType(gameObjectType.PLAYER);
		setSprite(Textures.Knight,2,1);
		this.setDeltaX(.5);
		this.setDeltaY(1);
		lifeHUD.setSprite(3, 2);
		lifeHUD.setRenderWidth(16);
		lifeHUD.setRenderHeight(16);
		setPositionGlobal(true);
	}
	private HashMap<KeyCode, String> playerControls;
	public void setControls(HashMap<KeyCode, String> playerControls) {
		this.playerControls=playerControls;
	}
	@Override
	protected void update() {
		if(life <= 0){
			this.getScene().gameOver();
			return;
		}
		this.setFrame(((this.getTimer()/50)+1)%2);
		double mx = dx*speed;
		double my = dy*speed;
		double x=getX()+mx;
		double y=getY()+my;
		flipH(dx<0);
		if(canWalkOn(x, y)){
			this.getScene().translate(-mx, -my);
			setX(x);
			setY(y);
		}
	}
	public void flipH(boolean activated){
		super.flipH(activated);
		setDeltaX(activated?-.25:.25);
		setDeltaY(activated?-1.2:1.2);
	}
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
		lifeHUD.setY(10+lifeHUD.getRenderHeight());
		lifeHUD.setFrame(0);
		for (int i = 0; i < 3; i++) {
			lifeHUD.setX(10+i*lifeHUD.getRenderWidth());
			lifeHUD.setFrame(3+((this.getTimer()/25)+1)%3);
			lifeHUD.render(graphic);
		}
	}
	int dx = 0;
	int dy = 0;
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
	private boolean canWalkOn(double x,double y){
		List<gameObject> objects = this.getScene().getObjects(x,y);
		// System.out.println(objects+" at "+x+" "+y);
		boolean floor = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PATH.equals(gO.getType());
		}).findFirst().isPresent();
		// System.out.println(floor);
		return floor;
	}
}
