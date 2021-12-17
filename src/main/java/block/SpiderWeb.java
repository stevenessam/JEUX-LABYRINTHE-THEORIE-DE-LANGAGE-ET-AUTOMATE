package block;

import assets.Textures;
import entity.Player;
import gameObject.gameObject;
import gameObject.gameObjectType;

import java.util.List;
import java.util.Optional;
/**
 * Class SpiderWeb qui extends la class gameObject
 */
public class SpiderWeb extends gameObject{

    /**
     * Variable canplayer boolean = true
     */
    private boolean canplayer = true;
    /**
     * spider_web variable de la class {@link SpiderWeb}
     */
    SpiderWeb spider_web;

    /**
     * Constructeur de la class {@link SpiderWeb} qui a comme parametre x et y
     * et un setType( gameObjectType.PATH ) qui permet le player de passer sur le spiderWeb
     * @param x
     * @param y
     */
    public SpiderWeb(int x, int y) {
        super(x,y, Textures.spiderWeb);
        setType(gameObjectType.PATH);
    }

    /**
     * player un variable de la class {@link Player}
     */
    Player player;

    /**
     * Methode boolean isplayerTouching verifie si le player a toucher le spiderWeb ou non
     * @return
     */
    private boolean isplayerTouching(){
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

    /**
     *  Update Override la methode Update dans la class gameObject
     *  La méthode Update permet de mettre à jour le spiderweb et le player
     *  si le joueur va sur la toile d'araignée la vitesse du joueur sera réduite avec la méthode slowness
     */
    @Override
    protected void update() {

            if(isplayerTouching() && canplayer){
                player.slowness(1);
                canplayer = false;
            }

            if(!canplayer && this.wait(50)){
                canplayer = true;
                player.slowness(5);
            }

    }
}
