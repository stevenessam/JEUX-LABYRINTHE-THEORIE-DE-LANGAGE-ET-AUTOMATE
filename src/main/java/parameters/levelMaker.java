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
		level.setDebug(true);
		
		/** commentaire "--[a-z]*" */
		List<Token> group = new ArrayList<Token>();
		Token commentStart = new Token("--","commentStart");
		level.addToken(commentStart);
		commentStart.add(0);

		Token commentComment = new Token(Pattern.compile("[^\n]"),"commentComment");
		level.addToken(commentComment);
		commentComment.add(commentStart);
		commentComment.add(commentComment);

		group.add(commentComment);
		groups.add(group);

		/** define block "set . to [a-z]*;" */
		group = new ArrayList<Token>();
		Token blockStart = new Token("set ","blockStart");
		level.addToken(blockStart);
		blockStart.add(0);

		Token blockVar = new Token(Pattern.compile("."),"blockVar");
		level.addToken(blockVar);
		blockVar.add(blockStart);

		Token blockAssign = new Token(" to ","blockAssign");
		level.addToken(blockAssign);
		blockAssign.add(blockVar);

		Token blockName = new Token(Pattern.compile("[a-z]"),"blockName");
		level.addToken(blockName);
		blockName.add(blockAssign);
		blockName.add(blockName);

		group.add(blockName);
		groups.add(group);

		parser("--set vars");
		// parser("set _ to floor;");
	}
	public void parser(String map){
		List<RegonizeToken> tokens = level.regroup(new AutomateString(map),groups);
		System.out.println(tokens);
	}
}
