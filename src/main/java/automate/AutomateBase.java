package automate;

public class AutomateBase {
	final public static int ETAT_INITIAL = 0;
	final public static boolean REJECT = false;
	final public static boolean ACCPET = true;
	/**
	 * transforme une chaine en chaine d'automate
	 * @param entree
	 * @return
	 */
	public AutomateString translate(String entree){
		return new AutomateString(entree);
	}
	/**
	 * renvoit la chaine d'automate
	 * sert juste a appele la methode translate sans que l'utilisateur se preocuppe du type de sa variable.
	 * @param entree
	 * @return
	 */
	public AutomateString translate(AutomateString entree){
		return entree;
	}
}