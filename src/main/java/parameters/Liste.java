package parameters;

public class Liste {
    int val;
    Liste suivant;
    Liste(int v, Liste x) {
        val = v;
        suivant = x;
    }
    /*La longueur d'une liste*/
    static int longueur(Liste x) {
        if (x == null)
            return 0;
        else
            return 1 + longueur(x.suivant);
    }
    /* Le k ème élément*/
    static int kieme(Liste x, int k) {
        if (k == 1)
            return x.val;
        else
            return kieme(x.suivant, k-1);
    }
    /* Le test d'appartenance*/
    static boolean estDans(Liste x, int v) {
        if (x == null)
            return false;
        else
            return x.val == v || estDans(x.suivant, v);
    }

}
