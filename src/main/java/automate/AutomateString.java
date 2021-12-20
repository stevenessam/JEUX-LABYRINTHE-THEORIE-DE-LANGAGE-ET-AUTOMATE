package automate;

import java.util.function.Consumer;

public class AutomateString{
	/**
	 * cette class a pour but de symplifer les actions sur la chaine entré dans l'automate
	 */
	private String chars;
	private int cursor = 0;
	/**
	 * Longueur de la chaine de caractere
	 * @return
	 */
	public int getLength(){
		return this.chars.length();
	}
	/**
	 * transforme un chaine de caracter ( @see String )
	 * en chaine lisible par l'automate
	 * @param str
	 */
	public AutomateString(String str){
		this.chars = str;
	}
	/**
	 * prochain caracter
	 * @return
	 */
	public char next(){
		//si le cursor pointera vers un element de la list
		return this.cursor>=this.getLength()?null:this.chars.charAt(cursor++);// retourne le caracter actuel puis passe au prochin (++)
	}
	/**
	 * dernier caracter
	 * @return
	 */
	public char last(){
		return this.chars.charAt(this.cursor-1);// retourne le caracter utilisé avant (-1)
	}
	/**
	 * verifie si le prochain caracter n'est pas null
	 * @return
	 */
	public boolean hasNext(){
		return this.cursor<this.getLength()&&(this.chars.charAt(this.cursor) != 0);//est dans la liste et n'est pas 0
	}
	/**
	 * iteration sur la liste de caractere de la chaine
	 * @param callback
	 */
	public void forEach(Consumer<Character> callback){
		var c = this.next();// prochain caracter
		while(this.hasNext()){// is prochain existe
			callback.accept(c);// jouer l'action pour le caracter c
			c = this.next();// passer au prochain caracter
		}
		callback.accept(c);// jouer l'action pour un caracter non null
		callback.accept((char) 0);// jouer l'action pour le dernier caracter (fin de chaine = 0)
		return ;
	}
	/**
	 * iteration sur la liste avec une action a la fin
	 * @param callback
	 * @param after
	 */
	public void forEach(Consumer<Character> callback,Consumer<Character> after){
		this.forEach(callback);//boucle pour chaque caracter
		after.accept((char) 0);//action a la fin
	}
}