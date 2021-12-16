package gameObject;

import java.util.ArrayList;
import java.util.List;




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

        if( this.size()  == cursor ){
            cursor=0;
        }
        return this.get(cursor++);
    }
}
