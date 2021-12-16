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

	public gameFont(double x,double y,String text,boolean centred) {
		this(x,y,text);
		this.setCentred(centred);
	}
	public gameFont(double x,double y,String text) {
		super(0,0,Textures.font);
		init_x = x;
		setY(y);
		setText(text);
		setSprite(6, 6);
	}
	private int letterMatching(char letter){
		switch(letter){
			case '.':return 27;
			default:
				return letter - 'a';
		}
	}
	@Override
	public void render(GraphicsContext graphic) {
		// var s = myfontSize/default_FontSize;
		int s = 1;
		// graphic.scale(s, s);
		var length = text.length();
		if(centred){
			super.setX(init_x-(length/2*getWidth()));
		}else{
			super.setX(init_x);
		}
		for (int i = 0; i < length; i++) {
			char letter = text.charAt(i);
			int frame = letterMatching(letter);
			setFrame(frame);
			super.render(graphic);
			super.setX(getX()+getWidth());
		}
		s = 1/s;
		// graphic.scale(s, s);
	}
	private Consumer<MouseButton> action;
	public void setAction(Consumer<MouseButton> action) {
		this.action = action;
	}
	@Override
	public void click(double x, double y, MouseButton button) {
		if(action != null){
			action.accept(button);
		}
	}
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
	public static gameFont createButton(double x,double y,String text,Consumer<MouseButton> click){
		gameFont btn = new gameFont(x, y, text,true);
		btn.setAction(click);
		return btn;
	}
}
