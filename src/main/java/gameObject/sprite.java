package gameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import ressourceManages.Textures;

public class sprite {

	private double x;
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	private double y;
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	private int width;
	public int getWidth() {
		return width;
	}
	private int height;
	public int getHeight() {
		return height;
	}

	private Image image;

	private int column = 1;
	private int row = 1;

	private double deltaX = 0;
	private double deltaY = 0;
	public void setDeltaX(double deltaX) {
		this.deltaX = deltaX;
	}
	public void setDeltaY(double deltaY) {
		this.deltaY = deltaY;
	}
	public void setCentred(double delta) {
		this.deltaX = delta;
		this.deltaY = delta;
	}


	protected int frame = 1;

	public sprite(String sprite) {
		// this.image = new Image(sprite);
		this.image = Textures.load(sprite);
		this.width = (int) this.image.getWidth();
		this.height = (int) this.image.getHeight();
		}
	public void render(GraphicsContext graphic){
		double dx = -width * this.deltaX;
		double dy = -height * this.deltaY;
		if(column == 1 && row ==1){
			graphic.drawImage(image, x+dx, y+dy);
		}else{
			graphic.drawImage(image, ((frame)%column)*width, (int)((frame)/column)*height, width, height, x+dx, y+dy, width, height);
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
		image = Textures.load(url);
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
