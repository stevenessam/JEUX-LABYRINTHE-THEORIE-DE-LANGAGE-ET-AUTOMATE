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
	public Token (String input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	public Token(String input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}

	public Token (Pattern input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	public Token(Pattern input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}

	public Token (List<Character> input,String output,Integer[] stats){
		this.token = input;
		this.output = output;
		this.stats = Arrays.asList(stats);
	}
	public Token(List<Character> input, String output) {
		this.token = input;
		this.output = output;
		this.stats = new ArrayList<Integer>();
	}

	public Token (Character input,String output,Integer[] stats){
		this(Arrays.asList(new Character[]{input}),output,stats);
	}
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
	public boolean includes(int stat){
		return this.stats.contains(stat);
	}
	public void add(int stat){
		this.stats.add(stat);
	}
	public void add(Token token){
		this.stats.add(token.getStat());
	}
	@Override
	public String toString() {
		return token.toString();
	}
	public boolean test(String input){
		if(token instanceof Pattern ){
			Matcher matcher = ((Pattern)token).matcher(input);
			if(matcher.find()){
				return matcher.start() == 0;
			}else{
				return false;
			}
		}else if(this.token instanceof String){
			return ((String) token).equals(input);
		}else if(this.token instanceof List){
			return ((List) this.token).contains(input);
		}else{
			return false;
		}
	}
}
