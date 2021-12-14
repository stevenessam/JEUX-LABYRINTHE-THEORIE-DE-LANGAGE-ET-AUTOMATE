package gameObject;

import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class gameObject {
	
	protected gameObjectType type = gameObjectType.NOCLIP;

	protected float x;
	protected float y;
	private int width;
	private int height;
	private Image sprite;

	private int column = 1;
	private int row = 1;
	protected int frame = 1;

	private gameScene scene;
	public void setScene(gameScene scene) {
		this.scene = scene;
	}
	protected gameScene getScene() {
		return scene;
	}

	public gameObject(){
		this(0,0);
	}
	public gameObject(int x,int y){
		this.x = x;
		this.y = y;
		sprite = new Image("empty.png");
		//ressourcesManager.getImage("Empty")
		// sprite.setViewOrder(10);
	}
	protected void update(){

	}
	public void render(GraphicsContext graphic){
		if(column == 1 && row ==1){
			graphic.drawImage(sprite, x, y);
		}else{
			graphic.drawImage(sprite, ((frame)%column)*width, (int)((frame)/column)*height, width, height, x, y, width, height);
		}
	}
	public void setSprite(String url,int col,int row){
		sprite = new Image(url);
		this.width = (int) (sprite.getWidth()/col);
		this.height = (int) (sprite.getHeight()/row);
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setFrame(int frame){
		this.frame = frame;
	}
	public void keypress(KeyCode keycode){
	}
	public boolean getNoCollision(double x,double y){
		List<gameObject> objects = this.scene.getObjects(x,y);
		return objects.stream().filter((gameObject gO)->{
			return gO.type == gameObjectType.SOLID;
		}).findFirst().isEmpty();
	}
	public boolean isOn(double x,double y){
		boolean onX = this.x<=x&&this.x+width>=x;
		boolean onY = this.y<=y&&this.y+height>=y;
		return onX&&onY;
	}
}
