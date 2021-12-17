package gameObject;

import assets.Textures;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

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
	/** taille de l'image */
	private double scale = 1;
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}

	private Image image;
	/** sprite division en tiles */
	private int column = 1;
	private int row = 1;
	/** decalage */
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
		// creer une image;
		this.image = Textures.load(sprite);
		// largeur du rendu par defaut est la largeur de l'image;
		renderwidth = (image.getWidth());
		this.width = (int) renderwidth;
		// hauteur du rendu par defaut est la hauteur de l'image;
		renderheight = (image.getHeight());
		this.height = (int) renderheight;
		}
	public void render(GraphicsContext graphic){
		/** faire l'affichage de l'image */
		// calcule des decalage
		double dx = -renderheight * this.deltaX;
		double dy = -renderwidth * this.deltaY;
		// si l'image n'est pas composer de tiles (soit si l'image n'est pas un sprite)
		if(column == 1 && row ==1){
			graphic.drawImage(image, x+dx, y+dy,renderwidth*scale,renderheight*scale);
		}else{
			graphic.drawImage(image, ((frame)%column)*width, (int)((frame)/column)*height, width, height, x+dx, y+dy, renderwidth*scale, renderheight*scale);
		}
	}

	public void setSprite(int col,int row){
		/**pour le nombre de column et ligne pour decouper ces tiles */
		renderwidth = (image.getWidth()/col);
		this.width = (int) renderwidth;
		renderheight = (image.getHeight()/row);
		this.height = (int) renderheight;
		this.column = col;
		this.row = row;
		this.frame = 0;
	}
	public void setSprite(String url,int col,int row){
		/**definir la textures et le nombre de column et ligne pour decouper ces tiles */
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
		/** defini le numero du tiles a afficher  */
		// 0|1|2|3|
		// 4|5|6|7|
		this.frame = frame;
	}
	public void setRenderWidth(double w) {
		/** definir largeur de l'image à l'affichage */
		renderwidth = w/scale;
	}
	public void setRenderHeight(double h) {
		/** definir hauteur de l'image à l'affichage */
		renderheight = h/scale;
	}
	public double getRenderWidth() {
		/** retourner largeur de l'image à l'affichage */
		return renderwidth;
	}
	public double getRenderHeight() {
		/** retourner hauteur de l'image à l'affichage */
		return renderheight;
	}
	/** Flip */
	public void flipV(){
		/** flip vertical image */
		renderheight = -renderheight;
	}
	public void flipH(){
		/** flip horizontal image */
		renderwidth = -renderwidth;
	}
	public void flipV(boolean activated){
		/** flip vertical image avec vrai ou faux*/
		int flip = activated?-1:1;
		renderheight = Math.abs(renderheight)*flip;
	}
	public void flipH(boolean activated){
		/** flip horizontal image avec vrai ou faux*/
		int flip = activated?-1:1;
		renderwidth = Math.abs(renderwidth)*flip;
	}
}
