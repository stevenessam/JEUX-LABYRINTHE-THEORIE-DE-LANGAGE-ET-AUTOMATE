package automate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Token {
	private int stat;
	private Object token;
	private String output;
	private List<Integer> stats;
	/**
	 * creer un token avec une chaine de caracter
	 * @param input
	 * @param output
	 * @param stats
	 */
	public Token (String input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	/**
	 * creer un token avec une chaine de caracter
	 * @param input
	 * @param output
	 */
	public Token(String input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}
	/**
	 * creer un token avec une repression reguliere
	 * @param input
	 * @param output
	 * @param stats
	 */
	public Token (Pattern input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	/**
	 * creer un token avec une repression reguliere
	 * @param input
	 * @param output
	 */
	public Token(Pattern input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}
	/**
	 * creer un token avec une list de caracter
	 * @param input
	 * @param output
	 * @param stats
	 */
	public Token (List<Character> input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	/**
	 * creer un token avec une list de caracter
	 * @param input
	 * @param output
	 */
	public Token(List<Character> input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}
	/**
	 * creer un token avec un caracter
	 * @param input
	 * @param output
	 * @param stats
	 */
	public Token (Character input,String output,Integer[] stats){
		this(Arrays.asList(new Character[]{input}),output,stats);
	}
	/**
	 * creer un token avec un caracter
	 * @param input
	 * @param output
	 */
	public Token(Character input, String output) {
		this(Arrays.asList(new Character[]{input}),output);
	}
	public void setStat(int id){
		stat = id;
	}
	public String getOutput() {
		return output;
	}
	public int getStat() {
		return stat;
	}
	public List<Integer> getStats() {
		return stats;
	}
	public Object getInput() {
		return token;
	}
	/**
	 * verif si ce token peut etre suivre l'etat donné
	 * @param stat
	 * @return
	 */
	public boolean includes(int stat){
		return this.stats.contains(stat);
	}
	/**
	 * ajoute un etat precedent
	 * @param stat
	 */
	public void add(int stat){
		this.stats.add(stat);
	}
	/**
	 * ajoute un token precedent
	 * @param token
	 */
	public void add(Token token){
		this.stats.add(token.getStat());
	}
	@Override
	public String toString() {
		return token.toString();
	}
	/**
	 * verif que le text donné et equal a se token
	 * @param input
	 * @return
	 */
	public boolean test(String input){
		if(token instanceof Pattern ){
			// eqivalent du RegExp.test en javascript
			Matcher matcher = ((Pattern)token).matcher(input);//recupere la list des mots commun
			if(matcher.find()){//verif si il y a des mots commun
				return matcher.start() == 0;//verfi si le premier mots trouver et le premier mots du text donné 
			}else{
				return false;
			}
		}else if(this.token instanceof String){
			return ((String) token).equals(input);// text == text du token
		}else if(this.token instanceof List){
			return ((List) this.token).contains(input);//si le text est un character de la list (n'est pas utilisé par les automate du projet)
		}else{
			return false;
		}
	}
}
