package automate;

import java.util.function.Consumer;

public class AutomateString{
	private String chars;
	private int cursor = 0;
	public int getLength(){
		return this.chars.length();
	}
	public AutomateString(String str){
		this.chars = str;
	}
	public char next(){
		return this.cursor>=this.getLength()?null:this.chars.charAt(cursor++);
	}
	public char last(){
		return this.chars.charAt(this.cursor-1);
	}
	public boolean hasNext(){
		return this.cursor<this.getLength()&&(this.chars.charAt(this.cursor) != 0);
	}
	public void forEach(Consumer<Character> callback){
		var c = this.next();
		while(this.hasNext()){
			callback.accept(c);
			c = this.next();
		}
		callback.accept(c);
		callback.accept((char) 0);
		return ;
	}
	public void forEach(Consumer<Character> callback,Consumer<Character> after){
		this.forEach(callback);
		after.accept((char) 0);
	}
}