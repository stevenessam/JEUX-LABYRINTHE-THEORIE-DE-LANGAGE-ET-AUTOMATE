package automate;
/**
 * Tom TOUZE 
 * projet : TLA 2021
 */

public class RegonizeToken{
	/**
	 * class de stockage des elements
	 */
	private String input;
	private String output = "undefined";
	private int stat;
	private Token token;
	/**
	 * pour un token non reconnu
	 * @param input
	 * @param stat
	 */
	public RegonizeToken (String input,int stat){
		this.input = input;
		this.stat = stat;
	}
	/**
	 * quand le token est reconnu il y a plusieurs informations
	 * @param input chaine entré dans le language
	 * @param output nom de l'etat (utilisé lors de l'execition )
	 * @param stat etat en cour dans l'automate
	 * @param token token original connu par l'automate
	 */
	public RegonizeToken (String input,String output,int stat,Token token){
		this.input = input;
		this.output = output;
		this.stat = stat;
		this.token = token;

	}
	public String getInput() {
		return input;
	}
	public Token getToken() {
		return token;
	}
	public String getOutput() {
		return output;
	}public int getStat() {
		return stat;
	}
	@Override
	public String toString() {
		return input;
	}
}
