package entity;

import java.util.List;
import java.util.Optional;

import assets.Textures;
import game.Game;
import gameObject.gameObject;
import gameObject.gameObjectType;
import gameObject.movement;
import gameObject.pattern;

/**
 *La class Mob extends gameObject
 */
public class Mob extends gameObject {
	protected int speed = 5;
	protected int init_X = 0;
	protected int init_Y = 0;
	public void setInitX(int init_X) {
		this.init_X = (int)(init_X*Game.BLOCK_WIDTH*this.getScene().getScale());
	}
	public void setInitY(int init_Y) {
		this.init_Y = (int)(init_Y*Game.BLOCK_HEIGHT*this.getScene().getScale());
	}
	private pattern pattern;
	public void setPattern(pattern pattern) {
		this.pattern = pattern;
	}

	/**
	 *la méthode Public Mod setSprite(col,row) le nombre de column et ligne pour decouper ces tiles
	 * @param x
	 * @param y
	 */
	public Mob(int x, int y) {
		super(x,y+.5);
		init_X = x;
		init_Y = y;
		this.pattern = new pattern();
		setSprite(Textures.mob, 2, 1);
	}

	/**
	 * La methode public setSprite c'est pour definir
	 * la textures et le nombre de column et ligne pour decouper ces tiles
	 * @param x
	 * @param y
	 * @param pattern
	 */
	public Mob(int x, int y,pattern pattern) {
		super(x, y+.5);
		this.pattern = pattern;
		init_X = x;
		init_Y = y;
		setSprite(Textures.mob, 2, 1);
	}

	movement move_mob = movement.RESTART;

	/**
	 *
	 */
	public void moves() {
		if(this.wait(50))
			move_mob =pattern.next();
		switch (move_mob) {
		 	case DOWN:setY(getY()+speed);break;
		 	case LEFT:
				setX(getX()-speed);
				flipH(true);
				break;
		 	case RIGHT:
				flipH(false);
				setX(getX()+speed);
				break;
		 	case UP:setY(getY()-speed);break;
		 	case RESTART:
				setX(init_X);
				setY(init_Y);
				break;
		 }
 	}
	protected double degat = .5;
	private boolean canplayer = true;
	@Override
	/**
	 *  Update Override la methode Update dans la class gameObject
	 *  La méthode Update permet de mettre à jour le Mob
	 */
	protected void update() {
		if(wait(50)){
			moves();
		}
		if(isplayerTouching() && canplayer){
            player.addHealth(-degat);
            canplayer = false;
        }
        if(!canplayer && this.wait(50)){
            canplayer = true;
        }
	}
	Player player;

	/**
	 *La méthode isplayerTouching permet de verifier si le player a toucher l'enémie
	 * @return
	 */
	protected boolean isplayerTouching(){
		List<gameObject> objects = this.getScene().getObjects(getX(),getY());
		Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
			return gameObjectType.PLAYER.equals(gO.getType());
		}).findFirst();
		// System.out.println(OgO);
		boolean ispresent = OgO.isPresent();
		if(ispresent){
			player = (Player) OgO.get();
		}
		return ispresent;
	}
}

