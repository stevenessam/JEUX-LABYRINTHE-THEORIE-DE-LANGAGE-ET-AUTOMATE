package gameObject;

import java.util.HashMap;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import ressourceManages.Textures;

public class player extends gameObject{

	private int speed = 5;
	private double life = 3.5f;

	private sprite lifeHUD = new sprite(Textures.Stats);

	public void addHealth(double heart){
		life+=heart;
	}

	public player(int x, int y) {
		super(x,y);
		setType(gameObjectType.PLAYER);
		setSprite(Textures.Knight,2,1);
		this.setDeltaX(.5);
		this.setDeltaY(1);
		lifeHUD.setSprite(3, 2);
	}
	private HashMap<KeyCode, String> playerControls;
	public void setControls(HashMap<KeyCode, String> playerControls) {
		this.playerControls=playerControls;
	}
	@Override
	protected void update() {
		this.setFrame(((this.getTimer()/50)+1)%2);
		double x=getX()+dx*speed;
		double y=getY()+dy*speed;
		if(canWalkOn(x, y)){
			setX(x);
			setY(y);
		}
	}
	@Override
	public void render(GraphicsContext graphic) {
		super.render(graphic);
		lifeHUD.setY(10);
		lifeHUD.setFrame(0);
		double hearts = life;
		for (int i = 0; i < 5; i++) {
			lifeHUD.setX(10+i*16*this.getScene().getScale());
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
		lifeHUD.setY(26*this.getScene().getScale());
		lifeHUD.setFrame(0);
		for (int i = 0; i < 3; i++) {
			lifeHUD.setX(10+i*16*this.getScene().getScale());
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
