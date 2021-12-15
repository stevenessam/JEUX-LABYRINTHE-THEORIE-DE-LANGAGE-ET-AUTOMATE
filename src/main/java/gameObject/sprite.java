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
	private double renderwidth;
	private int width;
	public double getWidth() {
		return width*scale;
	}
	private double renderheight;
	private int height;
	public double getHeight() {
		return height*scale;
	}

	private double scale = 1;
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
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
	public void setDelta(double delta) {
		this.deltaX = delta;
		this.deltaY = delta;
	}
	public double getDeltaX() {
		return deltaX;
	}
	public double getDeltaY() {
		return deltaY;
	}


	protected int frame = 1;

	public sprite(String sprite) {
		// this.image = new Image(sprite);
		this.image = Textures.load(sprite);
		renderwidth = (image.getWidth());
		this.width = (int) renderwidth;
		renderheight = (image.getHeight());
		this.height = (int) renderheight;
		}
	public void render(GraphicsContext graphic){
		double dx = -width * this.deltaX;
		double dy = -height * this.deltaY;
		if(column == 1 && row ==1){
			graphic.drawImage(image, x+dx, y+dy,renderwidth*scale,renderheight*scale);
		}else{
			graphic.drawImage(image, ((frame)%column)*width, (int)((frame)/column)*height, width, height, x+dx, y+dy, renderwidth*scale, renderheight*scale);
		}
	}
	public void setSprite(int col,int row){
		renderwidth = (image.getWidth()/col);
		this.width = (int) renderwidth;
		renderheight = (image.getHeight()/row);
		this.height = (int) renderheight;
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setSprite(String url,int col,int row){
		image = Textures.load(url);
		renderwidth = (image.getWidth()/col);
		this.width = (int) renderwidth;
		renderheight = (image.getHeight()/row);
		this.height = (int) renderheight;
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setFrame(int frame){
		this.frame = frame;
	}
	public void setRenderWidth(double w) {
		renderwidth = w/scale;
	}
	public void setRenderHeight(double h) {
		renderheight = h/scale;
	}
	public double getRenderWidth() {
		return renderwidth;
	}
	public double getRenderHeight() {
		return renderheight;
	}
}
