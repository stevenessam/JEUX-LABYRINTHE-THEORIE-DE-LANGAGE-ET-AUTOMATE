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
		List<Token> group = new ArrayList<Token>();

		/** commentaire "--[a-z]*" */
		Token commentStart = new Token("--","commentStart");
		commentStart.add(0);
		level.addToken(commentStart);

		// Token commentComment = new Token(Pattern.compile("[a-z]"),"commentComment");
		// commentComment.add(commentStart);
		// commentComment.add(-1);
		// level.addToken(commentComment);

		// group.add(commentComment);
		groups.add(group);

		/** define block "set . to [a-z]*;" */

		Token blockStart = new Token("set ","blockStart");
		blockStart.add(0);
		level.addToken(blockStart);

		Token blockVar = new Token(Pattern.compile("."),"blockVar");
		blockVar.add(blockStart);
		level.addToken(blockVar);

		Token blockAssign = new Token(" to ","blockAssign");
		blockAssign.add(blockVar);
		level.addToken(blockAssign);

		Token blockName = new Token(Pattern.compile("[a-z]"),"blockName");
		blockName.add(blockAssign);
		blockName.add(-1);
		level.addToken(blockName);

		// parser("--set vars");
		parser("set _ to floor;");
	}
	public void parser(String map){
		List<RegonizeToken> tokens = level.regroup(new AutomateString(map),groups);
		System.out.println(tokens);
	}
}
