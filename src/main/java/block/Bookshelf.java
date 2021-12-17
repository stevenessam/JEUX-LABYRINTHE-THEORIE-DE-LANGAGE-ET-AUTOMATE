package block;

import java.util.List;

import assets.Textures;
// import gameObject.gameObjectType;
import gameObject.gameObject;
import gameObject.gameObjectType;

public class Bookshelf extends Wall{

	private boolean visited = false;
	private int VisitedX = 0;
	private int VisitedY = 0;
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
		setDeltaX(1);
	}
	/**
	 *Le constructeur de Bookshelf prend les positions x et y comme paramètres
	 * @param x
	 * @param y
	 */
	public Bookshelf(int x, int y) {
		this(x,y,x+0,y+1);
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
				if(getDeltaX()>0){
					setDeltaX(getDeltaX()-.05);
				}else{
					isopen = true;
				}
			}
		}
	}
}
