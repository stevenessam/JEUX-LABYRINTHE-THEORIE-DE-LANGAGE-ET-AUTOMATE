package parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import automate.Automate;
import automate.AutomateString;
import automate.RegonizeToken;
import automate.Token;

public class levelMaker {
	Automate level = new Automate();
	List<List<Token>> groups = new ArrayList<List<Token>>();
	
	public levelMaker() {

		List<Token> group = new ArrayList<Token>();

		Token commentStart = new Token("--","commentStart");
		commentStart.add(0);
		level.addToken(commentStart);

		Token commentComment = new Token(Pattern.compile("[a-z]"),"commentComment");
		commentStart.add(-1);
		level.addToken(commentStart);

		group.add(commentComment);
		groups.add(group);

		parser("--set vars");
	}
	public void parser(String map){
		List<RegonizeToken> tokens = level.regroup(new AutomateString(map),groups);
		System.out.println(tokens);
	}
}
