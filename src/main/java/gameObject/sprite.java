package gameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class sprite {

	protected float x;
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	protected float y;
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	protected int width;
	protected int height;

	private Image image;

	private int column = 1;
	private int row = 1;

	protected int frame = 1;

	public sprite(String sprite) {
		this.image = new Image(sprite);
	}
	public void render(GraphicsContext graphic){
		if(column == 1 && row ==1){
			graphic.drawImage(image, x, y);
		}else{
			graphic.drawImage(image, ((frame)%column)*width, (int)((frame)/column)*height, width, height, x, y, width, height);
		}
	}
	public void setSprite(int col,int row){
		this.width = (int) (image.getWidth()/col);
		this.height = (int) (image.getHeight()/row);
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setSprite(String url,int col,int row){
		image = new Image(url);
		this.width = (int) (image.getWidth()/col);
		this.height = (int) (image.getHeight()/row);
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setFrame(int frame){
		this.frame = frame;
	}
}
