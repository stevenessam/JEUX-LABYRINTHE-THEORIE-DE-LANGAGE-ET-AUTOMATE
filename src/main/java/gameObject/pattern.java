package gameObject;

import java.util.ArrayList;




public class pattern extends ArrayList<movement> {
    public pattern(movement... args) {
        super();

        for (movement t : args) {
            this.add(t);
        }

    }

    int cursor = 0;

    public movement next() {
        //tu retourne le prochain movement si c'est la si tu revient au debut
        return this.get(cursor++);
    }
}
