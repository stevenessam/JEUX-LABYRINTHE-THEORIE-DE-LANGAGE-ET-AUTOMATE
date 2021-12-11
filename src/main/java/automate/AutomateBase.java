package automate;

public class AutomateBase {
	final public static int ETAT_INITIAL = 0;
	final public static boolean REJECT = false;
	final public static boolean ACCPET = true;

	public AutomateString translate(String entree){
		return new AutomateString(entree);
	}
	public AutomateString translate(AutomateString entree){
		return entree;
	}
}