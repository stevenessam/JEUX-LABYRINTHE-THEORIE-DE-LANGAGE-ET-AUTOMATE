package block;

import assets.Textures;
import entity.Player;
import game.Game;
import gameObject.gameObject;
import gameObject.gameObjectType;
import javafx.scene.canvas.GraphicsContext;

import java.util.List;
import java.util.Optional;

/**
 * la class Roof extends gameObject
 */

public class Roof extends gameObject {
	private boolean visited = false;
	private int VisitedX = 0;
	private int VisitedY = 0;
	/**
	 * paramétress des constructeurs
	 * @param x
	 * @param y
	 */
    public Roof(int x, int y) {
		this(x,y,x,y);
	}
    public Roof(int x, int y,int vx,int vy) {
        super(x+.5,y+.5, Textures.roof);
        this.setDeltaX(.5);
        this.setDeltaY(1.5);
		setSprite(1, 3);
		this.setFrame(-1);
		VisitedX = vx;
		VisitedY = vy;
    }
    Player player;
    private boolean isplayerTouching(){
        if(!visited){
			List<gameObject> objects = this.getScene().getObjects(
				(VisitedX+.5)*Game.BLOCK_WIDTH*this.getScene().getScale(),
				(VisitedY+.5)*Game.BLOCK_HEIGHT*this.getScene().getScale()
			);
			Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
				return gameObjectType.PLAYER.equals(gO.getType());
			}).findFirst();
			// System.out.println(OgO);
			visited = OgO.isPresent();
			if(visited){
				player = (Player) OgO.get();
			}
		}
		return visited;
    }

	/**
	 * la methode effect qui retourne un void
	 */
    public void effect(){
        player.addHealth(-1.5);
		setType(gameObjectType.SOLID);
    }
	boolean onfloor = false;
	boolean isfalling = false;
	@Override
	public void render(GraphicsContext graphic) {
		if(isfalling || onfloor)
			super.render(graphic);
		
	}

	/**
	 * Update Override la methode Update dans la class gameObject
	 *   La méthode Update permet de mettre à jour le Roof
	 */
    @Override
    protected void update() {
		if(!onfloor){
			if(isplayerTouching() && this.getScene()!=null && !isfalling){
				isfalling = true;
				player.addHealth(-.5);
	        }
			if(isfalling){
				if(getDeltaY()>.5){
					this.setDeltaY(getDeltaY()-.05);
					this.setFrame(getDeltaY()>1?0:1);
				}else{
					effect();
					this.setFrame(2);
					onfloor = true;
				}
			}
		}
    }
}
