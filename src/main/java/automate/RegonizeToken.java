package automate;

public class RegonizeToken{
	private String input;
	private String output = "undefined";
	private int stat;
	private Token token;
	public RegonizeToken (String input,int stat){
		this.input = input;
		this.stat = stat;
	}
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
