package block;

import assets.Textures;
// import gameObject.gameObjectType;

public class Bookshelf extends wall{

	public Bookshelf(int x, int y) {
		super(x, y);
		setSprite(Textures.Bookshelf, 1, 1);
	}
	@Override
    protected void update() {
		//this.itemStair.isPickup()
        // if(false){
        //     this.setFrame(1+((this.getTimer()/50)+1)%2);

        // }else{
        //     // this.setFrame(0);
		// 	// setType(gameObjectType.PATH);
        // }

    }
}
