package gameObject;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import ressourceManages.Textures;

public class sprite {

	private float x;
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	private float y;
	public float getY() {
		return y;
	}
	public void setY(float y) {
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



	protected int frame = 1;

	public sprite(String sprite) {
		// this.image = new Image(sprite);
		this.image = Textures.load(sprite);
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
