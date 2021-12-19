package block;

import java.util.List;

import assets.Textures;
import game.Game;
// import gameObject.gameObjectType;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.gameScene;
import gameObject.sprite;
import javafx.scene.canvas.GraphicsContext;

public class Bookshelf extends Wall{
	private sprite book = new sprite(Textures.Book);
	private boolean visited = false;
	private double VisitedX = 0;
	private double VisitedY = 0;
	@Override
	public void setScene(gameScene scene) {
		super.setScene(scene);
		VisitedX *= this.getScene().getScale();
		VisitedY *= this.getScene().getScale();
		book.setX(VisitedX);
		book.setY(VisitedY);
	}
	/**
	 *Le constructeur de Bookshelf prend les positions x et y comme paramètres
	 * @param x
	 * @param y
	 * @param VisitedX position x a avoir vu
	 * @param VisitedY position y a avoir vu
	 * 
	 * le bookshelf peut se deplace quand le joueur passe sur certain case;
	 */
	public Bookshelf(int x, int y,int vx,int vy) {
		super(x, y);
		// defini l'image
		setSprite(Textures.Bookshelf, 1, 1);
		setType(gameObjectType.SOLID);
		setDeltaX(0);
		VisitedX = (vx+.5)*Game.BLOCK_WIDTH;
		VisitedY = (vy+.5)*Game.BLOCK_HEIGHT;
		book.setX(VisitedX);
		book.setY(VisitedY);
		book.setSprite(1, 5);
		book.setDeltaX(.5);
		book.setDeltaY(.5);
	}
	/**
	 *C
	 * @param x
	 * @param y
	 */
	public Bookshelf(int x, int y) {
		this(x,y,x,y);
	}
	private boolean hasVisited(){
		if(!visited){
			List<gameObject> objects = this.getScene().getObjects(VisitedX,VisitedY);
			visited = objects.stream().filter((gameObject gO)->{
				return gameObjectType.PLAYER.equals(gO.getType());
			}).findFirst().isPresent();
		}
		return visited;
	}
	/**
	 * isopen est vrai quand bookshlef s'est deplacé et laisse le passage libre
	 */
	private boolean isopen = false;
	@Override
	protected void update() {
		if(hasVisited()){
			if(!isopen){
				if(getDeltaX()>-1){
					setDeltaX(getDeltaX()-.05);
				}else{
					isopen = true;
				}
			}
		}else{
			book.setFrame(((this.getTimer()/10)+1)%5);
		}
	}
	@Override
	public void render(GraphicsContext graphic) {
		super.render(graphic);
		if(!visited){
			book.render(graphic);
		}
	}
}
