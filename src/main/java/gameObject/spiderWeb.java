package gameObject;

import assets.Textures;

import java.util.List;
import java.util.Optional;

public class spiderWeb extends gameObject{

    private boolean canplayer = true;

    spiderWeb spider_web;
    private player player_speed;
    public spiderWeb(int x,int y) {
        super(x,y, Textures.spiderWeb);
        setType(gameObjectType.PATH);
    }
    player player;
    private boolean isplayerTouching(){
        List<gameObject> objects = this.getScene().getObjects(getX(),getY());
        Optional<gameObject> OgO = objects.stream().filter((gameObject gO)->{
            return gameObjectType.PLAYER.equals(gO.getType());
        }).findFirst();
        // System.out.println(OgO);
        boolean ispresent = OgO.isPresent();
        if(ispresent){
            player = (player) OgO.get();
        }
        return ispresent;
    }


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
