package gameObject;

import javafx.scene.input.KeyCode;
import ressourceManages.Textures;

public class enemies extends gameObject {

    private int speed = 5;

    public enemies(int x, int y,pattern) {
        super(x, y);

        setSprite(Textures.enemies, 2, 2);
    }

    public void DOWN() {
        if (y < this.getScene().getCanvasHeight() - 1 && getNoCollision(x, y + 1)) {
            y += speed;
        }
    }

    public void LEFT() {
        if (x > 0 && getNoCollision(x - 1, y)) {
            x -= speed;
        }
    }

    public void RIGHT() {
        if (x < this.getScene().getCanvasWidth() - 1 && getNoCollision(x + 1, y)) {
            x += speed;
        }
    }

    public void UP() {
        if (y > 0 && getNoCollision(x, y - 1)) {
            y -= speed;
        }
    }


    public void enemies_deplacement() {

        switch (pattern.next()) {
            case DOWN:DOWN();
                break;
            case LEFT:LEFT();
                break;
            case RIGHT:RIGHT();
                break;
            case UP:UP();
                break;
        }
    }
}
}

