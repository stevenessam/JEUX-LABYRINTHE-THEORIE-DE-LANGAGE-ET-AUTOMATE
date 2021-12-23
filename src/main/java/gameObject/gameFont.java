package gameObject;

import java.util.function.Consumer;

import assets.Textures;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;

public class gameFont extends gameObject{
	// private final static double default_FontSize = 20;
	public void setScene(gameScene scene) {
		super.setScene(scene);
		// myfontSize = getFontSize()*scene.getScale();
	}
	// private double myfontSize = 5;
	private static double fontSize = 5;
	public static double getFontSize() {
		return fontSize;
	}
	public static void setFontSize(double fontSize) {
		gameFont.fontSize = fontSize;
	}
	private String text;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text.toLowerCase();
	}
	private double init_x = 0;
	private boolean centred = false;
	public void setCentred(boolean centred) {
		this.centred = centred;
	}
	public boolean isCentred() {
		return centred;
	}
/**
 * Crée une instance de Text sur les coordonnées données contenant la chaîne donnée,
 * avec un text centré
 * @param x
 * @param y
 * @param text
 * @param centred
 */
	public gameFont(double x,double y,String text,boolean centred) {
		this(x,y,text);
		this.setCentred(centred);
	}
	/**
	 * Crée une instance de Text sur les coordonnées données contenant la chaîne donnée.
	 * @param x
	 * @param y
	 * @param text
	 */
	public gameFont(double x,double y,String text) {
		super(0,0,Textures.font);
		init_x = x;
		setY(y);
		setText(text);
		setSprite(6, 8);
	}
	private int letterMatching(char letter){
		switch(letter){
			case '.':return 27;
			case '!':return 28;
			case '?':return 29;
			case '-':return 30;
			case ',':return 30;
			case ';':return 31;
			case '"':return 32;
			case '`':return 33;
			case '(':return 34;
			case ')':return 35;
			case '1':return 36;
			case '2':return 37;
			case '3':return 38;
			case '4':return 39;
			case '5':return 40;
			case '6':return 41;
			case '7':return 43;
			case '8':return 44;
			case '9':return 45;
			case '0':return 46;
			case ' ':return 48;
			default:
				return letter - 'a';
		}
	}
	/**
	 * fait l'affichage du text
	 * 
	 */
	@Override
	public void render(GraphicsContext graphic) {
		// var s = myfontSize/default_FontSize;
		int s = 1;
		// graphic.scale(s, s);
		// calcul position 
		var length = text.length();
		if(centred){
			//si centré x = position_initial - largeurText/2 avec largeurText = longeurText * largeurCharacter
			super.setX(init_x-(length/2*getWidth()));
		}else{
			super.setX(init_x);
		}
		//pour chaque lettre du text
		for (int i = 0; i < length; i++) {
			char letter = text.charAt(i);//recupere le caracter
			int frame = letterMatching(letter);//cherche la frame de ce caracter
			setFrame(frame);// definie la frame a afficher
			super.render(graphic);//faire l'affichage du character
			super.setX(getX()+getWidth());//se deplacer d'un caracter avant de faire le prochain
		}
		s = 1/s;
		// graphic.scale(s, s);
	}
	private Consumer<MouseButton> action;
	/**
	 * Defini une action au moment du clique
	 * @param action
	 */
	public void setAction(Consumer<MouseButton> action) {
		this.action = action;
	}
	@Override
	public void click(double x, double y, MouseButton button) {
		if(action != null){
			action.accept(button);
		}
	}
	/**
	 * verifie si l'element est present au coordooné indique.
	 * @param x
	 * @param y
	 */
	public boolean isOn(double x,double y){
		int length = text.length();
		// double s = myfontSize/gameFont.default_FontSize;
		double ix = init_x;
		if(centred){
			ix = (init_x-(length/2*getWidth()));
		}
		double iy = getY();
		double width = getWidth()*length;
		double height = getHeight();
		double dx = -width * this.getDeltaX();
		double dy = -height * this.getDeltaY();
		boolean onX = ix+dx<=x&&ix+dx+width>=x;
		boolean onY = iy+dy<=y&&iy+dy+height>=y;
		System.out.println(onX+ " " +onY);
		return onX&&onY;
	}
	/**
	 * Creer une instance de gameFont qui agira comme un button,
	 * @param x
	 * @param y
	 * @param text
	 * @param click
	 * @return
	 */
	public static gameFont createButton(double x,double y,String text,Consumer<MouseButton> click){
		gameFont btn = new gameFont(x, y, text,true);
		btn.setAction(click);
		return btn;
	}
}
